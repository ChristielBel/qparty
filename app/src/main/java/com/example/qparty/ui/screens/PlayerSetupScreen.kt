package com.example.qparty.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
fun PlayerSetupScreen(navController: NavController) {
    var playerCount by remember { mutableStateOf(2) }
    var playerNames by remember { mutableStateOf(List(2) { "" }) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Введите количество игроков:")

        OutlinedTextField(
            value = playerCount.toString(),
            onValueChange = { value ->
                value.toIntOrNull()?.let {
                    playerCount = it
                    playerNames = List(it) { "" }
                }
            },
            label = { Text("Количество") }
        )

        Spacer(Modifier.height(16.dp))

        playerNames.forEachIndexed { index, name ->
            OutlinedTextField(
                value = name,
                onValueChange = { newValue ->
                    playerNames = playerNames.toMutableList().apply {
                        this[index] = newValue
                    }
                },
                label = { Text("Игрок ${index + 1}") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("players", playerNames)

                navController.navigate(Routes.DICE)
            }
        ) {
            Text("Далее → Бросок кубика")
        }
    }
}
