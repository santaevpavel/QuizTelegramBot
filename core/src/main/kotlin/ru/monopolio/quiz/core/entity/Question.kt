package ru.monopolio.quiz.core.entity

import ru.monopolio.quiz.core.dto.MessageDto
import ru.monopolio.quiz.core.dto.QuestionDto
import ru.monopolio.quiz.core.dto.RoundDto
import java.util.*
import kotlin.math.roundToInt

internal class Question {

    fun checkAnswer(
            round: RoundDto,
            message: MessageDto
    ): Boolean {
        if (round.isFinished) return false

        return checkAnswer(round, message.message)
    }

    fun getSuggestion(question: QuestionDto, partOfChars: Double): String {
        val length = question.answer.length
        val numberOfCharToShow = (length.toDouble() * partOfChars).roundToInt()
        val result = StringBuilder()

        question.answer.forEach { result.append('*') }
        val random = Random(question.answer.hashCode().toLong())

        for (i in 0 until numberOfCharToShow) {
            val idx = random.nextInt(length)
            result[idx] = question.answer[idx]
        }
        return result.toString()
    }

    private fun checkAnswer(
            round: RoundDto,
            answer: String
    ): Boolean {
        if (round.isFinished) return false

        return round.question.answer.toLowerCase() == answer.toLowerCase()
    }
}

