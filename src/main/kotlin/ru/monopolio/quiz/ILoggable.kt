package ru.monopolio.quiz

interface ILoggable {

    fun log() = Logger.log
}

object Logger {

    lateinit var log: org.slf4j.Logger
}