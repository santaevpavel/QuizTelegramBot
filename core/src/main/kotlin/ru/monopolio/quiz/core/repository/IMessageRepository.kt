package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.entity.Player

interface IMessageRepository {

    fun createCreateGameMessage(message: CreateGameMessage)

    fun createWinnerMessage(message: WinnerMessage)

    fun createNewQuestionMessage(message: QuestionMessage)

    fun createPointsMessage(message: PointsMessage)

    class CreateGameMessage

    data class WinnerMessage(
            val player: String
    )

    data class QuestionMessage(
            val question: String
    )

    data class PointsMessage(
            val points: Map<Player, Int>
    )
}