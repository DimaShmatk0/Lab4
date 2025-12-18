package com.example.lab3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreen(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Counter : AppScreen(NavigationDestinations.Counter, "Counter", Icons.Default.Home)
    object TextDisplay : AppScreen(NavigationDestinations.TextDisplay, "Text", Icons.Default.Call)
    object EmailMain : AppScreen(NavigationDestinations.EmailMain, "Mail", Icons.Default.Email)
    object EmailCompose :
        AppScreen(NavigationDestinations.EmailCompose, "Compose", Icons.Default.Email)

    object ItemSearch : AppScreen(NavigationDestinations.ItemSearch, "Search", Icons.Default.Search)
}

val mainScreens = listOf(
    AppScreen.Counter,
    AppScreen.TextDisplay,
    AppScreen.EmailMain,
    AppScreen.ItemSearch
)


object NavigationDestinations {
    // Основні екрани
    const val Counter = "counter"
    const val TextDisplay = "form"
    const val ItemSearch = "search"

    // Граф пошти
    const val EmailNavGraph = "email_nav_graph"
    const val EmailMain = "email_main_screen"
    const val EmailCompose = "email_compose_screen"
}

