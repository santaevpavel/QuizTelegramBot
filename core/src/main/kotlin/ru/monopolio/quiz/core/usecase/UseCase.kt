package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.repository.*

abstract class UseCase(
        val repositories: Repositories
) {
    abstract suspend fun run()
}

data class Repositories(
        val roundRepository: IRoundRepository,
        val questionRepository: IQuestionsRepository,
        val messageRepository: IMessageRepository,
        val pointsRepository: IPointsRepository,
        val sessionRepository: ISessionRepository
)