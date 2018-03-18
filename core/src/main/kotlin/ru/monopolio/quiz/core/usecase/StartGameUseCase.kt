package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.entity.Session

class StartGameUseCase(
        repositories: Repositories,
        private val chatId: Long
) : UseCase(repositories) {

    override fun run() {
        val repository = repositories
                .sessionRepository
        val session = repository.getSessionByChat(chatId)

        if (session == null) {
            repository.createSession(Session(chatId))
            NewRoundUseCase(
                    repositories,
                    chatId
            ).run()
        }

    }

}