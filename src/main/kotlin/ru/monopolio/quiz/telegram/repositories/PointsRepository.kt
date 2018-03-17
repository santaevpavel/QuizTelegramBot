package ru.monopolio.quiz.telegram.repositories

import ru.monopolio.quiz.core.entity.Player
import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.repository.IPointsRepository
import ru.monopolio.quiz.core.repository.IRoundRepository

class PointsRepository(
        sessionId: Long
) : BaseSessionRepository<Pair<Player, Int>>(sessionId, store), IPointsRepository {

    override fun setPoints(player: Player, points: Int) {
        getAll().removeIf {
            it.first == player
        }
        getAll().add(player to points)
    }

    override fun getPoints(player: Player): Int {
        return getAll().firstOrNull {
            it.first == player
        }?.second ?: 0
    }

    companion object {

        private val store: MutableMap<Long, MutableList<Pair<Player, Int>>> = mutableMapOf()
    }

}