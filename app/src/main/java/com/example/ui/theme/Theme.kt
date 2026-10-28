package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val QuizColorScheme = lightColorScheme(
    primary = PopHotPink,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDDE6),
    onPrimaryContainer = Color(0xFF880026),
    secondary = AccentPurple,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEDE7F6),
    onSecondaryContainer = Color(0xFF311B92),
    background = PastelPinkBg,
    onBackground = DeepCharcoalText,
    surface = LightPinkSurface,
    onSurface = DeepCharcoalText,
    surfaceVariant = Color(0xFFF7ECF0),
    onSurfaceVariant = SecondaryCharcoalText
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = QuizColorScheme,
        typography = Typography,
        content = content
    )
}
