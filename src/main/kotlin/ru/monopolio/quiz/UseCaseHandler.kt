package ru.monopolio.quiz

import kotlinx.coroutines.experimental.launch
import ru.monopolio.quiz.core.usecase.UseCase

class UseCaseHandler {

    fun handle(useCase: UseCase) {
        launch {
            useCase.run()
        }
    }
}