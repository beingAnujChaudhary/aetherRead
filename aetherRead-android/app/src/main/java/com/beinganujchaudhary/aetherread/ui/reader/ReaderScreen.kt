package com.beinganujchaudhary.aetherread.ui.reader

import android.graphics.Bitmap
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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

    var showThemePicker by remember { mutableStateOf(false) }
    var showAnnotationPanel by remember { mutableStateOf(false) }
    var isAnnotateMode by remember { mutableStateOf(false) }
    var showEditAnnotateGrid by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var activeTool by remember { mutableStateOf(DrawingTool.NONE) }
    var activeColor by remember { mutableStateOf(androidx.compose.ui.graphics.Color.Red) }
    var showMenu by remember { mutableStateOf(false) }

    // Zoom state — pinch-to-zoom + button controls
    var zoomLevel by remember { mutableStateOf(1f) }
    var panOffset by remember { mutableStateOf(Offset.Zero) }

    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        zoomLevel = (zoomLevel * zoomChange).coerceIn(0.5f, 4f)
        panOffset += panChange
    }

    val listState = rememberLazyListState()

    // Sync scroll position to view model
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
            viewModel.updateCurrentPage(index)
        }
    }

    // Restore scroll position on load
    LaunchedEffect(readingState?.currentPage) {
        val targetPage = (readingState?.currentPage ?: 1) - 1
        if (targetPage > 0 && listState.firstVisibleItemIndex == 0) {
            listState.scrollToItem(targetPage)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                    title = { 
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showMenu = true }.padding(4.dp)
                        ) {
                            Text("View", fontWeight = FontWeight.Bold)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Menu")
                        }
                        
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.fillMaxWidth(0.8f).background(MaterialTheme.colorScheme.surface)
                        ) {
                            ReaderMenuItem("View", Icons.Default.Visibility) { showMenu = false; isAnnotateMode = false }
                            ReaderMenuItem("Annotate", Icons.Default.Edit) { showMenu = false; isAnnotateMode = true; showEditAnnotateGrid = true }
                            ReaderMenuItem("Draw", Icons.Default.Create) { showMenu = false; isAnnotateMode = true; activeTool = DrawingTool.FREEHAND }
                            ReaderMenuItem("Fill and Sign", Icons.Default.CheckCircle) { showMenu = false }
                            ReaderMenuItem("Convert", Icons.Default.Build) { showMenu = false }
                            ReaderMenuItem("Prepare Form", Icons.Default.List) { showMenu = false }
                            ReaderMenuItem("Insert", Icons.Default.AddCircle) { showMenu = false }
                            ReaderMenuItem("Measure", Icons.Default.Place) { showMenu = false }
                            ReaderMenuItem("Pens", Icons.Default.Create) { showMenu = false; isAnnotateMode = true; activeTool = DrawingTool.FREEHAND }
                            ReaderMenuItem("Redact", Icons.Default.Lock) { showMenu = false }
                            ReaderMenuItem("Favorites", Icons.Default.Favorite) { showMenu = false }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        Text(
                            text = "${(readingState?.currentPage ?: 1)} / $pageCount",
                            modifier = Modifier.padding(end = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        // Zoom level indicator in top bar
                        Text(
                            text = "${(zoomLevel * 100).toInt()}%",
                            modifier = Modifier.padding(end = 16.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }
                )
        },
        bottomBar = {
            if (!isAnnotateMode) {
                BottomAppBar(
                    actions = {
                        // Zoom out
                        IconButton(onClick = {
                            zoomLevel = (zoomLevel - 0.25f).coerceAtLeast(0.5f)
                            if (zoomLevel == 1f) panOffset = Offset.Zero
                        }) {
                            Text("－", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        // Zoom in
                        IconButton(onClick = {
                            zoomLevel = (zoomLevel + 0.25f).coerceAtMost(4f)
                        }) {
                            Text("＋", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        // Annotations list
                        IconButton(onClick = { showAnnotationPanel = true }) {
                            Icon(Icons.Default.List, contentDescription = "Annotations")
                        }
                        // Themes
                        IconButton(onClick = { showThemePicker = true }) {
                            Icon(Icons.Default.Settings, contentDescription = "Themes")
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { isAnnotateMode = true },
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        val activeTheme = readingState?.activeTheme ?: ComfortTheme.DARK_ABYSS

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                // Pinch-to-zoom gesture — only in reading mode (not drawing)
                .then(
                    if (activeTool == DrawingTool.NONE)
                        Modifier.transformable(state = transformableState)
                    else Modifier
                )
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
                        viewModel = viewModel
                    )
                }
            }

            // Xodo-style floating pill toolbar overlay
            if (isAnnotateMode) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (showColorPicker && (activeTool == DrawingTool.FREEHAND || activeTool == DrawingTool.HIGHLIGHTER || activeTool == DrawingTool.TEXT_HIGHLIGHTER || activeTool == DrawingTool.TEXT_UNDERLINE)) {
                        Row(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .background(androidx.compose.ui.graphics.Color(0xFF2C2C2C), shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val colors = listOf(
                                androidx.compose.ui.graphics.Color(0xFFF44336), // Red
                                androidx.compose.ui.graphics.Color(0xFF2196F3), // Blue
                                androidx.compose.ui.graphics.Color(0xFF4CAF50), // Green
                                androidx.compose.ui.graphics.Color(0xFFFFEB3B), // Yellow
                                androidx.compose.ui.graphics.Color(0xFFE91E63), // Magenta
                                androidx.compose.ui.graphics.Color(0xFF000000)  // Black
                            )
                            colors.forEach { color ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(color, shape = androidx.compose.foundation.shape.CircleShape)
                                        .clickable { activeColor = color; showColorPicker = false }
                                ) {
                                    if (activeColor == color) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = androidx.compose.ui.graphics.Color.White, modifier = Modifier.align(Alignment.Center))
                                    }
                                }
                            }
                        }
                    }
                    
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer, shape = androidx.compose.foundation.shape.CircleShape)
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
                                modifier = Modifier.size(40.dp).background(if (penActive) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent, androidx.compose.foundation.shape.CircleShape)
                            ) {
                                Icon(Icons.Default.Create, contentDescription = "Pen", tint = if (penActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                            // Highlighter
                            val highlightActive = activeTool == DrawingTool.HIGHLIGHTER
                            IconButton(
                                onClick = { activeTool = DrawingTool.HIGHLIGHTER; showColorPicker = true },
                                modifier = Modifier.size(40.dp).background(if (highlightActive) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent, androidx.compose.foundation.shape.CircleShape)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Highlighter", tint = if (highlightActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                            // Text Underline
                            val underlineActive = activeTool == DrawingTool.TEXT_UNDERLINE
                            IconButton(
                                onClick = { activeTool = DrawingTool.TEXT_UNDERLINE; showColorPicker = true },
                                modifier = Modifier.size(40.dp).background(if (underlineActive) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent, androidx.compose.foundation.shape.CircleShape)
                            ) {
                                Icon(Icons.Default.FormatUnderlined, contentDescription = "Underline", tint = if (underlineActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                            // Eraser
                            val eraserActive = activeTool == DrawingTool.ERASER
                            IconButton(
                                onClick = { activeTool = DrawingTool.ERASER; showColorPicker = false },
                                modifier = Modifier.size(40.dp).background(if (eraserActive) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent, androidx.compose.foundation.shape.CircleShape)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Eraser", tint = if (eraserActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                            // Divider
                            Box(modifier = Modifier.width(1.dp).height(24.dp).background(MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha=0.2f)))
                            
                            // Close
                            IconButton(
                                onClick = { isAnnotateMode = false; activeTool = DrawingTool.NONE; showColorPicker = false },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                        }
                    }
                }
            }
        }
    }


    if (showThemePicker) {
        ThemePickerSheet(
            currentTheme = readingState?.activeTheme ?: ComfortTheme.DARK_ABYSS,
            onThemeSelected = { viewModel.updateTheme(it) },
            onDismiss = { showThemePicker = false }
        )
    }

    if (showAnnotationPanel) {
        AnnotationPanel(onDismiss = { showAnnotationPanel = false })
    }
    
    if (showEditAnnotateGrid) {
        EditAnnotateBottomSheet(
            onDismiss = { showEditAnnotateGrid = false },
            onToolSelected = { activeTool = it }
        )
    }
}

@Composable
fun PdfPageAsync(
    pageIndex: Int,
    widthPx: Int,
    theme: ComfortTheme,
    activeTool: DrawingTool,
    activeColor: androidx.compose.ui.graphics.Color,
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
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun ReaderMenuItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    DropdownMenuItem(
        text = { Text(title, fontSize = 16.sp) },
        trailingIcon = { Icon(icon, contentDescription = title, tint = androidx.compose.ui.graphics.Color.Gray) },
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    )
}
