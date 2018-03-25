package ru.monopolio.quiz.core.di

import ru.monopolio.quiz.core.repository.Repositories
import ru.monopolio.quiz.core.scheduler.IScheduler

interface IAppModule {

    val repositories: Repositories

    val scheduler: IScheduler
}