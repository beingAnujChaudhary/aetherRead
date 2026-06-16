package com.beinganujchaudhary.aetherread.features.reader

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beinganujchaudhary.aetherread.core.theme.*

@Composable
fun TextSelectionContextMenu(modifier: Modifier = Modifier, onDismiss: () -> Unit) {
    Surface(
        modifier = modifier.padding(16.dp),
        color = Surface,
        shadowElevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            ContextMenuItem(icon = Icons.Default.ContentCopy, label = "Copy", onClick = { })
            ContextMenuItem(icon = Icons.Default.Brush, label = "Highlight", onClick = { })
            ContextMenuItem(icon = Icons.Default.FormatUnderlined, label = "Underline", onClick = { })
            ContextMenuItem(icon = Icons.Default.Comment, label = "Comment", onClick = { })
            ContextMenuItem(icon = Icons.Default.Share, label = "Share", onClick = { })
            ContextMenuItem(icon = Icons.Default.MenuBook, label = "Define", onClick = { })
            ContextMenuItem(icon = Icons.Default.AutoAwesome, label = "Explain", onClick = { })
        }
    }
}

@Composable
fun ContextMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(12.dp).clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = label, tint = TextPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, color = TextSecondary, style = MaterialTheme.typography.labelSmall)
    }
}
