package com.example.qparty.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qparty.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.random.Random

@Composable
fun Dice(
    value: Int,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(100.dp)) {
        val side = min(size.width, size.height)
        val diceSize = Size(side, side)

        drawRoundRect(
            color = Color.White,
            size = diceSize,
            cornerRadius = CornerRadius(16f, 16f)
        )

        if (value == 0) return@Canvas

        val dotRadius = side * 0.08f
        val center = Offset(size.width / 2, size.height / 2)

        fun drawDot(x: Float, y: Float) {
            drawCircle(
                color = Color.Black,
                radius = dotRadius,
                center = Offset(x, y)
            )
        }

        val offset = side * 0.25f

        when (value) {
            1 -> drawDot(center.x, center.y)
            2 -> {
                drawDot(center.x - offset, center.y - offset)
                drawDot(center.x + offset, center.y + offset)
            }
            3 -> {
                drawDot(center.x - offset, center.y - offset)
                drawDot(center.x, center.y)
                drawDot(center.x + offset, center.y + offset)
            }
            4 -> {
                drawDot(center.x - offset, center.y - offset)
                drawDot(center.x + offset, center.y - offset)
                drawDot(center.x - offset, center.y + offset)
                drawDot(center.x + offset, center.y + offset)
            }
            5 -> {
                drawDot(center.x - offset, center.y - offset)
                drawDot(center.x + offset, center.y - offset)
                drawDot(center.x, center.y)
                drawDot(center.x - offset, center.y + offset)
                drawDot(center.x + offset, center.y + offset)
            }
            6 -> {
                drawDot(center.x - offset, center.y - offset)
                drawDot(center.x + offset, center.y - offset)
                drawDot(center.x - offset, center.y)
                drawDot(center.x + offset, center.y)
                drawDot(center.x - offset, center.y + offset)
                drawDot(center.x + offset, center.y + offset)
            }
        }
    }
}

@Composable
fun DiceRollScreen(navController: NavController) {
    val players = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<List<String>>("players") ?: emptyList()

    var rolls by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    var currentPlayerIndex by remember { mutableIntStateOf(0) }
    val currentPlayer = players.getOrNull(currentPlayerIndex)

    var lastRoll by remember { mutableIntStateOf(0) }
    var isRolling by remember { mutableStateOf(false) }

    val rotationX = remember { Animatable(0f) }
    val rotationY = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentPlayer != null) {
            Text("Ход игрока: $currentPlayer")
            Spacer(Modifier.height(16.dp))

            Dice(
                value = lastRoll,
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer(
                        rotationX = rotationX.value,
                        rotationY = rotationY.value
                    )
            )
            Spacer(Modifier.height(16.dp))

            if (!isRolling && lastRoll == 0) {
                Button(onClick = {
                    if (!isRolling) {
                        isRolling = true
                        scope.launch {
                            repeat(15) {
                                lastRoll = Random.nextInt(1, 7)
                                rotationX.animateTo(
                                    rotationX.value + 90f,
                                    animationSpec = tween(50)
                                )
                                rotationY.animateTo(
                                    rotationY.value + 90f,
                                    animationSpec = tween(50)
                                )
                                delay(60)
                            }
                            rolls = rolls + (currentPlayer to lastRoll)
                            isRolling = false

                            delay(500)

                            if (currentPlayerIndex < players.size - 1) {
                                currentPlayerIndex++
                                lastRoll = 0
                            }
                        }
                    }
                }) {
                    Text("Бросить кубик 🎲")
                }
            }

            if (currentPlayerIndex == players.lastIndex && lastRoll != 0 && !isRolling) {
                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    val firstPlayer = rolls.maxByOrNull { it.value }?.key
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("firstPlayer", firstPlayer)
                    navController.navigate(Routes.GAME)
                }) {
                    Text("Начать игру 🚀")
                }
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