package com.example.qparty.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.qparty.model.Question
import com.example.qparty.util.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private val allQuestions: List<Question> =
        QuestionRepository.loadQuestions(application.applicationContext)

    private var questionList = allQuestions.shuffled().toMutableList()
    private var index = 0

    private val _currentQuestion = MutableStateFlow<Question?>(questionList.firstOrNull())
    val currentQuestion = _currentQuestion.asStateFlow()

    fun nextQuestion() {
        index++
        if (index < questionList.size) {
            _currentQuestion.value = questionList[index]
        } else {
            _currentQuestion.value = null
        }
    }

    fun restartGame() {
        questionList = allQuestions.shuffled().toMutableList()
        index = 0
        _currentQuestion.value = questionList.firstOrNull()
    }
}