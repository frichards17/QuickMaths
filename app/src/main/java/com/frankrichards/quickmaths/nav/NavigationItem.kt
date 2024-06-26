package com.frankrichards.quickmaths.nav

enum class Screen {
    MENU,
    PLAY,
    RESULT,
    SETTINGS,
    TUTORIAL
}
sealed class NavigationItem(val route: String) {
    data object Menu : NavigationItem(Screen.MENU.name)
    data object Gameplay : NavigationItem(Screen.PLAY.name)
    data object Result : NavigationItem(Screen.RESULT.name)
    data object Settings: NavigationItem(Screen.SETTINGS.name)
    data object Tutorial: NavigationItem(Screen.TUTORIAL.name)
}