package com.beinganujchaudhary.aetherread.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF1A1A1A)
val Surface = Color(0xFF2D2D2D)
val PurplePrimary = Color(0xFF7C3AED)
val PurpleLight = Color(0xFFA855F7)
val SuccessGreen = Color(0xFF059669)

val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFF9CA3AF)

private val DarkColorScheme = darkColorScheme(
    primary = PurplePrimary,
    secondary = PurpleLight,
    background = DarkBackground,
    surface = Surface
)

@Composable
fun AetherReadTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
