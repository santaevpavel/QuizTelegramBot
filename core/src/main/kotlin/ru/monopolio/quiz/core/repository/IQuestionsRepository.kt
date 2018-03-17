package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.entity.Question

interface IQuestionsRepository {

    fun getNextQuestion(): Question
}