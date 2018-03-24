package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.entity.Round
import ru.monopolio.quiz.core.repository.IMessageRepository
import java.util.concurrent.TimeUnit

class StopRoundUseCase(
        repositories: Repositories,
        private val chatId: Long,
        private val round: Round? = null,
        private val timeout: Boolean
) : UseCase<Unit>(repositories) {

    override suspend fun run() {
        val session = repositories
                .sessionRepository
                .getSessionByChat(chatId) ?: throw IllegalStateException("Not found session for chat $chatId")

        val latestRound = repositories
                .roundRepository
                .getLatestRound(session)

        if (latestRound.isFinished) return

        if (round == latestRound || round == null) {
            repositories
                    .roundRepository
                    .updateRound(latestRound.copy(isFinished = true))
            if (timeout) {
                repositories
                        .messageRepository
                        .createStopRoundMessage(IMessageRepository.StopRoundMessage(chatId, latestRound.question.answer))
            } else {
                repositories
                        .messageRepository
                        .createStopRoundMessage2(IMessageRepository.StopRoundMessage(chatId, latestRound.question.answer))
            }
            scheduleNewRoundUseCase()
        }
        return
    }

    private fun scheduleNewRoundUseCase() {
        scheduler.schedule(5, TimeUnit.SECONDS) {
            CreateRoundUseCase(repositories, chatId).run()
        }
    }
}