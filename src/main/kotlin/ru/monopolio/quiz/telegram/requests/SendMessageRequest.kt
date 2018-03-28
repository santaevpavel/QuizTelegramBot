package ru.monopolio.quiz.telegram.requests

import com.google.gson.annotations.SerializedName

data class SendMessageRequest(
        @SerializedName("chat_id") val chatId: Long,
        @SerializedName("text") val text: String,
        @SerializedName("parse_mode") val parseMode: String = "markdown"
)