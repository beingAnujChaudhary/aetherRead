package com.beinganujchaudhary.aetherread.ui.reader

import android.graphics.Bitmap
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
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
    var activeTool by remember { mutableStateOf(DrawingTool.NONE) }

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
                title = { Text(document?.title ?: "Loading...") },
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
            if (activeTool != DrawingTool.NONE) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AnnotationToolbar(
                        activeTool = activeTool,
                        onToolSelected = { activeTool = it }
                    )
                }
            } else {
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
                        FloatingActionButton(onClick = { activeTool = DrawingTool.PEN }) {
                            Icon(Icons.Default.Create, contentDescription = "Annotate")
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
                        viewModel = viewModel
                    )
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
}

@Composable
fun PdfPageAsync(
    pageIndex: Int,
    widthPx: Int,
    theme: ComfortTheme,
    activeTool: DrawingTool,
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
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
