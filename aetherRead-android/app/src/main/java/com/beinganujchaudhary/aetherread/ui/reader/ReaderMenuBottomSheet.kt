package com.beinganujchaudhary.aetherread.ui.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderMenuBottomSheet(
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1E1E1E), // Dark gray
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color.Gray) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF", tint = Color(0xFFF21826), modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Hands-On Large Language Models...", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text("PDF • 13 Jun 2026", color = Color.Gray, fontSize = 12.sp)
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Outlined.StarBorder, contentDescription = "Star", tint = Color.White)
                }
            }

            HorizontalDivider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            // Menu Items
            ReaderMenuActionItem("View settings", Icons.Outlined.Settings)
            ReaderMenuActionItem("Pages", Icons.Outlined.GridView)
            ReaderMenuActionItem("Bookmarks & Table of Contents", Icons.Outlined.BookmarkBorder)
            ReaderMenuActionItem("Comment List", Icons.Outlined.ChatBubbleOutline)
            ReaderMenuActionItem("Add to PDF Space", Icons.Outlined.CloudUpload)
            ReaderMenuActionItem("Add bookmark", Icons.Outlined.BookmarkAdd)
            ReaderMenuActionItem("Read aloud", Icons.Outlined.VolumeUp)
            ReaderMenuActionItem("Ask AI Assistant", Icons.Outlined.AutoAwesome)
            
            ReaderMenuActionItem("Generate podcast", Icons.Outlined.Podcasts, trailingBadge = "New", trailingIcon = Icons.Outlined.CloudUpload)
            ReaderMenuActionItem("Start voice chat", Icons.Outlined.Mic, trailingBadge = "New")
            
            ReaderMenuActionItem("Edit PDF", Icons.Outlined.Edit, iconTint = Color.White, trailingIcon = Icons.Default.Star) // Placeholder for crown
            
            HorizontalDivider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
            
            ReaderMenuActionItem("Print", Icons.Outlined.Print)
            ReaderMenuActionItem("Save a copy", Icons.Outlined.FileCopy)
        }
    }
}

@Composable
fun ReaderMenuActionItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color = Color.White,
    trailingBadge: String? = null,
    trailingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO */ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = iconTint, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(1f))
        
        if (trailingIcon != null && trailingBadge == null && title != "Edit PDF") {
            Icon(trailingIcon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        } else if (title == "Edit PDF") {
             // Crown icon placeholder
             Box(modifier = Modifier.background(Color(0xFF8E8CD8), RoundedCornerShape(12.dp)).padding(4.dp)) {
                Icon(Icons.Default.Star, contentDescription = "Premium", tint = Color.White, modifier = Modifier.size(12.dp))
             }
        }
        
        if (trailingBadge != null) {
            if (trailingIcon != null) {
                Icon(trailingIcon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFF1B5E20), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(trailingBadge, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        } else if (trailingIcon == null) {
            Icon(Icons.Default.ChevronRight, contentDescription = "Next", tint = Color.Gray, modifier = Modifier.size(20.dp))
        }
    }
}
