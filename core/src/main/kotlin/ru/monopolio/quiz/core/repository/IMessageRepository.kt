package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.entity.Player

interface IMessageRepository {

    fun createCreateGameMessage(message: CreateGameMessage)

    fun createWinnerMessage(message: WinnerMessage)

    fun createNewQuestionMessage(message: QuestionMessage)

    fun createPointsMessage(message: PointsMessage)

    open class Message(val chatId: Long)

    class CreateGameMessage(chatId: Long) : Message(chatId)

    class WinnerMessage(
            chatId: Long,
            val player: String
    ) : Message(chatId)

    class QuestionMessage(
            chatId: Long,
            val question: String
    ) : Message(chatId)

    class PointsMessage(
            chatId: Long,
            val points: Map<Player, Int>
    ) : Message(chatId)
}