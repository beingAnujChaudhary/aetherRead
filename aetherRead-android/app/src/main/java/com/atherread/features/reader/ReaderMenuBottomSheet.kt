package com.atherread.features.reader

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atherread.core.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderMenuBottomSheet(
    onDismiss: () -> Unit,
    onNavigateToAiPlaceholder: (String) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = Surface) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Hands-On Large La...", color = TextPrimary, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            SideMenuItem(icon = Icons.Default.Settings, label = "View settings", onClick = { })
            SideMenuItem(icon = Icons.Default.Pages, label = "Pages", onClick = { })
            SideMenuItem(icon = Icons.Default.Bookmark, label = "Bookmarks", onClick = { })
            SideMenuItem(icon = Icons.Default.FormatListBulleted, label = "Table of Contents", onClick = { })
            SideMenuItem(icon = Icons.Default.Comment, label = "Comment List", onClick = { })
            SideMenuItem(icon = Icons.Default.BookmarkAdd, label = "Add Bookmark", onClick = { })
            SideMenuItem(icon = Icons.Default.VolumeUp, label = "Read Aloud", onClick = { })
            
            HorizontalDivider(color = DarkBackground, modifier = Modifier.padding(vertical = 8.dp))
            
            SideMenuItem(icon = Icons.Default.AutoAwesome, label = "Ask AI (Coming Soon)", onClick = { onNavigateToAiPlaceholder("AskAi") })
            SideMenuItem(icon = Icons.Default.Podcasts, label = "Podcast (Coming Soon)", onClick = { onNavigateToAiPlaceholder("Podcast") })
            SideMenuItem(icon = Icons.Default.Mic, label = "Voice Chat (Coming Soon)", onClick = { onNavigateToAiPlaceholder("VoiceChat") })
            
            HorizontalDivider(color = DarkBackground, modifier = Modifier.padding(vertical = 8.dp))
            
            SideMenuItem(icon = Icons.Default.Print, label = "Print", onClick = { })
            SideMenuItem(icon = Icons.Default.Save, label = "Save Copy", onClick = { })
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SideMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, color = TextPrimary)
    }
}
