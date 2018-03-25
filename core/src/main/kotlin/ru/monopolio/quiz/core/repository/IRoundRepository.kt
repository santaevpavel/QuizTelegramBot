package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.dto.RoundDto
import ru.monopolio.quiz.core.dto.SessionDto

interface IRoundRepository {

    fun createRound(round: RoundDto): RoundDto

    fun updateRound(round: RoundDto)

    fun getLatestRound(session: SessionDto): RoundDto?

    fun getRound(id: Long): RoundDto?

}