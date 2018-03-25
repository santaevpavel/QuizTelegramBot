package ru.monopolio.quiz.question.source

import ru.monopolio.quiz.question.Question
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * See http://viquiz.ru/wiki/skachat-voprosy-viktoriny
 */
class ViquizQuestionSourceParser {

    fun read(path: String): List<Question> {
        val scanner = Scanner(File(javaClass.classLoader.getResource(path).path))
        val questions = ArrayList<Question>()
        scanner.use {
            while (scanner.hasNextLine()) {
                val split = scanner.nextLine().split("|")
                if (split.size < 2) {
                    continue
                }
                val question = split[0]
                val answer = split[1]
                questions.add(Question(question, answer))
            }
        }
        return questions
    }
}