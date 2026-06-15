package com.beinganujchaudhary.aetherread.ui.reader

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class DrawingTool {
    PEN, HIGHLIGHTER, UNDERLINE, ERASER, NONE
}

@Composable
fun AnnotationToolbar(
    activeTool: DrawingTool,
    activeColor: Color,
    onToolSelected: (DrawingTool) -> Unit,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(16.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = { onToolSelected(if (activeTool == DrawingTool.PEN) DrawingTool.NONE else DrawingTool.PEN) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (activeTool == DrawingTool.PEN) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                    )
                ) {
                    Icon(Icons.Default.Create, contentDescription = "Pen")
                }

                IconButton(
                    onClick = { onToolSelected(if (activeTool == DrawingTool.HIGHLIGHTER) DrawingTool.NONE else DrawingTool.HIGHLIGHTER) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (activeTool == DrawingTool.HIGHLIGHTER) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                    )
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Highlighter")
                }
                
                IconButton(
                    onClick = { onToolSelected(if (activeTool == DrawingTool.UNDERLINE) DrawingTool.NONE else DrawingTool.UNDERLINE) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (activeTool == DrawingTool.UNDERLINE) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                    )
                ) {
                    Text("U", style = androidx.compose.ui.text.TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
                }

                IconButton(
                    onClick = { onToolSelected(if (activeTool == DrawingTool.ERASER) DrawingTool.NONE else DrawingTool.ERASER) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (activeTool == DrawingTool.ERASER) MaterialTheme.colorScheme.errorContainer else Color.Transparent
                    )
                ) {
                    Icon(Icons.Default.Clear, contentDescription = "Eraser")
                }
            }

            if (activeTool == DrawingTool.HIGHLIGHTER || activeTool == DrawingTool.UNDERLINE || activeTool == DrawingTool.PEN) {
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Magenta, Color.Black)
                    colors.forEach { color ->
                        IconButton(
                            onClick = { onColorSelected(color) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            androidx.compose.foundation.Canvas(modifier = Modifier.size(20.dp)) {
                                drawCircle(
                                    color = color,
                                    radius = size.minDimension / 2,
                                    style = if (activeColor == color) androidx.compose.ui.graphics.drawscope.Fill else androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
                                )
                                if (activeColor == color) {
                                    drawCircle(
                                        color = Color.White,
                                        radius = size.minDimension / 4
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
