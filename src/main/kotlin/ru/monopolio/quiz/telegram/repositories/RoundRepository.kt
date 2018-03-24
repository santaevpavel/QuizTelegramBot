package ru.monopolio.quiz.telegram.repositories

import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.entity.Session
import ru.monopolio.quiz.core.repository.IRoundRepository

object RoundRepository : IRoundRepository {

    private var nextId = 0L
    private val rounds: MutableList<Round> = mutableListOf()

    override fun createRound(round: Round): Round {
        nextId++
        return round
                .copy(id = nextId)
                .also {
                    rounds.add(it)
                    log()
                }
    }

    override fun getLatestRound(session: Session): Round {
        return rounds.last { it.session.id == session.id }.also { log() }
    }

    override fun updateRound(round: Round) {
        rounds
                .indexOfFirst { it.id == round.id }
                .takeIf { it != -1 }
                ?.let { rounds.set(it, round) }
                .also { log() }
    }

    private fun log() {
        println("------")
        rounds.forEach { r ->
            println(r)
        }
        println("------")
    }
}