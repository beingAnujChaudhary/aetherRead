package com.atherread.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.atherread.features.home.HomeScreen
import com.atherread.features.reader.ReaderScreen
import com.atherread.features.ai.AiComingSoonScreen
import com.atherread.features.search.SearchScreen
import com.atherread.features.bookmarks.BookmarksScreen
import com.atherread.features.collections.CollectionsScreen
import com.atherread.features.tools.ToolsScreen
import com.atherread.features.settings.SettingsScreen

@Composable
fun AetherReadNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToPdf = { path -> navController.navigate(Screen.Reader.createRoute(path)) }
            )
        }

        composable(
            route = Screen.Reader.route,
            arguments = listOf(navArgument("pdfPath") { type = NavType.StringType })
        ) { backStackEntry ->
            val pdfPath = backStackEntry.arguments?.getString("pdfPath") ?: return@composable
            ReaderScreen(
                documentId = pdfPath,
                onBack = { navController.popBackStack() },
                onNavigateToAiPlaceholder = { feature -> navController.navigate(Screen.AiPlaceholder.createRoute(feature)) }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }

        composable(
            route = Screen.AiPlaceholder.route,
            arguments = listOf(navArgument("featureName") { type = NavType.StringType })
        ) { backStackEntry ->
            val featureName = backStackEntry.arguments?.getString("featureName") ?: "AI Feature"
            AiComingSoonScreen(
                featureName = featureName,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Search.route) { SearchScreen() }
        composable(Screen.Bookmarks.route) { BookmarksScreen() }
        composable(Screen.Collections.route) { CollectionsScreen() }
        composable(Screen.Tools.route) { ToolsScreen() }
    }
}
