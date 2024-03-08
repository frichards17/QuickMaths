package com.frankrichards.quickmaths.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.model.Calculation
import com.frankrichards.quickmaths.model.CalculationNumber
import com.frankrichards.quickmaths.model.Operation
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme

@Composable
fun GameplayComponent(
    calculationNumbers: Array<CalculationNumber>,
    numberClick: (CalculationNumber) -> Unit,
    operationClick: (Operation?) -> Unit,
    back: () -> Unit,
    reset: () -> Unit,
    calculations: Array<Calculation>,
    modifier: Modifier = Modifier,
    currentNum1: Int? = null,
    currentOp: Operation? = null,
    currentNum2: Int? = null,
    calcError: Boolean = false,
    errorMsg: String? = null,
    gameOver: Boolean = false
) {
    val selectedNums = calculationNumbers
        .slice(0..<6)
        .toTypedArray()
    val resultNums = calculationNumbers
        .slice(6..calculationNumbers.lastIndex)
        .toTypedArray()

    Column(
        modifier = modifier,//.background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CalculationBox(
            calculations = calculations,
            currentNum1 = currentNum1,
            currentOp = currentOp,
            currentNum2 = currentNum2,
            modifier = Modifier.weight(1f),
            calcError = calcError,
            error = errorMsg
        )
        AnimatedVisibility(
            visible = !gameOver,
            exit = slideOutVertically(animationSpec = tween(2000)) { it }
        ){
            Controls(
                nums = calculationNumbers,
                operationClick = operationClick,
                numberClick = numberClick,
                back = back,
                reset = reset
            )
        }
    }
}

@Preview
@Composable
fun GameplayComponent_Preview() {
    val nums = arrayOf(
        CalculationNumber(
            index = 0,
            value = 10
        ),
        CalculationNumber(
            index = 2,
            value = 9
        ),
        CalculationNumber(
            index = 3,
            value = 2
        ),
        CalculationNumber(
            index = 4,
            value = 25
        ),
        CalculationNumber(
            index = 5,
            value = 50
        ),
        CalculationNumber(
            index = 6,
            value = 100
        ),
        CalculationNumber(
            index = 7,
            value = 200
        ),
        CalculationNumber(
            index = 8,
            value = 19
        )
    )
    QuickMathsTheme {
        GameplayComponent(
            calculationNumbers = nums,
            operationClick = {},
            numberClick = {},
            calculations = arrayOf(),
            back = {},
            reset = {},
            errorMsg = "Hello",
            currentNum1 = 75,
            currentOp = Operation.Add,
            currentNum2 = 100
        )
    }
}