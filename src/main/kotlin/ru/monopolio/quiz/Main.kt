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
import ru.monopolio.quiz.core.entity.Player
import ru.monopolio.quiz.core.repository.IMessageRepository
import ru.monopolio.quiz.core.usecase.AnswerUseCase
import ru.monopolio.quiz.core.usecase.Repositories
import ru.monopolio.quiz.core.usecase.StartGameUseCase
import ru.monopolio.quiz.properties.LocalProperties
import ru.monopolio.quiz.telegram.TelegramBot
import ru.monopolio.quiz.telegram.entities.Update
import ru.monopolio.quiz.telegram.repositories.*

fun main(args: Array<String>) {
    val token = LocalProperties().token
    val bot = TelegramBot(token)

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

                val text = update.message?.text
                val chatId = update.message?.chat?.id ?: -1

                val repositories = Repositories(
                        roundRepository = RoundRepository(chatId),
                        messageRepository = MessageRepository(bot, chatId),
                        questionRepository = QuestionRepository,
                        pointsRepository = PointsRepository(chatId)
                )

                when (text!!.toLowerCase()) {
                    "start" -> {
                        SessionRepository
                                .getSession(chatId)
                                .let { session ->
                                    if (session == null) {
                                        SessionRepository.createSession(Session(chatId))
                                        StartGameUseCase(repositories).run()
                                    }
                                }
                    }
                    "end" -> {
                        SessionRepository.deleteSession(chatId)
                    }
                    "points" -> {
                        MessageRepository(bot, chatId)
                                .createPointsMessage(
                                        IMessageRepository.PointsMessage(
                                                PointsRepository(chatId)
                                                        .getAll()
                                                        .toMap()
                                        )
                                )
                    }
                    else -> AnswerUseCase(
                            repositories,
                            text,
                            Player(0, update.message.from?.firstName!!)
                    ).run()
                }
            }
        }
    }
    Logger.log = LoggerFactory.getLogger("TelegramBot")
    server.start(wait = true)
}