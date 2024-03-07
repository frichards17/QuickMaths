package com.frankrichards.countdownnumbers.nav

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.frankrichards.countdownnumbers.data.DataStoreManager
import com.frankrichards.countdownnumbers.model.AppViewModel
import com.frankrichards.countdownnumbers.screens.*
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme
import java.util.prefs.Preferences

val ANIMATION_DURATION = 300

val enterVertical =
    slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

val popEnterVertical =
    slideInVertically(
        initialOffsetY = { -it },
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

val enterHorizontal =
    slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

val popEnterHorizontal =
    slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

val exitVertical =
    slideOutVertically(
        targetOffsetY = { -it },
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

val popExitVertical =
    slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

val exitHorizontal =
    slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

val popExitHorizontal =
    slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Menu.route,
    settings: DataStoreManager = DataStoreManager(LocalContext.current)
) {
    val viewModel = AppViewModel(settings)
    val darkMode by viewModel.settings.darkModeFlow.collectAsState(initial = false)

    CountdownNumbersTheme(
        darkTheme = darkMode
    ) {

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
                        NavigationItem.Settings.route -> enterVertical
                        NavigationItem.Result.route -> popEnterVertical
                        else -> enterHorizontal
                    }
                },
                exitTransition = {
                    when (this.targetState.destination.route) {
                        NavigationItem.Settings.route -> exitVertical
                        else -> exitHorizontal
                    }
                },
                popEnterTransition = {
                    when (this.initialState.destination.route) {
                        NavigationItem.Settings.route -> popEnterVertical
                        NavigationItem.Result.route -> enterVertical
                        else -> popEnterHorizontal
                    }
                },
                popExitTransition = {
                    when (this.initialState.destination.route) {
                        NavigationItem.Settings.route -> popExitVertical
                        else -> popExitHorizontal
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
                        NavigationItem.Result.route -> popEnterHorizontal
                        else -> enterHorizontal
                    }
                },
                exitTransition = {
                    exitHorizontal
                },
                popEnterTransition = {
                    popEnterHorizontal
                },
                popExitTransition = {
                    popExitHorizontal
                }
            ) {
                Gameplay(navigateTo, viewModel)
            }

            // RESULT
            composable(
                NavigationItem.Result.route,
                enterTransition = {
                    enterHorizontal
                },
                exitTransition = {
                    when(this.targetState.destination.route){
                        NavigationItem.Menu.route -> exitVertical
                        NavigationItem.Gameplay.route -> popExitHorizontal
                        else -> null
                    }
                }
            ) {
                Result(navigateTo, viewModel)
            }
            composable(
                NavigationItem.Settings.route,
                enterTransition = {
                    enterVertical
                },
                exitTransition = {
                    exitVertical
                },
                popExitTransition = {
                    popExitVertical
                }
            ) {
                Settings(navigateTo, viewModel)
            }
        }
    }
}
