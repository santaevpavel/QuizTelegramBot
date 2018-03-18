package ru.monopolio.quiz.telegram.repositories

import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.entity.Session
import ru.monopolio.quiz.core.repository.IRoundRepository

object RoundRepository : IRoundRepository {

    private var nextId = 0L
    private val rounds: MutableList<Round> = mutableListOf()

    override fun createRound(round: Round): Round {
        return round.apply {
            rounds.add(this)
            nextId++
            copy(id = nextId)
        }
    }

    override fun getLatestRound(session: Session): Round {
        return rounds.last { it.session.id == session.id }
    }
}