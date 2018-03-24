package ru.monopolio.quiz.core.entity

data class Round(
        val id: Long,
        val session: Session,
        val question: Question,
        val isFinished: Boolean
) {

    constructor(
            session: Session,
            question: Question
    ) : this(-1, session, question, false)
}