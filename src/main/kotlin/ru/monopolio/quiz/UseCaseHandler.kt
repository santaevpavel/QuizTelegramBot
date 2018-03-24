package ru.monopolio.quiz

import kotlinx.coroutines.experimental.launch
import ru.monopolio.quiz.core.usecase.UseCase

class UseCaseHandler {

    private val lock = "123"

    fun handle(useCase: UseCase<*>) {
        launch {
            synchronized(lock) {
                useCase.run()
            }
        }
    }
}