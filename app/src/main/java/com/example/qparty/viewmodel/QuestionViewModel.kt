package com.example.qparty.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.qparty.model.Player
import com.example.qparty.model.Question
import com.example.qparty.util.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private var players: List<Player> = emptyList()
    private var currentPlayerIndex: Int = 0

    private var questionList: MutableList<Question> = mutableListOf()
    private var index = 0

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion = _currentQuestion.asStateFlow()

    init {
        restartGame()
    }

    fun setPlayers(newPlayers: List<Player>) {
        players = newPlayers
        currentPlayerIndex = 0
    }

    fun nextTurn() {
        if (players.isNotEmpty()) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size
        }
    }

    fun getCurrentPlayer(): Player? =
        if (players.isNotEmpty()) players[currentPlayerIndex] else null

    fun nextQuestion() {
        index++
        if (index < questionList.size) {
            _currentQuestion.value = questionList[index]
        } else {
            _currentQuestion.value = null
        }
    }

    fun restartGame() {
        questionList = QuestionRepository
            .loadQuestions(getApplication(), 15)
            .toMutableList()
        index = 0
        _currentQuestion.value = questionList.firstOrNull()
    }
}