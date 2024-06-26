package com.frankrichards.quickmaths.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = LightBlue,
    onPrimary = DarkBlueVariant,
    secondary = DarkBlue,
    onSecondary = White,
    tertiary = DarkBlueVariant,
    background = Black,
    onBackground = OffWhite,
    surface = OffBlack,
    error = Red,
    onError = Pink
)

private val LightColorScheme = lightColorScheme(
    primary = DarkBlue,
    onPrimary = White,
    secondary = LightBlue,
    onSecondary = DarkBlueVariant,
    tertiary = LightBlueVariant,
    background = White,
    onBackground = Black,
    surface = OffWhite,
    error = Red,
    onError = Pink,

    /* Other default colors to override

    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val ColorScheme.surfaceBorder: Color
    get() {
        return Grey
    }



val ColorScheme.positive: Color
    get() {
        return Green
    }

val ColorScheme.onPositive: Color
    get() {
        return LightGreen
    }

val ColorScheme.lightText: Color
    get() {
        return White
    }

val ColorScheme.lightSurface: Color
    get() {
        return OffWhite
    }

val ColorScheme.secondaryOnBackground: Color
    @Composable
    get() {
        return if(isSystemInDarkTheme()){
            LightGrey
        }else{
            DarkGrey
        }
    }



@Composable
fun QuickMathsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}