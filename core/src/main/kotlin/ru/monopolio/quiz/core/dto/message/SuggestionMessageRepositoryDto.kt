package ru.monopolio.quiz.core.dto.message

class SuggestionMessageRepositoryDto(
        chatId: Long,
        val suggestion: String
) : MessageRepositoryDto(chatId)