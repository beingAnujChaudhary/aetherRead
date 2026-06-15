package com.beinganujchaudhary.aetherread.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import com.beinganujchaudhary.aetherread.domain.model.ComfortTheme

fun ComfortTheme.getColorFilter(): ColorFilter? {
    return when (this) {
        ComfortTheme.BOOK_PAPER -> ColorFilter.colorMatrix(ColorMatrix().apply {
            // Slight yellow/warm tint
            setToScale(1.0f, 0.95f, 0.85f, 1.0f)
        })
        ComfortTheme.SEPIA_SANDS -> ColorFilter.colorMatrix(ColorMatrix().apply {
            // Classic sepia
            val matrix = floatArrayOf(
                0.393f, 0.769f, 0.189f, 0f, 0f,
                0.349f, 0.686f, 0.168f, 0f, 0f,
                0.272f, 0.534f, 0.131f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
            set(ColorMatrix(matrix))
        })
        ComfortTheme.DARK_ABYSS -> ColorFilter.colorMatrix(ColorMatrix().apply {
            // Invert colors
            val matrix = floatArrayOf(
                -1f, 0f, 0f, 0f, 255f,
                0f, -1f, 0f, 0f, 255f,
                0f, 0f, -1f, 0f, 255f,
                0f, 0f, 0f, 1f, 0f
            )
            set(ColorMatrix(matrix))
        })
        ComfortTheme.FOCUS_PUNCH -> ColorFilter.colorMatrix(ColorMatrix().apply {
            // High contrast
            setToScale(1.2f, 1.2f, 1.2f, 1.0f)
        })
        ComfortTheme.MONOCHROME -> ColorFilter.colorMatrix(ColorMatrix().apply {
            // Grayscale
            setToSaturation(0f)
        })
        ComfortTheme.GARDEN_SAGE -> ColorFilter.colorMatrix(ColorMatrix().apply {
            // Soft green tint
            setToScale(0.9f, 1.0f, 0.9f, 1.0f)
        })
    }
}

fun ComfortTheme.getBackgroundColor(): Color {
    return when (this) {
        ComfortTheme.DARK_ABYSS -> Color(0xFF121212)
        ComfortTheme.BOOK_PAPER -> Color(0xFFFDF6E3)
        ComfortTheme.SEPIA_SANDS -> Color(0xFF704214).copy(alpha = 0.1f)
        ComfortTheme.FOCUS_PUNCH -> Color.White
        ComfortTheme.MONOCHROME -> Color.White
        ComfortTheme.GARDEN_SAGE -> Color(0xFFE8F5E9)
    }
}
