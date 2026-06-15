package com.beinganujchaudhary.aetherread.ui.reader

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.Canvas
import com.beinganujchaudhary.aetherread.domain.model.ComfortTheme
import com.beinganujchaudhary.aetherread.ui.theme.getColorFilter

@Composable
fun PdfPageRenderer(
    pageIndex: Int,
    bitmap: Bitmap?,
    theme: ComfortTheme,
    activeTool: DrawingTool,
    modifier: Modifier = Modifier
) {
    val colorFilter = remember(theme) { theme.getColorFilter() }
    val backgroundColor = remember(theme) { theme.getBackgroundColor() }
    
    // In-memory strokes for the page
    data class Line(val path: Path, val tool: DrawingTool)
    val lines = remember { mutableStateListOf<Line>() }
    var currentPath by remember { mutableStateOf<Path?>(null) }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            // Assume standard PDF aspect ratio (~1:1.414) if bitmap not loaded yet
            .aspectRatio(if (bitmap != null) bitmap.width.toFloat() / bitmap.height else 0.7f)
            .background(backgroundColor)
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Page ${pageIndex + 1}",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                colorFilter = colorFilter
            )

            // Drawing Overlay
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .pointerInput(activeTool) {
                        if (activeTool == DrawingTool.NONE) return@pointerInput

                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPath = Path().apply { moveTo(offset.x, offset.y) }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                currentPath?.lineTo(change.position.x, change.position.y)
                            },
                            onDragEnd = {
                                currentPath?.let {
                                    lines.add(Line(it, activeTool))
                                    currentPath = null
                                }
                            },
                            onDragCancel = { currentPath = null }
                        )
                    }
            ) {
                // Draw saved lines
                lines.forEach { line ->
                    val color = if (line.tool == DrawingTool.HIGHLIGHTER) Color.Yellow.copy(alpha = 0.4f) else Color.Red
                    val width = if (line.tool == DrawingTool.HIGHLIGHTER) 30f else 5f
                    drawPath(
                        path = line.path,
                        color = color,
                        style = Stroke(width = width, cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                }
                
                // Draw current line
                currentPath?.let { path ->
                    val color = if (activeTool == DrawingTool.HIGHLIGHTER) Color.Yellow.copy(alpha = 0.4f) else Color.Red
                    val width = if (activeTool == DrawingTool.HIGHLIGHTER) 30f else 5f
                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(width = width, cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                }
            }
        }
    }
}
