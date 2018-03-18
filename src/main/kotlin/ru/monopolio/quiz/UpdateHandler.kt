package ru.monopolio.quiz

import ru.monopolio.quiz.core.entity.Player
import ru.monopolio.quiz.core.usecase.AnswerUseCase
import ru.monopolio.quiz.core.usecase.Repositories
import ru.monopolio.quiz.core.usecase.StartGameUseCase
import ru.monopolio.quiz.core.usecase.StopGameUseCase
import ru.monopolio.quiz.telegram.entities.Update

class UpdateHandler(
        private val useCaseHandler: UseCaseHandler,
        private val repositories: Repositories
) {

    fun handleUpdate(update: Update) {
        val text = update.message?.text ?: return
        val chatId = update.message.chat.id

        val useCase = when (text.toLowerCase()) {
            "start" -> {
                StartGameUseCase(repositories, chatId)
            }
            "end" -> {
                StopGameUseCase(repositories, chatId)
            }
            "points" -> throw NotImplementedError()
            else -> {
                AnswerUseCase(
                        repositories,
                        chatId,
                        update.message.text,
                        Player(-1, update.message.from?.firstName!!)
                )
            }
        }
        useCaseHandler.handle(useCase)
    }
}