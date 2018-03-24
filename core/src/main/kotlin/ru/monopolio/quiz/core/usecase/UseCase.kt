package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.repository.*
import ru.monopolio.quiz.core.scheduler.IScheduler
import ru.monopolio.quiz.core.scheduler.Scheduler

abstract class UseCase<out T>(
        val repositories: Repositories
) {

    protected val scheduler: IScheduler by lazy { Scheduler() }

    abstract suspend fun run(): T
}

data class Repositories(
        val roundRepository: IRoundRepository,
        val questionRepository: IQuestionsRepository,
        val messageRepository: IMessageRepository,
        val pointsRepository: IPointsRepository,
        val sessionRepository: ISessionRepository
)