package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.repository.IMessageRepository

class NewRoundUseCase(
        repositories: Repositories
) : UseCase(repositories) {

    override fun run() {
        val question = repositories
                .questionRepository
                .getNextQuestion()

        repositories
                .roundRepository
                .createRound(Round(question))

        repositories
                .messageRepository
                .createNewQuestionMessage(
                        IMessageRepository.QuestionMessage(question.question)
                )
    }

}