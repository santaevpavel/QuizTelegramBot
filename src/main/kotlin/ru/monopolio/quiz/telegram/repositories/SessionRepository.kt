package ru.monopolio.quiz.telegram.repositories

object SessionRepository{

    private val list = mutableListOf<Session>()

    fun createSession(session: Session) {
        list.add(session)
    }

    fun getSession(chatId: Long): Session? {
        return list.firstOrNull { it.chatId == chatId }
    }

    fun deleteSession(chatId: Long) {
        list.removeIf{ it.chatId == chatId }
    }

}

data class Session(var chatId: Long)