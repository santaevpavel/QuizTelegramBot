package ru.monopolio.quiz.telegram.entities

import com.google.gson.annotations.SerializedName

data class Chat(
        @SerializedName("id") val id: Long,
        @SerializedName("type") val type: String,
        @SerializedName("title") val title: String?,
        @SerializedName("username") val userName: String?,
        @SerializedName("first_name") val firstName: String?,
        @SerializedName("last_name") val lastName: String?)