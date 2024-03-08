package com.frankrichards.quickmaths.components

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.lightSurface
import com.frankrichards.quickmaths.ui.theme.lightText

@Composable
fun SliderTicks(steps: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    ) {
        val drawPadding: Float = with(LocalDensity.current) { 10.dp.toPx() }
        val color = MaterialTheme.colorScheme.lightSurface
        Canvas(modifier = Modifier.fillMaxSize()) {
            val distance: Float = (size.width.minus(2 * drawPadding)).div(steps + 1)
            for(index in 0..steps + 1){
                drawCircle(
                    color = color,
                    radius = 4.dp.toPx(),
                    center = Offset(x = drawPadding + index.times(distance), y = size.height / 2)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceSlider(
    title: String,
    steps: Int,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    thumbLabel: String,
    sliderValueChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    thumbSubLabel: String? = null
    ){

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.lightText,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Box(
            contentAlignment = Alignment.Center
        ){

            SliderTicks(steps)
            Slider(
                value = value,
                onValueChange = sliderValueChanged,
                steps = steps,
                valueRange = valueRange,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.lightSurface,
                    inactiveTrackColor = MaterialTheme.colorScheme.lightSurface,
                    activeTickColor = MaterialTheme.colorScheme.lightSurface,
                    inactiveTickColor = MaterialTheme.colorScheme.lightSurface,
                )

            )

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                thumbLabel,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.lightText
            )
            if(thumbSubLabel != null) {
                Text(
                    thumbSubLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.lightText
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreferenceSliderPreview2() {

    var value by remember {
        mutableFloatStateOf(1f)
    }

    var label by remember {
        mutableStateOf("Medium")
    }

    QuickMathsTheme {
        Surface(
            color = MaterialTheme.colorScheme.secondary
        ) {

            PreferenceSlider(
                title = "Difficulty",
                steps = 1,
                value = value,
                valueRange = 1f..3f,
                thumbLabel = label,
                sliderValueChanged = {
                    value = it
                    when(value){
                        30f -> {
                            label = "Hard"
                        }
                        45f -> {
                            label = "Medium"
                        }
                        60f -> {
                            label = "Easy"
                        }
                    }
                }
            )
        }
    }
}