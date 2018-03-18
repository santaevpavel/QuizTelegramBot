package ru.monopolio.quiz.telegram.repositories

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import ru.monopolio.quiz.core.repository.IMessageRepository
import ru.monopolio.quiz.telegram.TelegramBot
import ru.monopolio.quiz.telegram.requests.SendMessageRequest
import java.util.concurrent.TimeUnit

class MessageRepository(
        private val bot: TelegramBot
) : IMessageRepository {

    override fun createPointsMessage(message: IMessageRepository.PointsMessage) {
        var msg = ""
        message
                .points
                .forEach { pair ->
                    msg += "${pair.key} - ${pair.value} очков\n"
                }

        launch { sendMessage(message.chatId, msg) }
    }

    override fun createCreateGameMessage(message: IMessageRepository.CreateGameMessage) {
        launch { sendMessage(message.chatId, "Новая игра!") }
    }

    override fun createWinnerMessage(message: IMessageRepository.WinnerMessage) {
        launch { sendMessage(message.chatId, "Выиграл ${message.player}") }
    }

    override fun createNewQuestionMessage(message: IMessageRepository.QuestionMessage) {
        launch {
            delay(3, TimeUnit.SECONDS)
            sendMessage(message.chatId, "Новый вопрос: ${message.question}")
        }
    }

    private suspend fun sendMessage(chatId: Long, msg: String) {
        bot.sendMessage(SendMessageRequest(chatId, msg))
    }

}