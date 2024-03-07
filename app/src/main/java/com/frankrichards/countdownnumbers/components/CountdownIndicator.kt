package com.frankrichards.countdownnumbers.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CountdownIndicator(
    countdown: Int,
    max: Int,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    val progress by animateFloatAsState((countdown-1).toFloat() / (max-1).toFloat(), label = "CountdownProgress", animationSpec = tween(durationMillis = 1000, easing = LinearEasing))
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        val text = if(countdown > 0) "${countdown}s" else "Game over!"
        Box(
            modifier = Modifier
                .height(10.dp)
                .background(color)
                .fillMaxWidth(progress)
        )


        Text(
            text,
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }

}

@Preview
@Composable
fun CountdownIndicator_Preview() {
    CountdownIndicator(countdown = 1, max = 30)
}