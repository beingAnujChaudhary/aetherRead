package com.beinganujchaudhary.aetherread.ui.reader

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.beinganujchaudhary.aetherread.domain.model.ComfortTheme
import com.beinganujchaudhary.aetherread.ui.theme.getColorFilter

@Composable
fun PdfPageRenderer(
    pageIndex: Int,
    bitmap: Bitmap?,
    theme: ComfortTheme,
    modifier: Modifier = Modifier
) {
    val colorFilter = remember(theme) { theme.getColorFilter() }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            // Assume standard PDF aspect ratio (~1:1.414) if bitmap not loaded yet
            .aspectRatio(if (bitmap != null) bitmap.width.toFloat() / bitmap.height else 0.7f)
            .background(Color.White)
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Page ${pageIndex + 1}",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                colorFilter = colorFilter
            )
        }
    }
}
