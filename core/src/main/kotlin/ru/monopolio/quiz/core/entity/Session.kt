package ru.monopolio.quiz.core.entity

import ru.monopolio.quiz.core.dto.MessageDto
import ru.monopolio.quiz.core.dto.SessionDto
import ru.monopolio.quiz.core.dto.message.CreateGameMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.WinnerMessageRepositoryDto
import ru.monopolio.quiz.core.repository.Repositories
import ru.monopolio.quiz.core.scheduler.IScheduler
import ru.monopolio.quiz.core.utils.log
import java.util.concurrent.TimeUnit

internal class Session(
        private val repositories: Repositories,
        private val round: Round,
        private val question: Question,
        private val scheduler: IScheduler
) {

    fun startSession(chatId: Long) {
        val session = repositories
                .sessionRepository.getSessionByChat(chatId)

        if (session == null) {
            val newSession = repositories
                    .sessionRepository
                    .createSession(SessionDto(-1, chatId))
            repositories.messageRepository.createCreateGameMessage(CreateGameMessageRepositoryDto(chatId))
            scheduleStartNewRound(newSession.id)
        } else {
            log("Session already exist for chat $chatId")
        }
    }

    fun stopSession(chatId: Long) {
        val session = repositories
                .sessionRepository.getSessionByChat(chatId) ?: return

        round.stopLatestRound(session)

        repositories.sessionRepository.deleteSession(session.id)
    }

    fun onAnswer(chatId: Long, message: MessageDto) {
        val session = repositories
                .sessionRepository.getSessionByChat(chatId) ?: return

        val round = repositories.roundRepository.getLatestRound(session) ?: return

        if (round.isFinished) return

        if (question.checkAnswer(round, message)) {
            repositories
                    .messageRepository
                    .createWinnerMessage(WinnerMessageRepositoryDto(session.chatId, message.playerDto.name))
            this.round.stopRound(session, round, true)
        }
    }

    private fun scheduleStartNewRound(sessionId: Long) {
        scheduler.schedule(3, TimeUnit.SECONDS) {
            repositories
                    .sessionRepository
                    .getSession(sessionId)
                    ?.let {
                        round.startRound(it)
                    }
        }
    }

}