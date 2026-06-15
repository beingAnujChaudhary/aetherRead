package com.beinganujchaudhary.aetherread.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ── Palette (mirrors web app) ─────────────────────────────────────────────────
private val AetherPurple   = Color(0xFF6C63FF)
private val AetherViolet   = Color(0xFFA78BFA)
private val AccentOrange   = Color(0xFFFE320A)
private val SurfaceDark    = Color(0xFF16162A)
private val BackgroundDark = Color(0xFF0F0F1A)
private val OnBackgroundDark = Color(0xFFE8E8F0)

private val DarkColorScheme = darkColorScheme(
    primary        = AetherPurple,
    secondary      = AetherViolet,
    tertiary       = AccentOrange,
    background     = BackgroundDark,
    surface        = SurfaceDark,
    onPrimary      = Color.White,
    onSecondary    = Color.White,
    onBackground   = OnBackgroundDark,
    onSurface      = OnBackgroundDark,
)

private val LightColorScheme = lightColorScheme(
    primary        = AetherPurple,
    secondary      = AetherViolet,
    tertiary       = AccentOrange,
    background     = Color(0xFFEFEAE3),  // Portfolio warm cream
    surface        = Color(0xFFFFFFFF),
    onPrimary      = Color.White,
    onSecondary    = Color.White,
    onBackground   = Color(0xFF1A1A1A),
    onSurface      = Color(0xFF1A1A1A),
)

@Composable
fun AetherReadTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,   // Android 12+ Material You
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = AetherTypography,
        content     = content,
    )
}
