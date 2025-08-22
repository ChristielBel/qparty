package com.example.qparty.viewmodel

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class QuestionViewModelTest {
    private lateinit var viewModel: QuestionViewModel

   @Before
   fun setup(){
       val context = ApplicationProvider.getApplicationContext<Application>()
       viewModel = QuestionViewModel(context)
   }

    @Test
    fun initialState_hasCurrentQuestion() {
        val question = viewModel.currentQuestion.value
        Assert.assertNotNull("Должен быть первый вопрос при старте", question)
    }

    @Test
    fun nextQuestion_changesQuestion() {
        val first = viewModel.currentQuestion.value
        viewModel.nextQuestion()
        val second = viewModel.currentQuestion.value
        Assert.assertNotEquals("Вопрос должен измениться", first, second)
    }

    @Test
    fun restartGame_resetsQuestions() {
        viewModel.nextQuestion()
        viewModel.restartGame()
        val restarted = viewModel.currentQuestion.value
        Assert.assertNotNull("После рестарта снова должен быть вопрос", restarted)
    }
}