package com.beinganujchaudhary.aetherread.ui.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class DrawingTool(val title: String, val icon: ImageVector?) {
    SMART_PEN("Smart Pen", Icons.Default.Create),
    SMART_HIGHLIGHTER("Smart Highlight", Icons.Default.Edit),
    TEXT_HIGHLIGHTER("Text Highlight", Icons.Default.Edit),
    HIGHLIGHTER("Highlight", Icons.Default.Edit),
    TEXT_UNDERLINE("Text Underline", null), // Will use text "U"
    FREEHAND("Freehand", Icons.Default.Create),
    FREE_TEXT("Free Text", null), // Will use text "T"
    TEXT_STRIKETHROUGH("Text Strikethrough", null), // Will use text "S"
    TEXT_SQUIGGLY("Text Squiggly", null), // Will use text "~"
    STICKY_NOTE("Sticky Note", Icons.Default.Email), // Closest to comment icon
    ERASER("Eraser Tool", Icons.Default.Clear),
    CALLOUT("Callout Tool", Icons.Default.Info),
    MULTI_SELECT("Multi-select", Icons.Default.List),
    NONE("None", null)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnotateTopBar(
    activeTool: DrawingTool,
    onBack: () -> Unit,
    onTitleClick: () -> Unit,
    onToolSelected: (DrawingTool) -> Unit,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onTitleClick() }.padding(4.dp)
            ) {
                Text("Annotate", fontWeight = FontWeight.SemiBold)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Edit Annotate")
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Exit Annotate")
            }
        },
        actions = {
            IconButton(onClick = { onToolSelected(DrawingTool.STICKY_NOTE) }) {
                Icon(Icons.Default.Email, contentDescription = "Sticky Note")
            }
            IconButton(onClick = { onToolSelected(DrawingTool.ERASER) }) {
                Icon(Icons.Default.Clear, contentDescription = "Eraser")
            }
            IconButton(onClick = { onToolSelected(DrawingTool.CALLOUT) }) {
                Icon(Icons.Default.Info, contentDescription = "Callout")
            }
            IconButton(onClick = { onToolSelected(DrawingTool.MULTI_SELECT) }) {
                Icon(Icons.Default.List, contentDescription = "Select")
            }
            IconButton(onClick = { onToolSelected(DrawingTool.FREEHAND) }) {
                Icon(Icons.Default.Create, contentDescription = "Freehand")
            }
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, contentDescription = "Properties")
            }
            IconButton(onClick = { /* Undo */ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Undo")
            }
            IconButton(onClick = { /* Redo */ }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Redo")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1E1E1E),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAnnotateBottomSheet(
    onDismiss: () -> Unit,
    onToolSelected: (DrawingTool) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2C2C2E),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    text = "Edit Annotate",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f).padding(start = 16.dp)
                )
                IconButton(onClick = { /* More */ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
                }
            }

            // Grid of tools
            val tools = listOf(
                DrawingTool.SMART_PEN, DrawingTool.SMART_HIGHLIGHTER, DrawingTool.TEXT_HIGHLIGHTER,
                DrawingTool.HIGHLIGHTER, DrawingTool.TEXT_UNDERLINE, DrawingTool.FREEHAND,
                DrawingTool.FREE_TEXT, DrawingTool.TEXT_STRIKETHROUGH, DrawingTool.TEXT_SQUIGGLY,
                DrawingTool.STICKY_NOTE, DrawingTool.ERASER, DrawingTool.CALLOUT,
                DrawingTool.MULTI_SELECT
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Dashed border background (simulated with standard border for now)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF3A3A3C), shape = RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(tools) { tool ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { 
                                onToolSelected(tool)
                                onDismiss()
                            }
                        ) {
                            if (tool.icon != null) {
                                Icon(tool.icon, contentDescription = tool.title, tint = Color.LightGray, modifier = Modifier.size(28.dp))
                            } else {
                                // Text fallback for icons we don't have
                                val initial = when(tool) {
                                    DrawingTool.TEXT_UNDERLINE -> "U"
                                    DrawingTool.FREE_TEXT -> "T"
                                    DrawingTool.TEXT_STRIKETHROUGH -> "S"
                                    DrawingTool.TEXT_SQUIGGLY -> "~"
                                    else -> "A"
                                }
                                Text(
                                    text = initial,
                                    color = Color.LightGray,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.size(28.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Text(
                                text = tool.title,
                                color = Color.LightGray,
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }

            Text(
                text = "Hold and drag tool to change order",
                color = Color.LightGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
            )
        }
    }
}
