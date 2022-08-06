package de.jeremiasloos.yearlies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import de.jeremiasloos.yearlies.domain.preferences.Preferences
import de.jeremiasloos.yearlies.navigation.Route
import de.jeremiasloos.yearlies.presentation.add.AddScreen
import de.jeremiasloos.yearlies.presentation.main.MainScreen
import de.jeremiasloos.yearlies.presentation.settings.SettingsScreen
import de.jeremiasloos.yearlies.ui.theme.YearliesTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            YearliesTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.MAIN,
                    ) {
                        composable(Route.MAIN) {
                            MainScreen(
                                snackbarHostState = snackbarHostState,
                                onNavigatetoCredits = {
                                    navController.navigate(Route.SETTINGS)
                                },
                                onNavigatetoAdd = {
                                    navController.navigate(Route.ADD)
                                }
                            )
                        }
                        composable(Route.ADD) {
                            AddScreen(
                                snackbarHostState = snackbarHostState,
                                onNavigateUp = {
                                    navController.navigate(Route.MAIN) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                        composable(Route.SETTINGS) {
                            SettingsScreen(
                                snackbarHostState = snackbarHostState,
                                onNavigateUp = {
                                    navController.navigate(Route.MAIN) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}