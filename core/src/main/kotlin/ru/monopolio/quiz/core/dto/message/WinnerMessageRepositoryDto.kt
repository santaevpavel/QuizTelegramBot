package ru.monopolio.quiz.core.dto.message

class WinnerMessageRepositoryDto(
        chatId: Long,
        val player: String
) : MessageRepositoryDto(chatId)