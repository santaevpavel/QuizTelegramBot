package ru.monopolio.quiz

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.content.readText
import io.ktor.features.CallLogging
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import org.slf4j.LoggerFactory
import ru.monopolio.quiz.properties.LocalProperties
import ru.monopolio.quiz.telegram.TelegramBot
import ru.monopolio.quiz.telegram.entities.Update
import ru.monopolio.quiz.telegram.requests.SendMessageRequest

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

                bot.sendMessage(SendMessageRequest(update.message!!.chat.id, update.message.text!!))
            }
        }
    }
    Logger.log = LoggerFactory.getLogger("TelegramBot")
    server.start(wait = true)
}