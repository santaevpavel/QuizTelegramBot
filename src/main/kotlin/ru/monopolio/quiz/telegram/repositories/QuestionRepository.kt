package ru.monopolio.quiz.telegram.repositories

import ru.monopolio.quiz.core.dto.QuestionDto
import ru.monopolio.quiz.core.repository.IQuestionsRepository
import java.util.*

object QuestionRepository: IQuestionsRepository {

    override fun getNextQuestion(): QuestionDto {
        val a = Random().nextInt(100)
        val b = Random().nextInt(100)
        return QuestionDto(0, "$a + $b?", "${a + b}")
    }

}