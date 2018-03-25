package ru.monopolio.quiz.core.entity

import ru.monopolio.quiz.core.dto.RoundDto
import ru.monopolio.quiz.core.dto.SessionDto
import ru.monopolio.quiz.core.dto.message.QuestionMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.StopRoundMessageRepositoryDto
import ru.monopolio.quiz.core.repository.Repositories
import ru.monopolio.quiz.core.scheduler.IScheduler
import ru.monopolio.quiz.core.utils.log
import java.util.concurrent.TimeUnit

internal class Round(
        private val repositories: Repositories,
        private val scheduler: IScheduler
) {

    fun startRound(session: SessionDto) {
        val question = repositories
                .questionRepository
                .getNextQuestion()

        val round = repositories
                .roundRepository
                .createRound(RoundDto.newRound(session, question))

        repositories
                .messageRepository
                .createNewQuestionMessage(
                        QuestionMessageRepositoryDto(session.chatId, question.question)
                )

        scheduleStopRoundUseCase(session, round)
    }

    fun stopLatestRound(
            session: SessionDto
    ) {
        val round = repositories
                .roundRepository
                .getLatestRound(session) ?: return
        stopRound(session, round, false)
    }

    fun stopRound(
            session: SessionDto,
            round: RoundDto,
            scheduleNewRound: Boolean
    ): Boolean {
        if (round.isFinished) {
            log("Round $round is already finished")
            return false
        }

        repositories
                .roundRepository
                .updateRound(round.copy(isFinished = true))
        if (scheduleNewRound) {
            scheduleNewRoundUseCase(session)
        }
        return true
    }

    private fun scheduleNewRoundUseCase(session: SessionDto) {
        scheduler.schedule(3, TimeUnit.SECONDS) {
            startRound(session)
        }
    }

    private fun scheduleStopRoundUseCase(session: SessionDto, round: RoundDto) {
        scheduler.schedule(20, TimeUnit.SECONDS) {
            val roundFromRepository = repositories.roundRepository.getRound(round.id) ?: return@schedule
            if (stopRound(session, roundFromRepository, true)) {
                repositories
                        .messageRepository
                        .createStopRoundMessage(StopRoundMessageRepositoryDto(
                                session.chatId,
                                roundFromRepository.question.answer
                        ))
            }

        }
    }
}