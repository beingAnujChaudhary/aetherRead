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
import com.beinganujchaudhary.aetherread.ui.auth.AuthScreen
import com.beinganujchaudhary.aetherread.ui.home.HomeScreen
import com.beinganujchaudhary.aetherread.ui.reader.ReaderScreen

object Route {
    const val MAIN = "main"
    const val AUTH = "auth"
    const val PROFILE = "profile"
    const val READER  = "reader/{documentId}"

    fun reader(documentId: String) = "reader/$documentId"
}

@Composable
fun AetherReadNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.MAIN,
    ) {
        composable(Route.AUTH) {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(Route.MAIN) {
                        popUpTo(Route.AUTH) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.MAIN) {
            HomeScreen(
                onNavigateToReader = { id -> navController.navigate(Route.reader(id)) },
                onNavigateToProfile = { navController.navigate(Route.PROFILE) }
            )
        }

        composable(Route.PROFILE) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onSignOut = {
                    navController.navigate(Route.AUTH) {
                        popUpTo(Route.MAIN) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Route.READER,
            arguments = listOf(
                navArgument("documentId") { type = NavType.StringType }
            ),
        ) { backStackEntry ->
            val documentId = backStackEntry.arguments?.getString("documentId") ?: return@composable
            ReaderScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
