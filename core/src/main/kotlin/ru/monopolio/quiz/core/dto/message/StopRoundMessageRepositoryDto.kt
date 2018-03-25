package ru.monopolio.quiz.core.dto.message

class StopRoundMessageRepositoryDto(
        chatId: Long,
        val answer: String
) : MessageRepositoryDto(chatId)