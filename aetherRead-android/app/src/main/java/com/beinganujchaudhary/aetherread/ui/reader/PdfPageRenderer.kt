package com.beinganujchaudhary.aetherread.ui.reader

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import com.beinganujchaudhary.aetherread.domain.model.ComfortTheme
import com.beinganujchaudhary.aetherread.ui.theme.getBackgroundColor
import com.beinganujchaudhary.aetherread.ui.theme.getColorFilter

@Composable
fun PdfPageRenderer(
    pageIndex: Int,
    bitmap: Bitmap?,
    theme: ComfortTheme,
    activeTool: DrawingTool,
    activeColor: Color,
    modifier: Modifier = Modifier
) {
    val colorFilter = remember(theme) { theme.getColorFilter() }
    val backgroundColor = remember(theme) { theme.getBackgroundColor() }

    // In-memory strokes for the page
    data class Line(val path: Path, val tool: DrawingTool, val color: Color)
    val lines = remember { mutableStateListOf<Line>() }
    var currentPath by remember { mutableStateOf<Path?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(if (bitmap != null) bitmap.width.toFloat() / bitmap.height else 0.7f)
            .background(color = backgroundColor)
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
                    .androidx.compose.ui.graphics.graphicsLayer {
                        compositingStrategy = androidx.compose.ui.graphics.CompositingStrategy.Offscreen
                    }
                    .pointerInput(activeTool) {
                        if (activeTool == DrawingTool.NONE) return@pointerInput

                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPath = Path().apply { moveTo(offset.x, offset.y) }
                            },
                            onDrag = { change, _ ->
                                change.consume()
                                currentPath?.lineTo(change.position.x, change.position.y)
                            },
                            onDragEnd = {
                                currentPath?.let {
                                    lines.add(Line(it, activeTool, activeColor))
                                    currentPath = null
                                }
                            },
                            onDragCancel = { currentPath = null }
                        )
                    }
            ) {
                // Draw saved lines
                lines.forEach { line ->
                    val isEraser = line.tool == DrawingTool.ERASER
                    val strokeColor = if (isEraser) Color.Transparent else line.color.copy(alpha = if (line.tool == DrawingTool.HIGHLIGHTER) 0.4f else 1f)
                    val width = if (line.tool == DrawingTool.HIGHLIGHTER) 30f else 5f
                    val blendMode = if (isEraser) androidx.compose.ui.graphics.BlendMode.Clear else androidx.compose.ui.graphics.BlendMode.SrcOver
                    
                    if (line.tool == DrawingTool.TEXT_UNDERLINE) {
                        // Drawing logic for underline: we can just draw path but use stroke
                        drawPath(
                            path = line.path,
                            color = strokeColor,
                            style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                    } else {
                        drawPath(
                            path = line.path,
                            color = strokeColor,
                            style = Stroke(width = width, cap = StrokeCap.Round, join = StrokeJoin.Round),
                            blendMode = blendMode
                        )
                    }
                }

                // Draw current in-progress line
                currentPath?.let { path ->
                    val isEraser = activeTool == DrawingTool.ERASER
                    val strokeColor = if (isEraser) Color.Transparent else activeColor.copy(alpha = if (activeTool == DrawingTool.HIGHLIGHTER) 0.4f else 1f)
                    val width = if (activeTool == DrawingTool.HIGHLIGHTER) 30f else 5f
                    val blendMode = if (isEraser) androidx.compose.ui.graphics.BlendMode.Clear else androidx.compose.ui.graphics.BlendMode.SrcOver

                    if (activeTool == DrawingTool.TEXT_UNDERLINE) {
                        drawPath(
                            path = path,
                            color = strokeColor,
                            style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                    } else {
                        drawPath(
                            path = path,
                            color = strokeColor,
                            style = Stroke(width = width, cap = StrokeCap.Round, join = StrokeJoin.Round),
                            blendMode = blendMode
                        )
                    }
                }
            }
        }
    }
}
