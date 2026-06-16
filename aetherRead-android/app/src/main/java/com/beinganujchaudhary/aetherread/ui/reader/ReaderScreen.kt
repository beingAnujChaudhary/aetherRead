package com.beinganujchaudhary.aetherread.ui.reader

import android.graphics.Bitmap
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beinganujchaudhary.aetherread.domain.model.ComfortTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    onNavigateBack: () -> Unit,
    viewModel: ReaderViewModel = hiltViewModel()
) {
    val document by viewModel.document.collectAsState()
    val readingState by viewModel.readingState.collectAsState()
    val pageCount by viewModel.pageCount.collectAsState()
    val annotationsMap by viewModel.annotations.collectAsState()

    var showMenu by remember { mutableStateOf(false) }
    var showTextSelection by remember { mutableStateOf(false) } // Mock text selection toggle
    var showSummaryBanner by remember { mutableStateOf(true) }

    // Drawing/Annotation state (kept from previous code)
    var isAnnotateMode by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var activeTool by remember { mutableStateOf(DrawingTool.NONE) }
    var activeColor by remember { mutableStateOf(Color.Red) }

    // Zoom state
    var zoomLevel by remember { mutableStateOf(1f) }
    var panOffset by remember { mutableStateOf(Offset.Zero) }

    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        zoomLevel = (zoomLevel * zoomChange).coerceIn(0.5f, 4f)
        panOffset += panChange
    }

    val listState = rememberLazyListState()

    // Sync scroll position
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
            viewModel.updateCurrentPage(index)
        }
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color(0xFF121212))) {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Outlined.WaterDrop, contentDescription = "Liquid Mode", tint = Color.White)
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Outlined.VolumeUp, contentDescription = "Read aloud", tint = Color.White)
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Outlined.Search, contentDescription = "Search", tint = Color.White)
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Outlined.Share, contentDescription = "Share", tint = Color.White)
                        }
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121212))
                )
                if (showSummaryBanner) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(Color.Black, RoundedCornerShape(8.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = "AI", tint = Color(0xFF8E8CD8))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Short on time? Try Generative summary", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                            IconButton(onClick = { /* TODO */ }) {
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Expand", tint = Color.White)
                            }
                            IconButton(onClick = { showSummaryBanner = false }) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                            }
                        }
                    }
                }
            }
        },
        containerColor = Color.White // The PDF background
    ) { paddingValues ->
        val activeTheme = readingState?.activeTheme ?: ComfortTheme.DARK_ABYSS

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .then(
                    if (activeTool == DrawingTool.NONE)
                        Modifier.transformable(state = transformableState)
                    else Modifier
                )
                // Mock text selection toggle
                .clickable { showTextSelection = !showTextSelection }
        ) {
            var columnWidthPx by remember { mutableStateOf(0) }

            LazyColumn(
                state = listState,
                userScrollEnabled = activeTool == DrawingTool.NONE,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = zoomLevel
                        scaleY = zoomLevel
                        translationX = panOffset.x
                        translationY = panOffset.y
                    }
                    .onGloballyPositioned { coordinates ->
                        columnWidthPx = coordinates.size.width
                    }
            ) {
                items(pageCount) { index ->
                    PdfPageAsync(
                        pageIndex = index,
                        widthPx = columnWidthPx,
                        theme = activeTheme,
                        activeTool = activeTool,
                        activeColor = activeColor,
                        lines = annotationsMap[index] ?: emptyList(),
                        viewModel = viewModel
                    )
                }
            }

            // Context Menu mock (shows up when clicking the document for this demo)
            if (showTextSelection) {
                TextSelectionContextMenu(
                    onDismiss = { showTextSelection = false },
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Floating Bottom Pill Bar
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF1E1E1E), RoundedCornerShape(24.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomPillItem("Edit PDF", Icons.Outlined.Edit, isPremium = true)
                    BottomPillItem("Comment", Icons.Outlined.ChatBubbleOutline)
                    BottomPillItem("Highlight", Icons.Outlined.BorderColor)
                    BottomPillItem("Draw", Icons.Outlined.Create, onClick = { isAnnotateMode = true; activeTool = DrawingTool.FREEHAND })
                    BottomPillItem("Fill & Sign", Icons.Outlined.Draw)
                    BottomPillItem("More tools", Icons.Outlined.MoreHoriz)
                }
            }

            // AI Assistant FAB floating on top right, just above bottom bar
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Color(0xFF6B4BCC), // Purple gradient mock color
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 80.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = "AI", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("AI Assistant", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            
            // Re-integrate drawing tools when annotate mode is active
            if (isAnnotateMode) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 140.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (showColorPicker && (activeTool == DrawingTool.FREEHAND || activeTool == DrawingTool.HIGHLIGHTER || activeTool == DrawingTool.TEXT_HIGHLIGHTER || activeTool == DrawingTool.TEXT_UNDERLINE)) {
                        Row(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .background(Color(0xFF2C2C2C), shape = RoundedCornerShape(16.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val colors = listOf(
                                Color(0xFFF44336), // Red
                                Color(0xFF2196F3), // Blue
                                Color(0xFF4CAF50), // Green
                                Color(0xFFFFEB3B), // Yellow
                                Color(0xFFE91E63), // Magenta
                                Color(0xFFFFFFFF)  // White
                            )
                            colors.forEach { color ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(color, shape = CircleShape)
                                        .clickable { activeColor = color; showColorPicker = false }
                                ) {
                                    if (activeColor == color) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = Color.Black, modifier = Modifier.align(Alignment.Center))
                                    }
                                }
                            }
                        }
                    }
                    
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF2A2A2A), shape = CircleShape)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Freehand (Pen)
                            val penActive = activeTool == DrawingTool.FREEHAND
                            IconButton(
                                onClick = { activeTool = DrawingTool.FREEHAND; showColorPicker = true },
                                modifier = Modifier.size(40.dp).background(if (penActive) Color(0xFF4C8DFF) else Color.Transparent, CircleShape)
                            ) {
                                Icon(Icons.Default.Create, contentDescription = "Pen", tint = if (penActive) Color.White else Color.LightGray)
                            }
                            // Highlighter
                            val highlightActive = activeTool == DrawingTool.HIGHLIGHTER
                            IconButton(
                                onClick = { activeTool = DrawingTool.HIGHLIGHTER; showColorPicker = true },
                                modifier = Modifier.size(40.dp).background(if (highlightActive) Color(0xFF4C8DFF) else Color.Transparent, CircleShape)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Highlighter", tint = if (highlightActive) Color.White else Color.LightGray)
                            }
                            // Text Underline
                            val underlineActive = activeTool == DrawingTool.TEXT_UNDERLINE
                            IconButton(
                                onClick = { activeTool = DrawingTool.TEXT_UNDERLINE; showColorPicker = true },
                                modifier = Modifier.size(40.dp).background(if (underlineActive) Color(0xFF4C8DFF) else Color.Transparent, CircleShape)
                            ) {
                                Icon(Icons.Default.FormatUnderlined, contentDescription = "Underline", tint = if (underlineActive) Color.White else Color.LightGray)
                            }
                            // Eraser
                            val eraserActive = activeTool == DrawingTool.ERASER
                            IconButton(
                                onClick = { activeTool = DrawingTool.ERASER; showColorPicker = false },
                                modifier = Modifier.size(40.dp).background(if (eraserActive) Color(0xFF4C8DFF) else Color.Transparent, CircleShape)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Eraser", tint = if (eraserActive) Color.White else Color.LightGray)
                            }
                            // Divider
                            Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color.DarkGray))
                            
                            // Close
                            IconButton(
                                onClick = { isAnnotateMode = false; activeTool = DrawingTool.NONE; showColorPicker = false },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.LightGray)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showMenu) {
        ReaderMenuBottomSheet(onDismiss = { showMenu = false })
    }
}

@Composable
fun BottomPillItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isPremium: Boolean = false, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box {
            Icon(icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(24.dp))
            if (isPremium) {
                Box(modifier = Modifier.align(Alignment.TopEnd).offset(x = 4.dp, y = (-4).dp).background(Color(0xFF8E8CD8), CircleShape).padding(2.dp)) {
                    Icon(Icons.Default.Star, contentDescription = "Premium", tint = Color.White, modifier = Modifier.size(10.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(title, color = Color.White, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.widthIn(max = 48.dp))
    }
}

@Composable
fun PdfPageAsync(
    pageIndex: Int,
    widthPx: Int,
    theme: ComfortTheme,
    activeTool: DrawingTool,
    activeColor: Color,
    lines: List<Line>,
    viewModel: ReaderViewModel
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(pageIndex, widthPx) {
        if (widthPx > 0) {
            bitmap = viewModel.renderPage(pageIndex, widthPx)
        }
    }

    PdfPageRenderer(
        pageIndex = pageIndex,
        bitmap = bitmap,
        theme = theme,
        activeTool = activeTool,
        activeColor = activeColor,
        lines = lines,
        onAddLine = { viewModel.addAnnotation(pageIndex, it) },
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
