package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.Application
import ru.monopolio.quiz.core.dto.MessageDto

class AnswerUseCase(
        private val chatId: Long,
        private val message: MessageDto
) : UseCase<Unit>() {

    private val session = Application.entityModule.session

    override suspend fun run() {
        session.onAnswer(chatId, message)
    }

}