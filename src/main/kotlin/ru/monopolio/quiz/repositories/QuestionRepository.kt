package ru.monopolio.quiz.repositories

import ru.monopolio.quiz.core.dto.QuestionDto
import ru.monopolio.quiz.core.repository.IQuestionsRepository
import ru.monopolio.quiz.question.Question
import ru.monopolio.quiz.question.QuestionSource

object QuestionRepository : IQuestionsRepository {

    var questionSource: QuestionSource? = null
        set(value) {
            questions = value?.getQuestions()?.iterator()
            field = value
        }
    private var questions: Iterator<Question>? = null

    override fun getNextQuestion(): QuestionDto {
        questions.let { questions ->
            if (questions == null || questions.hasNext().not()) {
                return QuestionDto(0, "No questions", "")
            }
            val next = questions.next()
            return QuestionDto(0, next.question, next.answer)
        }
    }

}