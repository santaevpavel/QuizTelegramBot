package ru.monopolio.quiz.telegram

import com.google.gson.annotations.SerializedName

class TelegramResponse<out T>(
        @SerializedName("result") val result: T?,
        @SerializedName("ok") val ok: Boolean,
        @SerializedName("error_code") val errorCode: Int?,
        @SerializedName("description") val errorDescription: String?
)