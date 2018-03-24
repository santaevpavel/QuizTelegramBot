package ru.monopolio.quiz.core.scheduler

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

class Scheduler : IScheduler {

    override fun <T> schedule(
            time: Long,
            timeUnit: TimeUnit,
            action: suspend () -> T
    ): Job {
        val job = launch {
            delay(time, timeUnit)
            action()
        }
        return JobInternal(0, job)
    }

    class JobInternal(
            id: Long,
            private val job: kotlinx.coroutines.experimental.Job
    ) : Job(id) {

        override fun cancel() {
            job.cancel()
        }
    }

}