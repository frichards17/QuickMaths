package com.frankrichards.countdownnumbers.components

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.frankrichards.countdownnumbers.R
import com.frankrichards.countdownnumbers.model.Calculation
import com.frankrichards.countdownnumbers.model.CalculationNumber
import com.frankrichards.countdownnumbers.model.Operation
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme
import com.frankrichards.countdownnumbers.ui.theme.error
import com.frankrichards.countdownnumbers.ui.theme.surfaceBorder

@Composable
fun CalculationBox(
    calculations: Array<Calculation>,
    availableResults: Array<CalculationNumber>,
    modifier: Modifier = Modifier,
    currentNum1: Int? = null,
    currentOp: Operation? = null,
    currentNum2: Int? = null,
    calcError: Boolean = false,
    resultOnClick: (CalculationNumber) -> Unit,
    back: () -> Unit,
    reset: () -> Unit,
    error: String? = null
) {
    val listState = rememberLazyListState()

    LaunchedEffect(availableResults) {
        listState.animateScrollBy(1000f, animationSpec = tween(
            durationMillis = 2000,
            easing = EaseOut
        ))
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Divider(
                thickness = 4.dp,
                color = MaterialTheme.colorScheme.surfaceBorder
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {

                for (i in calculations.indices) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            val solution = availableResults[i]
                            val solutionColor = if(solution.isAvailable){
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
//                            if(solution.value != calculations[i].selectedSolution){
//                                Log.e("CalculationBox", "ERROR SOLUTIONS DON'T MATCH")
//                            }
                            NumberCard(
                                number = calculations[i].number1,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                calculations[i].operation.label,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.weight(1f)
                            )
                            NumberCard(
                                number = calculations[i].number2,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "=",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.weight(1f)
                            )
                            NumberCard(
                                number = solution,
                                color = solutionColor,
                                onClick=resultOnClick,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                if (currentNum1 != null) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(8.dp)
//                        .optionalBorder(
//                            show = calcError,
//                            color = MaterialTheme.colorScheme.error,
//                            width = 4.dp,
//                            shape = RoundedCornerShape(10.dp)
//                        )
                                .padding(8.dp)

                        ) {
                            NumberCard(
                                number = currentNum1,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.weight(1f)
                            )
                            if (currentOp != null) {
                                Text(
                                    currentOp.label,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(1f)
                                )
                            } else {
                                Box(modifier = Modifier.weight(1f))
                            }
                            if (currentNum2 != null) {
                                NumberCard(
                                    number = currentNum2,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.weight(1f)
                                )
                            } else {
                                Box(modifier = Modifier.weight(1f))
                            }
                            if (calcError) {
                                Text(
                                    "=",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                SquareCard(
                                    text = "!",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.weight(1f)
                                )

                            } else {
                                Box(modifier = Modifier.weight(1f))
                                Box(modifier = Modifier.weight(1f))
                            }

                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                if(error != null){
                    Text(
                        error,
                        style = MaterialTheme.typography.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f, fill = false).padding(horizontal = 8.dp)
                    )
                }
                CustomIconButton(
                    onClick = back,
                    iconID = R.drawable.backspace
                )
                CustomIconButton(
                    onClick = reset,
                    iconID = R.drawable.reset
                )
            }


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

    CountdownNumbersTheme {
        CalculationBox(
            calculations = arrayOf(
                calc
            ),
            availableResults = arrayOf(
                CalculationNumber(
                    index = 0,
                    value = 15
                )
            ),
            currentNum1 = 1,
            currentOp = Operation.Divide,
            resultOnClick = {},
            back = {},
            reset = {},
            error = "One of your calculations is wrong!"
        )
    }
}