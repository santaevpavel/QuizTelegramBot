package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.entity.Round

interface IRoundRepository {

    fun createRound(round: Round)

    fun getLatestRound(): Round

}