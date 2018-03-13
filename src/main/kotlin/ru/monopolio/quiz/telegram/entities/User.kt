package ru.monopolio.quiz.telegram.entities

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") val id: Long,
        @SerializedName("first_name") val firstName: String,
        @SerializedName("last_name") val lastName: String?,
        @SerializedName("username") val userName: String?)