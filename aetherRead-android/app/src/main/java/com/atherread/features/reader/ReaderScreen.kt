package com.atherread.features.reader

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.atherread.core.theme.DarkBackground
import com.atherread.core.theme.Surface
import com.atherread.core.theme.TextPrimary
import com.atherread.core.theme.PurplePrimary
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    documentId: String,
    onBack: () -> Unit,
    onNavigateToAiPlaceholder: (String) -> Unit,
    viewModel: ReaderViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val decodedPath = remember(documentId) { URLDecoder.decode(documentId, "UTF-8") }
    
    val pageCount by viewModel.pageCount.collectAsState()
    val renderedPages by viewModel.renderedPages.collectAsState()

    var containerWidth by remember { mutableStateOf(0) }

    LaunchedEffect(decodedPath) {
        viewModel.initPdf(context, decodedPath)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reader", color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigateToAiPlaceholder("Ask AI") }) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = "Ask AI", tint = PurplePrimary)
                    }
                    IconButton(onClick = { /* TODO TOC */ }) {
                        Icon(Icons.Default.List, contentDescription = "Table of Contents", tint = TextPrimary)
                    }
                    IconButton(onClick = { /* TODO Bookmark */ }) {
                        Icon(Icons.Default.BookmarkBorder, contentDescription = "Bookmark", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = Surface) {
                // Future bottom toolbar
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(DarkBackground)
                .onGloballyPositioned { coordinates ->
                    containerWidth = coordinates.size.width
                }
        ) {
            if (pageCount > 0 && containerWidth > 0) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(pageCount) { index ->
                        LaunchedEffect(index, containerWidth) {
                            viewModel.loadPage(index, containerWidth)
                        }
                        
                        val bitmap = renderedPages[index]
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Page $index",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                                    .background(Surface)
                            )
                        }
                    }
                }
            } else {
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.Center))
            }
        }
    }
}
