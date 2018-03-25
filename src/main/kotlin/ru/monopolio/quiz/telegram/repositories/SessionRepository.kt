package ru.monopolio.quiz.telegram.repositories

import ru.monopolio.quiz.core.dto.SessionDto
import ru.monopolio.quiz.core.repository.ISessionRepository

object SessionRepository : ISessionRepository {

    private val list = mutableListOf<SessionDto>()
    private var nextId = 0L

    override fun createSession(session: SessionDto): SessionDto {
        nextId++
        val newSession = session.copy(id = nextId)
        list.add(newSession)
        return newSession
    }

    override fun getSessionByChat(chatId: Long): SessionDto? = list.firstOrNull { it.chatId == chatId }

    override fun deleteSession(id: Long) {
        list.removeIf { it.id == id }
    }

    override fun getSession(id: Long) = list.firstOrNull { it.id == id }

}