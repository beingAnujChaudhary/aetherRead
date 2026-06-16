package com.beinganujchaudhary.aetherread.ui.library

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolboxScreen() {
    val context = LocalContext.current
    val showToast = { msg: String -> Toast.makeText(context, "$msg - Coming Soon", Toast.LENGTH_SHORT).show() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Toolbox", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                actions = {
                    IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Search, contentDescription = "Search") }
                    IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Settings, contentDescription = "Settings") }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Text("Picked For You", fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
                
                // Picked for you grid
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PickedToolItem(Icons.Default.Edit, "View &\nAnnotate") { showToast("View & Annotate") }
                    PickedToolItem(Icons.Default.Layers, "Flatten PDF") { showToast("Flatten PDF") }
                    PickedToolItem(Icons.Default.Add, "Merge Pages") { showToast("Merge Pages") }
                    PickedToolItem(Icons.Default.Create, "PDF to Word") { showToast("PDF to Word") }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Tool List
            item { ToolboxListItem(Icons.Default.Build, "Scan Document", "Scan documents with your camera to create PDF") { showToast("Scan Document") } }
            item { ToolboxListItem(Icons.Default.DateRange, "Image to PDF", "Turn images in your device gallery into PDF") { showToast("Image to PDF") } }
            item { ToolboxListItem(Icons.Default.Create, "eSign PDF", "Create a signature and sign your document") { showToast("eSign PDF") } }
            item { ToolboxListItem(Icons.Default.Menu, "Text Recognition (OCR)", "Convert images and PDF scans into searchable PDF files") { showToast("OCR") } }
            item { ToolboxListItem(Icons.Default.ArrowBack, "Convert from PDF", "Turn PDF into Word, Excel, PPT, PDF/A, HTML and images") { showToast("Convert from PDF") } }
            item { ToolboxListItem(Icons.Default.ArrowForward, "Convert to PDF", "Turn Word, Excel, PPT files and images into PDF") { showToast("Convert to PDF") } }
            item { ToolboxListItem(Icons.Default.List, "Manage PDF", "Merge, extract, rearrange, and delete pages within your PDF") { showToast("Manage PDF") } }
        }
    }
}

@Composable
fun PickedToolItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, fontSize = 11.sp, textAlign = TextAlign.Center, lineHeight = 14.sp)
    }
}

@Composable
fun ToolboxListItem(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray, lineHeight = 16.sp)
        }
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Open", tint = Color.Gray)
    }
}
