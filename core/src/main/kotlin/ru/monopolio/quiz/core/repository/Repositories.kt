package ru.monopolio.quiz.core.repository

data class Repositories(
        val roundRepository: IRoundRepository,
        val questionRepository: IQuestionsRepository,
        val messageRepository: IMessageRepository,
        val sessionRepository: ISessionRepository
)