package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.Application

class StopGameUseCase(
        private val chatId: Long
) : UseCase<Unit>() {

    private val session = Application.entityModule.session

    override suspend fun run() {
        this.session.stopSession(chatId)
    }

}