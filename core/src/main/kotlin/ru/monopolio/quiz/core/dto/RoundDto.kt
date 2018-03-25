package ru.monopolio.quiz.core.dto

data class RoundDto(
        val id: Long,
        val session: SessionDto,
        val question: QuestionDto,
        val isFinished: Boolean
) {

    companion object {

        fun newRound(
                session: SessionDto,
                question: QuestionDto
        ) = RoundDto(-1, session, question, false)
    }
}