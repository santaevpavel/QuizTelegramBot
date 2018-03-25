package ru.monopolio.quiz.core.dto.message

class QuestionMessageRepositoryDto(
        chatId: Long,
        val question: String
) : MessageRepositoryDto(chatId)