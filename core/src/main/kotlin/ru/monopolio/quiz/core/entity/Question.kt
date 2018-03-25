package ru.monopolio.quiz.core.entity

import ru.monopolio.quiz.core.dto.MessageDto
import ru.monopolio.quiz.core.dto.RoundDto

internal class Question {

    fun checkAnswer(
            round: RoundDto,
            message: MessageDto
    ): Boolean {
        if (round.isFinished) return false

        return checkAnswer(round, message.message)
    }

    private fun checkAnswer(
            round: RoundDto,
            answer: String
    ): Boolean {
        if (round.isFinished) return false

        return round.question.answer == answer
    }
}

