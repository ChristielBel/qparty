package com.example.qparty.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.qparty.ui.screens.StartScreenContent
import com.example.qparty.ui.theme.QpartyTheme
import org.junit.Rule
import org.junit.Test
import com.example.qparty.R

class StartScreenTest {

    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val title = context.getString(R.string.app_name)
    val welcome = context.getString(R.string.welcome_message)
    val button = context.getString(R.string.start_game_button)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun startScreen_showsTitleAndButton() {
        composeTestRule.setContent {
            QpartyTheme {
                StartScreenContent(onGame = {})
            }
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(welcome).assertIsDisplayed()
        composeTestRule.onNodeWithText(button).assertIsDisplayed()
    }

    @Test
    fun startButton_triggersCallback() {
        var clicked = false

        composeTestRule.setContent {
            QpartyTheme {
                StartScreenContent( onGame = {clicked = true } )
            }
        }

        composeTestRule.onNodeWithText(button).performClick()
        assert(clicked)
    }
}