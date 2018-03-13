package ru.monopolio.quiz.telegram

import ru.monopolio.quiz.telegram.entities.Message
import ru.monopolio.quiz.telegram.requests.SendMessageRequest

interface ITelegramBot {

    suspend fun sendMessage(request: SendMessageRequest): TelegramResponse<Message>
}