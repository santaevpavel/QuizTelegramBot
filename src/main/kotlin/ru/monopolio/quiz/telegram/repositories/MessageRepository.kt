package ru.monopolio.quiz.telegram.repositories

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import ru.monopolio.quiz.core.repository.IMessageRepository
import ru.monopolio.quiz.telegram.TelegramBot
import ru.monopolio.quiz.telegram.requests.SendMessageRequest
import java.util.concurrent.TimeUnit

class MessageRepository(
        val bot: TelegramBot,
        val chatId: Long
) : IMessageRepository {

    override fun createPointsMessage(message: IMessageRepository.PointsMessage) {
        var msg = ""
        message
                .points
                .forEach { pair ->
                    msg += "${pair.key} - ${pair.value} очков\n"
                }

        launch { sendMessage(msg) }
    }

    override fun createCreateGameMessage(message: IMessageRepository.CreateGameMessage) {
        launch { sendMessage("Новая игра!") }
    }

    override fun createWinnerMessage(message: IMessageRepository.WinnerMessage) {
        launch { sendMessage("Выиграл ${message.player}") }
    }

    override fun createNewQuestionMessage(message: IMessageRepository.QuestionMessage) {
        launch {
            delay(3, TimeUnit.SECONDS)
            sendMessage("Новый вопрос: ${message.question}")
        }
    }

    private suspend fun sendMessage(msg: String) {
        bot.sendMessage(SendMessageRequest(chatId, msg))
    }

}