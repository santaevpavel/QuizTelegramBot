package ru.monopolio.quiz.core.scheduler

import java.util.concurrent.TimeUnit

interface IScheduler {

    fun <T> schedule(time: Long, timeUnit: TimeUnit, action: suspend () -> T): Job

}

abstract class Job(
        val id: Long
) {
    abstract fun cancel()
}