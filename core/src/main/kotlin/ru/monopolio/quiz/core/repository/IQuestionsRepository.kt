package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.dto.QuestionDto

interface IQuestionsRepository {

    fun getNextQuestion(): QuestionDto
}