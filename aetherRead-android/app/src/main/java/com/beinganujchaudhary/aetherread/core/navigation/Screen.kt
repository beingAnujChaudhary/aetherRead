package com.beinganujchaudhary.aetherread.core.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Reader : Screen("reader/{pdfPath}") {
        fun createRoute(pdfPath: String) = "reader/$pdfPath"
    }
    data object Search : Screen("search")
    data object Bookmarks : Screen("bookmarks")
    data object Settings : Screen("settings")
    data object Collections : Screen("collections")
    data object Tools : Screen("tools")
    data object AiPlaceholder : Screen("ai_placeholder/{featureName}") {
        fun createRoute(featureName: String) = "ai_placeholder/$featureName"
    }
}
