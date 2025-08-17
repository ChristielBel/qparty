package com.example.qparty.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

private val LightBlueColorScheme = lightColorScheme(
    primary = LightBluePrimary,
    secondary = LightBlueSecondary,
    tertiary = LightBlueTertiary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkBlueColorScheme = darkColorScheme(
    primary = DarkBluePrimary,
    secondary = DarkBlueSecondary,
    tertiary = DarkBlueTertiary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun QpartyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val targetColors = if (darkTheme) DarkBlueColorScheme else LightBlueColorScheme

    val primary by animateColorAsState(targetColors.primary, label = "primary")
    val background by animateColorAsState(targetColors.background, label = "background")
    val surface by animateColorAsState(targetColors.surface, label = "surface")
    val onPrimary by animateColorAsState(targetColors.onPrimary, label = "onPrimary")
    val onBackground by animateColorAsState(targetColors.onBackground, label = "onBackground")

    val animatedColorScheme = targetColors.copy(
        primary = primary,
        background = background,
        surface = surface,
        onPrimary = onPrimary,
        onBackground = onBackground
    )

    MaterialTheme(
        colorScheme = animatedColorScheme,
        typography = Typography,
        content = content
    )
}