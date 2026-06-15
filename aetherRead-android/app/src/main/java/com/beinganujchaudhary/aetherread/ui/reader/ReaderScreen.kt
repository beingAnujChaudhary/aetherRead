package com.beinganujchaudhary.aetherread.ui.reader

import android.graphics.Bitmap
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
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
    var showAnnotationForm by remember { mutableStateOf(false) }

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
                        modifier = Modifier.padding(end = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { showAnnotationPanel = true }) {
                        Icon(Icons.Default.List, contentDescription = "Annotations")
                    }
                    IconButton(onClick = { showThemePicker = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Themes")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { showAnnotationForm = true }) {
                        Icon(Icons.Default.Create, contentDescription = "Annotate")
                    }
                }
            )
        }
    ) { paddingValues ->
        val activeTheme = readingState?.activeTheme ?: ComfortTheme.DARK_ABYSS

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            var columnWidthPx by remember { mutableStateOf(0) }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        columnWidthPx = coordinates.size.width
                    }
            ) {
                items(pageCount) { index ->
                    PdfPageAsync(
                        pageIndex = index,
                        widthPx = columnWidthPx,
                        theme = activeTheme,
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

    if (showAnnotationForm) {
        AnnotationForm(
            currentPage = readingState?.currentPage ?: 1,
            onDismiss = { showAnnotationForm = false }
        )
    }
}

@Composable
fun PdfPageAsync(
    pageIndex: Int,
    widthPx: Int,
    theme: ComfortTheme,
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
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
