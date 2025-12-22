package com.example.lab3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.lab3.ui.screen.CounterScreen
import com.example.lab3.ui.screen.EchoInputScreen
import com.example.lab3.ui.screen.PostListScreen
import com.example.lab3.ui.screen.SearchScreen
import com.example.lab3.ui.screen.mail.MailComposeScreen
import com.example.lab3.ui.screen.mail.MailMainScreen
import com.example.lab3.viewmodel.CounterViewModel
import com.example.lab3.viewmodel.db.AppViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    counterViewModel: CounterViewModel,
    appViewModel: AppViewModel
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.TextDisplay.route
    ) {
        composable(NavigationDestinations.Counter) {
            CounterScreen(counterViewModel)
        }
        composable(NavigationDestinations.TextDisplay) {
            EchoInputScreen(appViewModel)
        }
        composable(NavigationDestinations.ItemSearch) {
            SearchScreen()
        }
        composable(NavigationDestinations.PostList) {
            PostListScreen() // Не передаємо параметри - ViewModel створюється всередині
        }
        navigation(
            startDestination = NavigationDestinations.EmailMain,
            route = NavigationDestinations.EmailNavGraph
        ) {
            composable(NavigationDestinations.EmailMain) {
                MailMainScreen(
                    onNavigateToCompose = {
                        navController.navigate(NavigationDestinations.EmailCompose)
                    }
                )
            }
            composable(NavigationDestinations.EmailCompose) {
                MailComposeScreen(onBack = { navController.navigateUp() })
            }
        }
    }
}