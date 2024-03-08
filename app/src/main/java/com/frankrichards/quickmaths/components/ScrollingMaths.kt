package com.frankrichards.quickmaths.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.calculation
import com.frankrichards.quickmaths.util.Utility
import kotlinx.coroutines.delay

@Composable
fun ScrollingMaths(
    textColor: Color = MaterialTheme.colorScheme.tertiary,
    n: Int = LocalConfiguration.current.screenHeightDp / 100
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ScrollingMaths")
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val offsets by remember { mutableStateOf(Utility.getRandomOffsets(n)) }
    val calcs by remember { mutableStateOf(Utility.getRandomCalculations(n)) }

    val xOffsets = remember {
        (0..<n).map {
            Animatable(-200f)
        }
    }

    LaunchedEffect(xOffsets) {
        for(offset in xOffsets) {
            delay((0..1000).random().toLong())
            offset.animateTo(
                targetValue = screenWidth.toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(5000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {

        for(i in 0..<n) {
            Text(
                calcs[i].toString(),
                color = textColor,
                style = MaterialTheme.typography.calculation,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(200.dp)
                    .offset(
                        x = if(i % 2 == 0) {
                            infiniteTransition.animateValue(
                                initialValue = (-200).dp,
                                targetValue = screenWidth.dp,
                                typeConverter = Dp.VectorConverter,
                                animationSpec = infiniteRepeatable(
                                    tween(
                                        (6000..9000).random(),
                                        delayMillis = (0..1000).random(),
                                        easing = LinearEasing
                                    ),
                                    repeatMode = RepeatMode.Restart
                                ),
                                label = "ScrollingMaths"
                            ).value
                        }else{
                            infiniteTransition.animateValue(
                                initialValue = screenWidth.dp,
                                targetValue = (-200).dp,
                                typeConverter = Dp.VectorConverter,
                                animationSpec = infiniteRepeatable(
                                    tween(
                                        (4000..6000).random(),
                                        delayMillis = (0..1000).random(),
                                        easing = LinearEasing
                                    ),
                                    repeatMode = RepeatMode.Restart
                                ),
                                label = "ScrollingMaths"
                            ).value
                             },
                        y = offsets[i]
                    )
            )
        }
    }
}

@Preview
@Composable
fun ScrollingMaths_Preview() {
    QuickMathsTheme {
        ScrollingMaths()
    }
}

