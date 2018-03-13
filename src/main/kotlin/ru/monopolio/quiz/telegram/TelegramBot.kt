package ru.monopolio.quiz.telegram

import io.ktor.application.Application
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.monopolio.quiz.ILoggable
import ru.monopolio.quiz.telegram.entities.Message
import ru.monopolio.quiz.telegram.requests.SendMessageRequest


class TelegramBot(token: String): ITelegramBot, ILoggable {

    private val service: ITelegramBotService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.telegram.org/bot$token/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(ITelegramBotService::class.java)
    }

    suspend override fun sendMessage(request: SendMessageRequest): TelegramResponse<Message> {
        return call(service.sendMessage(request))!!
    }

    private fun <T> call(call: Call<T>): T? {
        val response = call.execute()
        log().debug(response.raw().toString())
        return response.body()
    }

}
