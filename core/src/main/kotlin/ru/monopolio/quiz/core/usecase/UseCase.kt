package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.repository.IMessageRepository
import ru.monopolio.quiz.core.repository.IPointsRepository
import ru.monopolio.quiz.core.repository.IQuestionsRepository
import ru.monopolio.quiz.core.repository.IRoundRepository

abstract class UseCase(
        val repositories: Repositories
) {
    abstract fun run()
}

data class Repositories(
        val roundRepository: IRoundRepository,
        val questionRepository: IQuestionsRepository,
        val messageRepository: IMessageRepository,
        val pointsRepository: IPointsRepository
)