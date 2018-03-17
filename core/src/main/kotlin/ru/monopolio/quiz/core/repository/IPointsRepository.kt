package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.entity.Player

interface IPointsRepository {

    fun setPoints(player: Player, points: Int)

    fun getPoints(player: Player): Int

}