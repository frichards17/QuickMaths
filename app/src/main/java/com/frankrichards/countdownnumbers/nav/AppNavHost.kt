package com.frankrichards.countdownnumbers.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.frankrichards.countdownnumbers.model.AppViewModel
import com.frankrichards.countdownnumbers.screens.*
import java.util.prefs.Preferences

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Menu.route,
    viewModel: AppViewModel = viewModel()
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

        composable(NavigationItem.Menu.route) {
            MainMenu(navigateTo, viewModel)
        }
        composable(NavigationItem.Gameplay.route) {
            Gameplay(navigateTo, viewModel)
        }
        composable(NavigationItem.Result.route) {
            Result(navigateTo, viewModel)
        }
        composable(NavigationItem.Settings.route) {
            Settings(navigateTo)
        }
    }
}