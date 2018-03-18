package ru.monopolio.quiz.telegram.repositories

import ru.monopolio.quiz.core.entity.Session
import ru.monopolio.quiz.core.repository.ISessionRepository

object SessionRepository : ISessionRepository {

    private val list = mutableListOf<Session>()
    private var nextId = 0L

    override fun createSession(session: Session): Session {
        list.add(session)
        nextId++
        return session.copy(id = nextId)
    }

    override fun getSessionByChat(chatId: Long): Session? = list.firstOrNull { it.chatId == chatId }

    override fun deleteSession(id: Long) {
        list.removeIf { it.id == id }
    }

    override fun getSession(id: Long) = list.first { it.id == id }

}