package ru.monopolio.quiz

import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.content.readText
import io.ktor.features.CallLogging
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import org.slf4j.LoggerFactory
import ru.monopolio.quiz.core.usecase.Repositories
import ru.monopolio.quiz.properties.LocalProperties
import ru.monopolio.quiz.telegram.TelegramBot
import ru.monopolio.quiz.telegram.entities.Update
import ru.monopolio.quiz.telegram.repositories.*

fun main(args: Array<String>) {
    val token = LocalProperties().token
    val bot = TelegramBot(token)
    val repositories = Repositories(
            roundRepository = RoundRepository,
            messageRepository = MessageRepository(bot),
            questionRepository = QuestionRepository,
            pointsRepository = PointsRepository,
            sessionRepository = SessionRepository
    )
    val useCaseHandler = UseCaseHandler()
    val updateHandler = UpdateHandler(
            useCaseHandler,
            repositories
    )

    val server = embeddedServer(Jetty, port = 9000) {
        install(CallLogging)
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            post("/$token") {
                val body = call.request.receiveContent().readText()
                val update = Gson().fromJson(body, Update::class.java)

                log.debug("webhook called: ${update.message?.text}")

                call.respond(HttpStatusCode.OK, "OK")
                updateHandler.handleUpdate(update)
            }
        }
    }
    Logger.log = LoggerFactory.getLogger("TelegramBot")
    server.start(wait = true)
}