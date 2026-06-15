package com.beinganujchaudhary.aetherread.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.beinganujchaudhary.aetherread.ui.library.LibraryScreen
import com.beinganujchaudhary.aetherread.ui.library.ToolboxScreen

sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    object Files : BottomNavItem("Files", Icons.Default.List)
    object Toolbox : BottomNavItem("Toolbox", Icons.Default.Build)
    object Sign : BottomNavItem("Aether Sign", Icons.Default.Create)
    object Scanner : BottomNavItem("Scanner", Icons.Default.Search)
}

@Composable
fun MainScreen(
    onNavigateToReader: (String) -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var selectedTab by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Files) }
    val items = listOf(
        BottomNavItem.Files,
        BottomNavItem.Toolbox,
        BottomNavItem.Sign,
        BottomNavItem.Scanner
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = selectedTab == item,
                        onClick = { selectedTab = item },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                is BottomNavItem.Files -> {
                    LibraryScreen(
                        onNavigateToReader = onNavigateToReader,
                        onNavigateToAuth = onNavigateToAuth,
                        onNavigateToProfile = onNavigateToProfile
                    )
                }
                is BottomNavItem.Toolbox -> {
                    ToolboxScreen()
                }
                is BottomNavItem.Sign -> {
                    // Placeholder for Sign
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("Aether Sign - Coming Soon")
                    }
                }
                is BottomNavItem.Scanner -> {
                    // Placeholder for Scanner
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("Scanner - Coming Soon")
                    }
                }
            }
        }
    }
}
