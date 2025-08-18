package com.example.qparty.util

import android.content.Context
import com.example.qparty.model.Question
import kotlinx.serialization.json.Json

object QuestionRepository {

    private val questionFiles = listOf(
        "questions1.json",
        "questions2.json",
        "questions3.json"
    )

    fun loadQuestions(context: Context, totalQuestions: Int = 15): List<Question> {
        val allQuestionsPerFile = questionFiles.map { fileName ->
            val inputStream = context.assets.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString<List<Question>>(jsonString).shuffled()
        }

        val questionsPerFile = totalQuestions / questionFiles.size
        val remaining = totalQuestions % questionFiles.size

        val selectedQuestions = mutableListOf<Question>()

        allQuestionsPerFile.forEachIndexed { index, questions ->
            val count = questionsPerFile + if (index < remaining) 1 else 0
            selectedQuestions.addAll(questions.take(count))
        }

        return selectedQuestions.shuffled()
    }
}