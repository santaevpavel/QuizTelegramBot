package ru.monopolio.quiz.telegram.repositories

abstract class BaseSessionRepository<T>(
        private val sessionId: Long,
        private val store: MutableMap<Long, MutableList<T>>
) {

    fun getAll(): MutableList<T> {
        return store[sessionId]
                ?: return mutableListOf<T>()
                .apply {
                    store.put(sessionId, this)
                }
    }

    fun add(entity: T) {
        getAll().add(entity)
    }


}