package com.example.qparty.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.qparty.ui.screens.GameScreenContent
import com.example.qparty.ui.screens.QuestionCard
import com.example.qparty.ui.theme.QpartyTheme
import org.junit.Rule
import org.junit.Test
import com.example.qparty.R

class GameScreenTest {

    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val nextButton = context.getString(R.string.next_button)
    val restartButton = context.getString(R.string.restart_button)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun questionCard_showsText() {
        val testText = "test question"
        composeTestRule.setContent {
            QpartyTheme {
                QuestionCard(text = testText)
            }
        }

        composeTestRule.onNodeWithText(testText).assertIsDisplayed()
    }

    @Test
    fun nextButton_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            QpartyTheme {
                GameScreenContent(
                    question = "test question",
                    onNext = { clicked = true },
                    onRestart = {}
                )
            }
        }

        composeTestRule.onNodeWithText(nextButton).performClick()
        assert(clicked)
    }

    @Test
    fun nextButton_notShown_whenQuestionIsNull() {
        composeTestRule.setContent {
            QpartyTheme {
                GameScreenContent(
                    question = null,
                    onNext = { },
                    onRestart = { }
                )
            }
        }

        composeTestRule.onNodeWithText(nextButton).assertDoesNotExist()
    }

    @Test
    fun restartButton_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            QpartyTheme {
                GameScreenContent(
                    question = null,
                    onNext = { },
                    onRestart = { clicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText(restartButton).performClick()
        assert(clicked)
    }

    @Test
    fun restartButton_notShown_whenQuestionExists() {
        composeTestRule.setContent {
            QpartyTheme {
                GameScreenContent(
                    question = "test question",
                    onNext = { },
                    onRestart = { }
                )
            }
        }

        composeTestRule.onNodeWithText(restartButton).assertDoesNotExist()
    }
}