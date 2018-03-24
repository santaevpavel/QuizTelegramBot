package ru.monopolio.quiz.core.usecase

class StopGameUseCase(
        repositories: Repositories,
        private val chatId: Long
) : UseCase<Unit>(repositories) {

    override suspend fun run() {
        val repository = repositories
                .sessionRepository

        repositories
                .sessionRepository
                .getSessionByChat(chatId) ?: return

        repository
                .getSessionByChat(chatId)
                ?.let { repository.deleteSession(it.id) }
    }

}