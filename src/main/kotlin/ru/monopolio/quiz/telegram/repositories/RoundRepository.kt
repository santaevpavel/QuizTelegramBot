package ru.monopolio.quiz.telegram.repositories

import ru.monopolio.quiz.core.dto.RoundDto
import ru.monopolio.quiz.core.dto.SessionDto
import ru.monopolio.quiz.core.repository.IRoundRepository

object RoundRepository : IRoundRepository {

    private var nextId = 0L
    private val rounds: MutableList<RoundDto> = mutableListOf()

    override fun createRound(round: RoundDto): RoundDto {
        nextId++
        return round
                .copy(id = nextId)
                .also {
                    rounds.add(it)
                    log()
                }
    }

    override fun getLatestRound(session: SessionDto): RoundDto {
        return rounds.last { it.session.id == session.id }.also { log() }
    }

    override fun updateRound(round: RoundDto) {
        rounds
                .indexOfFirst { it.id == round.id }
                .takeIf { it != -1 }
                ?.let { rounds.set(it, round) }
                .also { log() }
    }

    override fun getRound(id: Long): RoundDto? = rounds.firstOrNull { it.id == id }

    private fun log() {
        println("------")
        rounds.forEach { r ->
            println(r)
        }
        println("------")
    }
}