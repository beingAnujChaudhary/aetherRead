package com.beinganujchaudhary.aetherread.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beinganujchaudhary.aetherread.core.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToPdf: (String) -> Unit
) {
    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { Text("AtherRead", color = TextPrimary) },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Surface) {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = PurplePrimary, unselectedIconColor = TextSecondary))
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Folder, null) }, label = { Text("Files") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = PurplePrimary, unselectedIconColor = TextSecondary))
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.LibraryBooks, null) }, label = { Text("Collections") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = PurplePrimary, unselectedIconColor = TextSecondary))
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Build, null) }, label = { Text("Tools") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = PurplePrimary, unselectedIconColor = TextSecondary))
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Settings, null) }, label = { Text("Settings") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = PurplePrimary, unselectedIconColor = TextSecondary))
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                SectionHeader("Continue Reading")
                DocumentCard(title = "Hands-On Large Language Models", subtitle = "Page 42 of 300", onClick = { onNavigateToPdf("doc_1") })
            }

            item {
                SectionHeader("Recent PDFs")
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DocumentCard(title = "Qwen Specs Documentation", subtitle = "Opened Yesterday", onClick = { onNavigateToPdf("doc_2") })
                    DocumentCard(title = "Kotlin Coroutines Guide", subtitle = "Opened 2 days ago", onClick = { onNavigateToPdf("doc_3") })
                }
            }

            item {
                SectionHeader("Quick Tools")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(listOf("Merge PDF", "Split PDF", "Compress PDF")) { tool ->
                        QuickToolCard(title = tool)
                    }
                }
            }

            item {
                SectionHeader("Favorites")
                Text("No favorites yet.", color = TextSecondary)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(title, color = TextPrimary, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
}

@Composable
fun QuickToolCard(title: String) {
    Card(
        modifier = Modifier.width(110.dp).height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(title, color = TextPrimary, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = TextPrimary, style = MaterialTheme.typography.bodyLarge)
                Text(subtitle, color = TextSecondary, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
