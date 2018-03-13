package ru.monopolio.quiz

import io.ktor.application.*
import io.ktor.content.readText
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import ru.monopolio.quiz.properties.LocalProperties

fun main(args: Array<String>) {
    val token = LocalProperties().token
    val server = embeddedServer(Jetty, port = 9000) {
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            post("/$token") {
                val body = call.request.receiveContent().readText()
                log.debug("webhook called: $body")
                call.respond(HttpStatusCode.OK, "OK")
            }
        }
    }
    server.start(wait = true)
}