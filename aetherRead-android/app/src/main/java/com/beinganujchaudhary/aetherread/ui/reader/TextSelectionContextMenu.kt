package com.beinganujchaudhary.aetherread.ui.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextSelectionContextMenu(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    // A floating popup context menu
    Box(
        modifier = modifier
            .background(Color(0xFF1E1E1E), RoundedCornerShape(8.dp))
            .width(220.dp)
            .padding(vertical = 8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(Icons.Default.MenuBook, contentDescription = "Dictionary", tint = Color.White)
                Icon(Icons.Default.Edit, contentDescription = "Highlight", tint = Color.White)
                Icon(Icons.Default.FindInPage, contentDescription = "Search", tint = Color.White)
                Text("T", color = Color.White, fontSize = 18.sp)
                Icon(Icons.Default.AutoAwesome, contentDescription = "Format", tint = Color.White)
            }
            
            HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)

            ContextMenuItem("Edit text", Icons.Default.Edit, isPremium = true)
            ContextMenuItem("Copy text", Icons.Default.ContentCopy)
            ContextMenuItem("Read aloud", Icons.Default.VolumeUp)
            ContextMenuItem("Ask AI Assistant", Icons.Default.AutoAwesome)
            ContextMenuItem("Simplify", Icons.Default.AutoAwesome)
            ContextMenuItem("Define", Icons.Default.FindInPage)
        }
    }
}

@Composable
private fun ContextMenuItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isPremium: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO */ }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(1f))
        if (isPremium) {
            Box(modifier = Modifier.background(Color(0xFF8E8CD8), RoundedCornerShape(12.dp)).padding(4.dp)) {
                Icon(Icons.Default.Star, contentDescription = "Premium", tint = Color.White, modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun HorizontalDivider(color: Color, thickness: androidx.compose.ui.unit.Dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .background(color)
    )
}
