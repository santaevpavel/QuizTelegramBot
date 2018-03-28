package ru.monopolio.quiz.core.entity

import ru.monopolio.quiz.core.dto.RoundDto
import ru.monopolio.quiz.core.dto.SessionDto
import ru.monopolio.quiz.core.dto.message.QuestionMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.StopRoundMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.SuggestionMessageRepositoryDto
import ru.monopolio.quiz.core.repository.Repositories
import ru.monopolio.quiz.core.scheduler.IScheduler
import ru.monopolio.quiz.core.utils.log
import java.util.concurrent.TimeUnit

internal class Round(
        private val repositories: Repositories,
        private val scheduler: IScheduler,
        private val question: Question
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

        scheduleSuggestions(session, round)
        scheduleStopRound(session, round)
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
            scheduleNewRound(session)
        }
        return true
    }

    private fun showSuggestion(
            session: SessionDto,
            round: RoundDto,
            partOfChars: Double
    ) {
        if (round.isFinished) return

        val suggestion = question.getSuggestion(round.question, partOfChars)

        repositories
                .messageRepository
                .createSuggestionMessage(SuggestionMessageRepositoryDto(
                        session.chatId,
                        suggestion
                ))
    }

    private fun scheduleNewRound(session: SessionDto) {
        scheduler.schedule(10, TimeUnit.SECONDS) {
            startRound(session)
        }
    }

    private fun scheduleStopRound(session: SessionDto, round: RoundDto) {
        scheduler.schedule(35, TimeUnit.SECONDS) {
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

    private fun scheduleSuggestions(session: SessionDto, round: RoundDto) {
        scheduler.schedule(5, TimeUnit.SECONDS) {
            repositories
                    .roundRepository
                    .getRound(round.id)
                    ?.let { showSuggestion(session, it, 0.0) }
        }
        scheduler.schedule(15, TimeUnit.SECONDS) {
            repositories
                    .roundRepository
                    .getRound(round.id)
                    ?.let { showSuggestion(session, it, 0.3) }
        }
        scheduler.schedule(25, TimeUnit.SECONDS) {
            repositories
                    .roundRepository
                    .getRound(round.id)
                    ?.let { showSuggestion(session, it, 0.7) }
        }
    }
}