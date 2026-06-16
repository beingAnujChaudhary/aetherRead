package com.beinganujchaudhary.aetherread.ui.reader

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beinganujchaudhary.aetherread.domain.model.Annotation
import com.beinganujchaudhary.aetherread.domain.model.AnnotationCategory
import com.beinganujchaudhary.aetherread.domain.model.AnnotationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnotationPanel(
    onDismiss: () -> Unit,
    viewModel: AnnotationViewModel = hiltViewModel()
) {
    val annotations by viewModel.annotations.collectAsState()

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Annotations",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (annotations.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Text("No annotations yet.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn {
                    items(annotations, key = { it.id }) { annotation ->
                        AnnotationItem(
                            annotation = annotation,
                            onDelete = { viewModel.deleteAnnotation(annotation.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnnotationItem(
    annotation: Annotation,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Badge { Text("Page ${annotation.pageNumber}") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = annotation.category.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                if (!annotation.quote.isNullOrBlank()) {
                    Text(
                        text = "\"${annotation.quote}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = annotation.note,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Annotation", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun AnnotationForm(
    currentPage: Int,
    onDismiss: () -> Unit,
    viewModel: AnnotationViewModel = hiltViewModel()
) {
    var note by remember { mutableStateOf("") }
    var quote by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(AnnotationCategory.IMPORTANT) }
    var selectedType by remember { mutableStateOf<AnnotationType?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Annotation") },
        text = {
            Column {
                Text("Page $currentPage", style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = quote,
                    onValueChange = { quote = it },
                    label = { Text("Quote (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Note") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                Text("Category", style = MaterialTheme.typography.labelMedium)
                // In a real app this would be a dropdown or chip group
                // For simplicity, a small row of chips
                // (Assuming Material3 Chips are available)
                // Note: Material3 FilterChip is available, but for brevity we use simple buttons or text
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    AnnotationCategory.values().take(3).forEach { cat ->
                        TextButton(onClick = { selectedCategory = cat }) {
                            Text(cat.name, color = if (selectedCategory == cat) MaterialTheme.colorScheme.primary else Color.Gray)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.addAnnotation(currentPage, selectedCategory, note, quote.takeIf { it.isNotBlank() }, selectedType)
                    onDismiss()
                },
                enabled = note.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
