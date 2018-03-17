package ru.monopolio.quiz.telegram.repositories

import ru.monopolio.quiz.core.entity.Question
import ru.monopolio.quiz.core.repository.IQuestionsRepository
import java.util.*

object QuestionRepository: IQuestionsRepository {

    override fun getNextQuestion(): Question {
        val a = Random().nextInt(100)
        val b = Random().nextInt(100)
        return Question("$a + $b?", "${a + b}")
    }

}