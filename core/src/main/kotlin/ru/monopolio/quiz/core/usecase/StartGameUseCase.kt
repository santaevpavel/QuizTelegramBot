package ru.monopolio.quiz.core.usecase

class StartGameUseCase(
        repositories: Repositories
) : UseCase(repositories) {

    override fun run() {
        NewRoundUseCase(repositories).run()
    }

}