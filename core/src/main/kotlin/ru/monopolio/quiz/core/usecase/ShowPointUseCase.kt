package ru.monopolio.quiz.core.usecase

class ShowPointUseCase(
        repositories: Repositories
) : UseCase<Unit>(repositories) {

    override suspend fun run() {
        /*val question = repositories
                .pointsRepository
                .getPoints()
                .getNextQuestion()*/
    }

}