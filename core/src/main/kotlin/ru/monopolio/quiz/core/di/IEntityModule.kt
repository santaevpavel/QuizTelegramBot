package ru.monopolio.quiz.core.di

import ru.monopolio.quiz.core.entity.Question
import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.entity.Session

internal interface IEntityModule {

    val session: Session
    val round: Round
    val question: Question
}