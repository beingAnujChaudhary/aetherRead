package com.beinganujchaudhary.aetherread.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beinganujchaudhary.aetherread.core.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderSettingsScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { SettingsHeader("Theme") }
            item { SettingsToggle("Light / Dark / Sepia", "Dark") }
            
            item { SettingsHeader("Reading Mode") }
            item { SettingsToggle("Mode", "Continuous") }
            
            item { SettingsHeader("Preferences") }
            item { SettingsToggle("Page Animation", "Enabled") }
            item { SettingsToggle("Read Aloud Speed", "1.0x") }
            item { SettingsToggle("Default Highlight Color", "Yellow") }
        }
    }
}

@Composable
fun SettingsHeader(title: String) {
    Text(title, color = PurplePrimary, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
}

@Composable
fun SettingsToggle(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextPrimary, style = MaterialTheme.typography.bodyLarge)
        Text(value, color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
    }
}
