package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.repository.IMessageRepository
import java.util.concurrent.TimeUnit

class CreateRoundUseCase(
        repositories: Repositories,
        private val chatId: Long
) : UseCase<Unit>(repositories) {

    override suspend fun run() {
        val session = repositories
                .sessionRepository
                .getSessionByChat(chatId) ?: throw IllegalStateException("Not found session for chat $chatId")

        val question = repositories
                .questionRepository
                .getNextQuestion()

        val round = repositories
                .roundRepository
                .createRound(Round(session, question))

        repositories
                .messageRepository
                .createNewQuestionMessage(
                        IMessageRepository.QuestionMessage(session.chatId, question.question)
                )

        scheduleStopRoundUseCase(round)
    }

    private fun scheduleStopRoundUseCase(round: Round) {
        scheduler.schedule(5, TimeUnit.SECONDS) {
            StopRoundUseCase(repositories, chatId, round, true).run()
        }
    }

}