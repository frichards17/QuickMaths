package com.frankrichards.quickmaths.components

import android.graphics.drawable.Icon
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.IntermediateMeasureScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.R
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme

@Composable
fun CountdownIndicator(
    countdown: Int,
    max: Int,
    skip: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    isInfinite: Boolean = false,
    skipEnabled: Boolean = true
) {

    Column(
        verticalArrangement = Arrangement.spacedBy((-8).dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {

        if(isInfinite) {
            ProgressBar(
                value = 10,
                max = 10,
                color = color,
                skip = skip,
                skipEnabled
            )
        }else{
            val text = if(countdown > 0) "${countdown}s" else "Game over!"

            ProgressBar(
                value = countdown,
                max = max,
                color = color,
                skip = skip,
                skipEnabled
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

}

@Composable
fun ProgressBar(
    value: Int,
    max: Int,
    color: Color = MaterialTheme.colorScheme.primary,
    skip: () -> Unit,
    skipEnabled: Boolean
) {
    val progress by animateFloatAsState((value-1).toFloat() / (max-1).toFloat(), label = "CountdownProgress", animationSpec = tween(durationMillis = 1000, easing = LinearEasing))
    Box(
        contentAlignment = Alignment.CenterStart
    ){
        Box(
            modifier = Modifier
                .height(10.dp)
                .background(color)
                .fillMaxWidth(progress)
        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        ){
            SkipButton(
                skip,
                skipEnabled
            )
        }

    }
}

@Composable
fun SkipButton(
    onClick: () -> Unit,
    enabled: Boolean
) {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        shape = CircleShape,
        modifier = Modifier
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
            modifier = Modifier.padding(4.dp),
            onClick = onClick,
            enabled = enabled
        ) {
            Icon(
                painterResource(R.drawable.skip),
                contentDescription = "Icon",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun CountdownIndicator_Preview() {
    QuickMathsTheme {
        Surface(
            color = MaterialTheme.colorScheme.secondary
        ) {

            CountdownIndicator(countdown = 30, max = 30, {})
        }
    }
}

@Preview
@Composable
fun CountdownIndicator_Preview2() {
    QuickMathsTheme {
        CountdownIndicator(countdown = 30, max = 30, isInfinite = true, skip = {})
    }
}