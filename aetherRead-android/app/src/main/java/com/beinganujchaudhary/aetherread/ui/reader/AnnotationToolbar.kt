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
    PEN, HIGHLIGHTER, ERASER, NONE
}

@Composable
fun AnnotationToolbar(
    activeTool: DrawingTool,
    onToolSelected: (DrawingTool) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(16.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
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
                onClick = { onToolSelected(if (activeTool == DrawingTool.ERASER) DrawingTool.NONE else DrawingTool.ERASER) },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (activeTool == DrawingTool.ERASER) MaterialTheme.colorScheme.errorContainer else Color.Transparent
                )
            ) {
                Icon(Icons.Default.Clear, contentDescription = "Eraser")
            }
        }
    }
}
