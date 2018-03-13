package ru.monopolio.quiz.telegram

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.monopolio.quiz.telegram.entities.Message
import ru.monopolio.quiz.telegram.requests.SendMessageRequest


interface ITelegramBotService {

    @POST("sendMessage")
    fun sendMessage(@Body() body: SendMessageRequest): Call<TelegramResponse<Message>>
}