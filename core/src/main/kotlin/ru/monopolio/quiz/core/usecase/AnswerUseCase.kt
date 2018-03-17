package ru.monopolio.quiz.core.usecase

import ru.monopolio.quiz.core.entity.Player
import ru.monopolio.quiz.core.repository.IMessageRepository

class AnswerUseCase(
        repositories: Repositories,
        private val answer: String,
        private val player: Player
): UseCase(repositories) {

    override fun run() {
        val round = repositories
                .roundRepository
                .getLatestRound()
        if (round.question.answer == answer) {
            repositories
                    .messageRepository
                    .createWinnerMessage(IMessageRepository.WinnerMessage(player.name))
            val points = repositories
                    .pointsRepository
                    .getPoints(player)
            repositories
                    .pointsRepository
                    .setPoints(player, points + 1)
            startNewRound()
        }
    }

    private fun startNewRound() {
        NewRoundUseCase(repositories).run()
    }

}