package ru.monopolio.quiz.core.dto

open class MessageDto(
        val chatId: Long,
        val message: String,
        val playerDto: PlayerDto
)