package com.frankrichards.quickmaths.nav

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.frankrichards.quickmaths.components.ScrollingMaths
import com.frankrichards.quickmaths.data.DataStoreManager
import com.frankrichards.quickmaths.model.AppViewModel
import com.frankrichards.quickmaths.screens.*
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.util.Animation

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Menu.route,
    settings: DataStoreManager = DataStoreManager(LocalContext.current)
) {
    val viewModel = AppViewModel(settings)
    val darkMode by viewModel.settings.darkModeFlow.collectAsState(initial = false)

    QuickMathsTheme(
        darkTheme = darkMode
    ) {

        Surface(
           color = MaterialTheme.colorScheme.secondary
        ) {
            ScrollingMaths()
            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = startDestination
            ) {
                val navigateTo: (String) -> Unit = {
                    navController.navigate(it) {
                        popUpTo(NavigationItem.Menu.route)
                        launchSingleTop = true
                    }
                }

                // MENU
                composable(
                    NavigationItem.Menu.route,
                    enterTransition = {
                        when (this.initialState.destination.route) {
                            NavigationItem.Settings.route -> Animation.enterVertical
                            NavigationItem.Result.route -> Animation.popEnterVertical
                            else -> Animation.enterHorizontal
                        }
                    },
                    exitTransition = {
                        when (this.targetState.destination.route) {
                            NavigationItem.Settings.route -> Animation.exitVertical
                            else -> Animation.exitHorizontal
                        }
                    },
                    popEnterTransition = {
                        when (this.initialState.destination.route) {
                            NavigationItem.Settings.route -> Animation.popEnterVertical
                            NavigationItem.Result.route -> Animation.enterVertical
                            else -> Animation.popEnterHorizontal
                        }
                    },
                    popExitTransition = {
                        when (this.initialState.destination.route) {
                            NavigationItem.Settings.route -> Animation.popExitVertical
                            else -> Animation.popExitHorizontal
                        }
                    }
                ) {
                    MainMenu(navigateTo, viewModel)
                }

                // GAMEPLAY
                composable(
                    NavigationItem.Gameplay.route,
                    enterTransition = {
                        when(this.initialState.destination.route){
                            NavigationItem.Result.route -> Animation.popEnterHorizontal
                            else -> Animation.enterHorizontal
                        }
                    },
                    exitTransition = {
                        Animation.exitHorizontal
                    },
                    popEnterTransition = {
                        Animation.popEnterHorizontal
                    },
                    popExitTransition = {
                        Animation.popExitHorizontal
                    }
                ) {
                    Gameplay(navigateTo, viewModel)
                }

                // RESULT
                composable(
                    NavigationItem.Result.route,
                    enterTransition = {
                        Animation.enterHorizontal
                    },
                    exitTransition = {
                        when(this.targetState.destination.route){
                            NavigationItem.Menu.route -> Animation.exitVertical
                            NavigationItem.Gameplay.route -> Animation.popExitHorizontal
                            else -> null
                        }
                    }
                ) {
                    Result(navigateTo, viewModel)
                }

                // SETTINGS
                composable(
                    NavigationItem.Settings.route,
                    enterTransition = {
                        Animation.enterVertical
                    },
                    exitTransition = {
                        Animation.exitVertical
                    },
                    popExitTransition = {
                        Animation.popExitVertical
                    }
                ) {
                    Settings(navigateTo, viewModel)
                }

                // TUTORIAL
                composable(
                    NavigationItem.Tutorial.route,
                    enterTransition = {
                        Animation.enterHorizontal
                    },
                    exitTransition = {
                        Animation.exitHorizontal
                    },
                    popEnterTransition = {
                        Animation.popEnterHorizontal
                    },
                    popExitTransition = {
                        Animation.popExitHorizontal
                    }
                ) {
                    Tutorial(navigateTo, appViewModel = viewModel)
                }
            }
        }

    }
}
