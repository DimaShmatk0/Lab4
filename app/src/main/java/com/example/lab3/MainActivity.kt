package com.example.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lab3.navigation.AppNavGraph
import com.example.lab3.navigation.AppScreen
import com.example.lab3.navigation.mainScreens
import com.example.lab3.ui.theme.Lab1Theme
import com.example.lab3.viewmodel.CounterViewModel
import com.example.lab3.viewmodel.db.AppViewModel

class MainActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels {
        AppViewModel.AppViewModelFactory(
            (application as MyApplication).foodRepository,
            (application as MyApplication).categoryRepository
        )
    }
    private val counterViewModel: CounterViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Lab1App(counterViewModel, appViewModel)
            }
        }
    }
}


@Composable
fun Lab1App(counterViewModel: CounterViewModel, appViewModel: AppViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: AppScreen.EmailMain.route

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            mainScreens.forEach { screen ->
                item(
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(screen.icon, contentDescription = screen.label) },
                    label = { Text(screen.label) }
                )
            }
        }
    ) {
        AppNavGraph(navController, counterViewModel,appViewModel)
    }
}