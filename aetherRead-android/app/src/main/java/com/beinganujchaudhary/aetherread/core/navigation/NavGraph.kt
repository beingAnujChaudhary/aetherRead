package com.beinganujchaudhary.aetherread.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.beinganujchaudhary.aetherread.features.home.HomeScreen
import com.beinganujchaudhary.aetherread.features.reader.ReaderScreen
import com.beinganujchaudhary.aetherread.features.settings.ReaderSettingsScreen
import com.beinganujchaudhary.aetherread.features.ai.AiComingSoonScreen

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
            ReaderSettingsScreen(onBack = { navController.popBackStack() })
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
        
        // Other routes like Search, Bookmarks, Collections, Tools can be mapped here as they are built.
    }
}
