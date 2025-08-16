package com.example.qparty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qparty.ui.screens.GameScreen
import com.example.qparty.ui.screens.StartScreen

object Routes {
    const val START = "start"
    const val GAME = "game"
}

@Composable
fun QuestionNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.START,
        modifier = modifier
    ) {
        composable(Routes.START) { StartScreen(navController) }
        composable(Routes.GAME) { GameScreen(navController) }
    }
}