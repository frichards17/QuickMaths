package com.frankrichards.quickmaths.util

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

object Animation {

    private const val ANIMATION_DURATION = 300

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
}