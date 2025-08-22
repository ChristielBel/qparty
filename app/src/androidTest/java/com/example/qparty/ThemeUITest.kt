package com.example.qparty

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ThemeUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun toggleThemeButton_changesIcon() {
        // Проверяем, что сначала есть кнопка "Тёмная тема"
        composeTestRule.onNodeWithContentDescription("Тёмная тема").assertExists()

        // Нажимаем на кнопку
        composeTestRule.onNodeWithContentDescription("Тёмная тема").performClick()

        // Теперь должна появиться кнопка "Светлая тема"
        composeTestRule.onNodeWithContentDescription("Светлая тема").assertExists()
    }
}
