package ru.monopolio.quiz

import ru.monopolio.quiz.core.dto.MessageDto
import ru.monopolio.quiz.core.dto.PlayerDto
import ru.monopolio.quiz.core.repository.Repositories
import ru.monopolio.quiz.core.usecase.AnswerUseCase
import ru.monopolio.quiz.core.usecase.StartGameUseCase
import ru.monopolio.quiz.core.usecase.StopGameUseCase
import ru.monopolio.quiz.telegram.entities.Update

class UpdateHandler(
        private val useCaseHandler: UseCaseHandler,
        private val repositories: Repositories
) {

    fun handle(update: Update) {
        val text = update.message?.text ?: return
        val chatId = update.message.chat.id

        val useCase = when (text.toLowerCase()) {
            "start" -> {
                StartGameUseCase(chatId)
            }
            "end" -> {
                StopGameUseCase(chatId)
            }
            "points" -> throw NotImplementedError()
            else -> {
                AnswerUseCase(
                        chatId,
                        MessageDto(chatId, update.message.text, PlayerDto(-1L, update.message.from?.firstName!!))
                )
            }
        }
        useCaseHandler.handle(useCase)
    }
}