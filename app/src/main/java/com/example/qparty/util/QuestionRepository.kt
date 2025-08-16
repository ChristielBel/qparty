package com.example.qparty.util

import android.content.Context
import com.example.qparty.model.Question
import kotlinx.serialization.json.Json

object QuestionRepository {
    fun loadQuestions(context: Context): List<Question> {
        val inputStream = context.assets.open("questions.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }
}