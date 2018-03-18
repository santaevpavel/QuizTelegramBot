package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.entity.Player
import ru.monopolio.quiz.core.repository.IMessageRepository

class AnswerUseCase(
        repositories: Repositories,
        private val chatId: Long,
        private val answer: String,
        private val player: Player
): UseCase(repositories) {

    override suspend fun run() {
        val session = repositories
                .sessionRepository
                .getSessionByChat(chatId) ?: throw IllegalStateException("Not found session for chat $chatId")

        val round = repositories
                .roundRepository
                .getLatestRound(session)

        if (round.question.answer == answer) {
            repositories
                    .messageRepository
                    .createWinnerMessage(IMessageRepository.WinnerMessage(chatId, player.name))
            /*val points = repositories
                    .pointsRepository
                    .getPoints(player)
            repositories
                    .pointsRepository
                    .setPoints(player, points + 1)*/
            startNewRound()
        }
    }

    private suspend fun startNewRound() {
        NewRoundUseCase(repositories, chatId).run()
    }

}