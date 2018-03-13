package ru.monopolio.quiz.telegram.entities

import com.google.gson.annotations.SerializedName

data class Update(
        @SerializedName("update_id") val updateId: Long,
        @SerializedName("message") val message: Message?,
        @SerializedName("edited_message") val editedMessage: Message?,
        @SerializedName("channel_post") val channelPost: Message?,
        @SerializedName("edited_channel_post") val editedChannelPost: Message?
)

