package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.dto.message.CreateGameMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.QuestionMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.StopRoundMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.WinnerMessageRepositoryDto

interface IMessageRepository {

    fun createCreateGameMessage(message: CreateGameMessageRepositoryDto)

    fun createWinnerMessage(message: WinnerMessageRepositoryDto)

    fun createNewQuestionMessage(message: QuestionMessageRepositoryDto)

    fun createStopRoundMessage(message: StopRoundMessageRepositoryDto)

    fun createStopRoundMessage2(message: StopRoundMessageRepositoryDto)

}