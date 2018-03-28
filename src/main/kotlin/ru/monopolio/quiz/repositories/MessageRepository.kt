package ru.monopolio.quiz.repositories

import kotlinx.coroutines.experimental.launch
import ru.monopolio.quiz.core.dto.message.*
import ru.monopolio.quiz.core.repository.IMessageRepository
import ru.monopolio.quiz.telegram.TelegramBot
import ru.monopolio.quiz.telegram.requests.SendMessageRequest

class MessageRepository(
        private val bot: TelegramBot
) : IMessageRepository {

    override fun createCreateGameMessage(message: CreateGameMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "*Новая игра!*") }
    }

    override fun createWinnerMessage(message: WinnerMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "Выиграл *${message.player}*") }
    }

    override fun createNewQuestionMessage(message: QuestionMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "*Новый вопрос*: ${message.question}") }
    }

    override fun createStopRoundMessage(message: StopRoundMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "Никто не ответил на вопрос. Правильный ответ: *${message.answer}*") }
    }

    override fun createStopRoundMessage2(message: StopRoundMessageRepositoryDto) {
        launch { sendMessage(message.chatId, "*Раунд закончился*") }
    }

    override fun createSuggestionMessage(message: SuggestionMessageRepositoryDto) {
        val suggestion = StringBuilder()
        message.suggestion.forEach { char ->
            if (char != '*') {
                suggestion.append("_${char}_ ")
            } else {
                suggestion.append("\\_ ")
            }
        }
        launch { sendMessage(message.chatId, "_Подсказка:_\n $suggestion") }
    }

    private suspend fun sendMessage(chatId: Long, msg: String) {
        bot.sendMessage(SendMessageRequest(chatId, msg))
    }

}