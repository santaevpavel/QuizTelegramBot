package ru.monopolio.quiz.telegram.repositories

import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.repository.IRoundRepository

class RoundRepository(
        sessionId: Long
): BaseSessionRepository<Round>(sessionId, store), IRoundRepository {

    override fun createRound(round: Round) {
        add(round)
    }

    override fun getLatestRound(): Round {
        return getAll().last()
    }

    companion object {

        private val store: MutableMap<Long, MutableList<Round>> = mutableMapOf()
    }
}