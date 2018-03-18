package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.repository.IMessageRepository

class NewRoundUseCase(
        repositories: Repositories,
        private val chatId: Long
) : UseCase(repositories) {

    override suspend fun run() {
        val session = repositories
                .sessionRepository
                .getSessionByChat(chatId) ?: throw IllegalStateException("Not found session for chat $chatId")

        val question = repositories
                .questionRepository
                .getNextQuestion()

        repositories
                .roundRepository
                .createRound(Round(session, question))

        repositories
                .messageRepository
                .createNewQuestionMessage(
                        IMessageRepository.QuestionMessage(session.chatId, question.question)
                )
    }

}