package com.frankrichards.quickmaths.components

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.model.Calculation
import com.frankrichards.quickmaths.model.CalculationNumber
import com.frankrichards.quickmaths.model.Operation
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.error

@Composable
fun CalculationBox(
    calculations: Array<Calculation>,
    modifier: Modifier = Modifier,
    currentNum1: Int? = null,
    currentOp: Operation? = null,
    currentNum2: Int? = null,
    calcError: Boolean = false,
    error: String? = null
) {
    val listState = rememberLazyListState()

    LaunchedEffect(currentNum1) {
        listState.animateScrollBy(
            1000f, animationSpec = tween(
                durationMillis = 2000,
                easing = EaseOut
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Divider(
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
            modifier = Modifier.weight(1f)
        ) {

            calculations.forEachIndexed { i, calculation ->
                item(
                    key = "Calculation $i"
                ) {
                    Text(
                        calculation.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                }

            }

            if (currentNum1 != null) {
                item(key = "CurrentOp") {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            currentNum1.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )

                        if (currentOp != null) {
                            Text(
                                currentOp.label,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        if (currentNum2 != null) {
                            Text(
                                currentNum2.toString(),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        if(calcError){
                            Text(
                                "=?",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
        if (error != null) {
            Text(
                error,
                style = MaterialTheme.typography.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }


}

fun Modifier.optionalBorder(
    show: Boolean,
    width: Dp,
    color: Color,
    shape: Shape
): Modifier {
    return if (show) {
        this.border(width, color, shape)
    } else {
        this
    }
}


@Preview
@Composable
fun CalculationBox_Preview() {

    val calc = Calculation(
        number1 = CalculationNumber(0, 10),
        operation = Operation.Add,
        number2 = CalculationNumber(1, 5)
    )
    calc.selectedSolution = 15

    QuickMathsTheme {
        CalculationBox(
            calculations = arrayOf(calc),
            currentNum1 = 15,
            currentOp = Operation.Add,
            currentNum2 = 10,
            calcError = true,
            error = "One of your calculations is wrong!"
        )
    }
}