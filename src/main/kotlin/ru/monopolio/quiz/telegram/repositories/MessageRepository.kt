package ru.monopolio.quiz.telegram.repositories

import kotlinx.coroutines.experimental.launch
import ru.monopolio.quiz.core.dto.message.CreateGameMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.QuestionMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.StopRoundMessageRepositoryDto
import ru.monopolio.quiz.core.dto.message.WinnerMessageRepositoryDto
import ru.monopolio.quiz.core.repository.IMessageRepository
import ru.monopolio.quiz.telegram.TelegramBot
import ru.monopolio.quiz.telegram.requests.SendMessageRequest

class MessageRepository(
        private val bot: TelegramBot
) : IMessageRepository {

    override fun createCreateGameMessage(message: CreateGameMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "Новая игра!") }
    }

    override fun createWinnerMessage(message: WinnerMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "Выиграл ${message.player}") }
    }

    override fun createNewQuestionMessage(message: QuestionMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "Новый вопрос: ${message.question}") }
    }

    override fun createStopRoundMessage(message: StopRoundMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "Никто не ответил на вопрос. Правильный ответ: ${message.answer}") }
    }

    override fun createStopRoundMessage2(message: StopRoundMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "Раунд закончился") }
    }

    private suspend fun sendMessage(chatId: Long, msg: String) {
        bot.sendMessage(SendMessageRequest(chatId, msg))
    }

}