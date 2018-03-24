package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.entity.Session

interface IRoundRepository {

    fun createRound(round: Round): Round

    fun updateRound(round: Round)

    fun getLatestRound(session: Session): Round

}