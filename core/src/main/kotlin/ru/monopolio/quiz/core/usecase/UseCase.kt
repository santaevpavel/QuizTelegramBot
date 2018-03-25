package ru.monopolio.quiz.core.usecase

abstract class UseCase<out T> {

    abstract suspend fun run(): T
}