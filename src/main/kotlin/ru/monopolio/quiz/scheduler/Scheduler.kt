package ru.monopolio.quiz.scheduler

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import ru.monopolio.quiz.core.scheduler.IScheduler
import ru.monopolio.quiz.core.scheduler.Job
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