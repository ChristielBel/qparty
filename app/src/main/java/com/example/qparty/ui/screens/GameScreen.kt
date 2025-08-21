package com.example.qparty.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.qparty.R
import com.example.qparty.ui.theme.QpartyTheme
import com.example.qparty.viewmodel.QuestionViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuestionCard(text: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(minHeight = 180.dp)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = text,
                transitionSpec = {
                    fadeIn(tween(300)) with fadeOut(tween(300))
                }
            ) { targetText ->
                Text(
                    text = targetText ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun GameScreenContent(
    question: String?,
    onNext: () -> Unit,
    onRestart: () -> Unit
){
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(16000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "waveAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val waveColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val waveHeight = 60f
            val waveLength = size.width / 1.6f
            val step = 1.dp.toPx()

            val yOffsets = listOf(100f, 200f, 300f, 400f, 500f)
            val phaseShifts = listOf(0f, waveLength / 3, waveLength / 2, waveLength / 1.5f, waveLength)

            val speedFactors = listOf(1f, 1.3f, 0.8f, 1.6f, 1.2f)
            val alphas = listOf(0.15f, 0.2f, 0.1f, 0.25f, 0.18f)

            yOffsets.forEachIndexed { index, yOffset ->
                val phase = phaseShifts.getOrElse(index) { 0f }
                val speed = speedFactors.getOrElse(index) { 1f }
                val alpha = alphas.getOrElse(index) { 0.2f }

                val path = Path().apply {
                    moveTo(0f, size.height / 2 + yOffset)
                    var x = 0f
                    while (x <= size.width) {
                        val y = size.height / 2 + yOffset + waveHeight *
                                kotlin.math.sin((x + offset * speed + phase) / waveLength * 2 * Math.PI).toFloat()
                        lineTo(x, y)
                        x += step
                    }
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                val brush = Brush.verticalGradient(
                    colors = listOf(
                        waveColor.copy(alpha = alpha / 2),
                        waveColor.copy(alpha = alpha),
                        waveColor.copy(alpha = alpha / 2)
                    ),
                    startY = size.height * 0.5f,
                    endY = size.height
                )

                drawPath(path, brush)
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (question != null) {
                QuestionCard(text = question)
            }

            Spacer(modifier = Modifier.height(32.dp))

            val buttonModifier = Modifier
                .width(220.dp)
                .height(56.dp)

            if (question != null) {
                Button(
                    onClick = onNext,
                    modifier = buttonModifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.next_button))
                }
            } else {
                Button(
                    onClick = onRestart,
                    modifier = buttonModifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.restart_button))
                }
            }
        }
    }
}

@Composable
fun GameScreen(
    navController: NavController,
    questionViewModel: QuestionViewModel = viewModel()
) {
    val question by questionViewModel.currentQuestion.collectAsState()

    GameScreenContent(
        question = question?.text,
        onNext = { questionViewModel.nextQuestion() },
        onRestart = { questionViewModel.restartGame() }
    )
}

@Composable
@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun GameScreenPreview(){
    QpartyTheme {
        GameScreenContent(
            question = "Какое животное считается символом мудрости?",
            onNext = {},
            onRestart = {}
        )
    }
}