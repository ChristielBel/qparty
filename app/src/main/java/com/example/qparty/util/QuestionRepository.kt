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

    private var unusedQuestions: MutableList<Question> = mutableListOf()

    fun loadQuestions(context: Context, totalQuestions: Int = 15): List<Question> {
        if (unusedQuestions.size < totalQuestions) {
            unusedQuestions = loadAllQuestions(context).shuffled().toMutableList()
        }

        val selected = unusedQuestions.take(totalQuestions)
        unusedQuestions = unusedQuestions.drop(totalQuestions).toMutableList()

        return selected
    }

    private fun loadAllQuestions(context: Context): List<Question> {
        return questionFiles.flatMap { fileName ->
            val inputStream = context.assets.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString<List<Question>>(jsonString)
        }
    }
}