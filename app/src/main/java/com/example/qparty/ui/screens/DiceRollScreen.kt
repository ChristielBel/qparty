package com.example.qparty.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qparty.navigation.Routes

@Composable
fun DiceRollScreen(navController: NavController) {
    val players = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<List<String>>("players") ?: emptyList()

    var rolls by remember { mutableStateOf(mutableMapOf<String, Int>()) }
    var currentPlayerIndex by remember { mutableStateOf(0) }
    val currentPlayer = players.getOrNull(currentPlayerIndex)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentPlayer != null) {
            Text("Ход игрока: $currentPlayer")
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                val roll = (1..6).random()
                rolls[currentPlayer] = roll

                if (currentPlayerIndex < players.size - 1) {
                    currentPlayerIndex++
                } else {
                    val firstPlayer = rolls.maxByOrNull { it.value }?.key
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("firstPlayer", firstPlayer)
                    navController.navigate(Routes.GAME)
                }
            }) {
                Text("Бросить кубик 🎲")
            }
        } else {
            Text("Ошибка: нет игроков")
        }

        Spacer(Modifier.height(32.dp))

        rolls.forEach { (player, roll) ->
            Text("$player выбросил $roll")
        }
    }
}