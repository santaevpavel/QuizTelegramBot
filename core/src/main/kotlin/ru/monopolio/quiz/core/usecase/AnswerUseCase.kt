package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.entity.Player
import ru.monopolio.quiz.core.repository.IMessageRepository

class AnswerUseCase(
        repositories: Repositories,
        private val chatId: Long,
        private val answer: String,
        private val player: Player
) : UseCase<Unit>(repositories) {

    override suspend fun run() {
        val session = repositories
                .sessionRepository
                .getSessionByChat(chatId) ?: return

        val round = repositories
                .roundRepository
                .getLatestRound(session)

        if (round.isFinished) return

        if (round.question.answer == answer) {
            StopRoundUseCase(repositories, chatId, round, false).run()
            repositories
                    .messageRepository
                    .createWinnerMessage(IMessageRepository.WinnerMessage(chatId, player.name))
        }
    }

}