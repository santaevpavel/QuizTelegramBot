package ru.monopolio.quiz.core.repository

interface ISessionRepository {

    fun createSession(): Long

    fun getSession(id: Long)

}