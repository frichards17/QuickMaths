package com.frankrichards.countdownnumbers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.countdownnumbers.model.Calculation
import com.frankrichards.countdownnumbers.model.CalculationNumber
import com.frankrichards.countdownnumbers.model.Operation

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
    errorMsg: String? = null
) {
    val selectedNums = calculationNumbers
        .slice(0..<6)
        .toTypedArray()
    val resultNums = calculationNumbers
        .slice(6..calculationNumbers.lastIndex)
        .toTypedArray()

    Column(
        modifier = modifier.fillMaxHeight(0.75f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectedCards(
            selectedNums,
            numberClick,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Controls(
            buttonClick = operationClick,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        CalculationBox(
            modifier = Modifier.weight(1f),
            calculations = calculations,
            availableResults = resultNums,
            currentNum1 = currentNum1,
            currentOp = currentOp,
            currentNum2 = currentNum2,
            calcError = calcError,
            resultOnClick = numberClick,
            back = back,
            reset = reset,
            error = errorMsg
        )
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

    GameplayComponent(
        calculationNumbers = nums,
        operationClick = {},
        numberClick = {},
        calculations = arrayOf(),
        back = {},
        reset = {},
        errorMsg = "Hello"
    )
}