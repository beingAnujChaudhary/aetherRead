package com.atherread.features.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atherread.core.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    documentId: String,
    onBack: () -> Unit,
    onNavigateToAiPlaceholder: (String) -> Unit
) {
    var showSideMenu by remember { mutableStateOf(false) }
    var showTextSelection by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { Text("PDF Name", color = TextPrimary, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary) }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Search, tint = TextPrimary, contentDescription = null) }
                    IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Bookmark, tint = TextPrimary, contentDescription = null) }
                    IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Share, tint = TextPrimary, contentDescription = null) }
                    IconButton(onClick = { showSideMenu = true }) { Icon(Icons.Default.MoreVert, tint = TextPrimary, contentDescription = "Menu") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Surface) {
                NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.Edit, "Highlight") }, label = { Text("Highlight") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = TextPrimary))
                NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.Comment, "Comment") }, label = { Text("Comment") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = TextPrimary))
                NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.Create, "Draw") }, label = { Text("Draw") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = TextPrimary))
                NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.Bookmark, "Bookmark") }, label = { Text("Bookmark") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = TextPrimary))
                NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.MoreHoriz, "More") }, label = { Text("More") }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = TextPrimary))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAiPlaceholder("AskAi") },
                containerColor = Surface,
                shape = MaterialTheme.shapes.large
            ) {
                Box(modifier = Modifier.size(56.dp).background(PurplePrimary), contentAlignment = Alignment.Center) {
                    Text("AI", color = TextPrimary, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues).background(DarkBackground)) {
            Text("Scrollable PDF Content", color = TextSecondary, modifier = Modifier.align(Alignment.Center))
            if (showTextSelection) {
                TextSelectionContextMenu(modifier = Modifier.align(Alignment.Center), onDismiss = { showTextSelection = false })
            }
        }
    }

    if (showSideMenu) {
        ReaderMenuBottomSheet(
            onDismiss = { showSideMenu = false },
            onNavigateToAiPlaceholder = onNavigateToAiPlaceholder
        )
    }
}
