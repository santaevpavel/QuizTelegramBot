package ru.monopolio.quiz.question

interface QuestionSource {

    fun getQuestions(): Sequence<Question>
}