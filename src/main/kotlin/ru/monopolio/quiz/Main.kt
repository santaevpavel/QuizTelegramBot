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
import ru.monopolio.quiz.core.Application
import ru.monopolio.quiz.core.di.IAppModule
import ru.monopolio.quiz.core.repository.Repositories
import ru.monopolio.quiz.core.scheduler.IScheduler
import ru.monopolio.quiz.properties.LocalProperties
import ru.monopolio.quiz.telegram.TelegramBot
import ru.monopolio.quiz.telegram.entities.Update
import ru.monopolio.quiz.telegram.repositories.MessageRepository
import ru.monopolio.quiz.telegram.repositories.QuestionRepository
import ru.monopolio.quiz.telegram.repositories.RoundRepository
import ru.monopolio.quiz.telegram.repositories.SessionRepository
import ru.monopolio.quiz.telegram.scheduler.Scheduler

fun main(args: Array<String>) {
    val token = LocalProperties().token
    val bot = TelegramBot(token)
    val repositories = Repositories(
            roundRepository = RoundRepository,
            messageRepository = MessageRepository(bot),
            questionRepository = QuestionRepository,
            sessionRepository = SessionRepository
    )
    val useCaseHandler = UseCaseHandler()
    val updateHandler = UpdateHandler(
            useCaseHandler,
            repositories
    )
    val scheduler = Scheduler()

    Application.init(appModule = object : IAppModule {

        override val repositories: Repositories = repositories
        override val scheduler: IScheduler = scheduler
    })

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
                updateHandler.handle(update)
            }
        }
    }
    Logger.log = LoggerFactory.getLogger("TelegramBot")
    server.start(wait = true)
}