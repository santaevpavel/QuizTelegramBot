package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.dto.SessionDto

interface ISessionRepository {

    fun createSession(session: SessionDto): SessionDto

    fun deleteSession(id: Long)

    fun getSession(id: Long): SessionDto?

    fun getSessionByChat(chatId: Long): SessionDto?
}