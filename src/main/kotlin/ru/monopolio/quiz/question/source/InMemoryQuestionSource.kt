package ru.monopolio.quiz.question.source

import io.ktor.util.nonceRandom
import ru.monopolio.quiz.question.Question
import ru.monopolio.quiz.question.QuestionSource
import kotlin.coroutines.experimental.buildSequence

class InMemoryQuestionSource(
        private val questions: List<Question>
) : QuestionSource {

    override fun getQuestions(): Sequence<Question> {
        return buildSequence {
            while (true) {
                val question = questions[nonceRandom.nextInt(questions.size)]
                yield(question)
            }
        }
    }
}