package com.atherread.features.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.atherread.core.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToPdf: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val recentPdfs by viewModel.recentPdfs.collectAsState()

    val documentPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.onPdfOpened(it, context, onNavigateToPdf)
            }
        }
    )

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { documentPickerLauncher.launch(arrayOf("application/pdf")) },
                containerColor = PurplePrimary,
                contentColor = TextPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Open PDF")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (recentPdfs.isNotEmpty()) {
                item {
                    SectionHeader("Continue Reading")
                    val latestPdf = recentPdfs.first()
                    DocumentCard(
                        title = latestPdf.name,
                        subtitle = if (latestPdf.totalPages > 0) "Page ${latestPdf.currentPage + 1} of ${latestPdf.totalPages}" else "Not opened yet",
                        onClick = { 
                            val encodedPath = java.net.URLEncoder.encode(latestPdf.path, "UTF-8")
                            onNavigateToPdf(encodedPath) 
                        }
                    )
                }
            }

            item {
                SectionHeader("Recent PDFs")
                if (recentPdfs.isEmpty()) {
                    Text("No recent PDFs.", color = TextSecondary)
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        val listToShow = if (recentPdfs.size > 1) recentPdfs.drop(1) else emptyList()
                        listToShow.forEach { pdf ->
                            DocumentCard(
                                title = pdf.name,
                                subtitle = "Last opened recently",
                                onClick = { 
                                    val encodedPath = java.net.URLEncoder.encode(pdf.path, "UTF-8")
                                    onNavigateToPdf(encodedPath) 
                                }
                            )
                        }
                    }
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
