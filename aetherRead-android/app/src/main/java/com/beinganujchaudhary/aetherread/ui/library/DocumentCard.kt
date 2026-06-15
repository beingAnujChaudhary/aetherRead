package com.beinganujchaudhary.aetherread.ui.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.beinganujchaudhary.aetherread.domain.model.Document
import java.io.File

@Composable
fun DocumentCard(
    document: Document,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Thumbnail or Icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (document.coverPageUri != null && File(document.coverPageUri).exists()) {
                Image(
                    painter = rememberAsyncImagePainter(model = File(document.coverPageUri)),
                    contentDescription = "Cover for ${document.title}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Document Details
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = document.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Mock Xodo Drive icon style
                Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "System Files",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        // Context Menu
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options", tint = Color.Gray)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Delete", color = MaterialTheme.colorScheme.error) },
                    onClick = {
                        expanded = false
                        onDelete()
                    }
                )
            }
        }
    }
}
