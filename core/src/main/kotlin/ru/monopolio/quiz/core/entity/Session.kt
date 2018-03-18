package ru.monopolio.quiz.core.entity

data class Session(
        val id: Long,
        val chatId: Long
) {
    constructor(chatId: Long) : this(-1, chatId)
}