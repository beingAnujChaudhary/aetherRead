package com.beinganujchaudhary.aetherread.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/**
 * Top-level navigation graph for AetherRead.
 *
 * Destinations:
 *   - [Route.LIBRARY]  → document library (home)
 *   - [Route.READER]   → PDF reader (takes documentId arg)
 */
object Route {
    const val LIBRARY = "library"
    const val READER  = "reader/{documentId}"

    fun reader(documentId: String) = "reader/$documentId"
}

@Composable
fun AetherReadNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.LIBRARY,
    ) {
        composable(Route.LIBRARY) {
            // TODO (Phase 3): LibraryScreen(navController)
            LibraryPlaceholder()
        }

        composable(
            route = Route.READER,
            arguments = listOf(
                navArgument("documentId") { type = NavType.StringType }
            ),
        ) { backStackEntry ->
            val documentId = backStackEntry.arguments?.getString("documentId") ?: return@composable
            // TODO (Phase 3): ReaderScreen(documentId, navController)
            ReaderPlaceholder(documentId)
        }
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Placeholder composables — replaced in Phase 3
// ──────────────────────────────────────────────────────────────────────────────

@Composable
private fun LibraryPlaceholder() {
    // Replaced by fully implemented LibraryScreen in Phase 3
}

@Composable
private fun ReaderPlaceholder(documentId: String) {
    // Replaced by fully implemented ReaderScreen in Phase 3
}
