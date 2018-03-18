package ru.monopolio.quiz.core.usecase

class StopGameUseCase(
        repositories: Repositories,
        private val chatId: Long
) : UseCase(repositories) {

    override fun run() {
        val repository = repositories
                .sessionRepository

        repository
                .getSessionByChat(chatId)
                ?.let { repository.deleteSession(it.id) }
    }

}