package ru.monopolio.quiz.core.repository

import ru.monopolio.quiz.core.dto.message.*

interface IMessageRepository {

    fun createCreateGameMessage(message: CreateGameMessageRepositoryDto)

    fun createWinnerMessage(message: WinnerMessageRepositoryDto)

    fun createNewQuestionMessage(message: QuestionMessageRepositoryDto)

    fun createStopRoundMessage(message: StopRoundMessageRepositoryDto)

    fun createStopRoundMessage2(message: StopRoundMessageRepositoryDto)

    fun createSuggestionMessage(message: SuggestionMessageRepositoryDto)

}