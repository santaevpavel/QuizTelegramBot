package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.entity.Session

interface ISessionRepository {

    fun createSession(session: Session): Session

    fun deleteSession(id: Long)

    fun getSession(id: Long): Session

    fun getSessionByChat(chatId: Long): Session?
}