package com.frankrichards.countdownnumbers.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.frankrichards.countdownnumbers.model.CalculationNumber

@Composable
fun NumberCard(
    number: Int,
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    displayNumber: (Int) -> String = { it.toString() },
    onClick: ((Int) -> Unit)? = null
) {
    if(onClick == null) {
        SquareCard(
            text = displayNumber(number),
            color = color,
            modifier = modifier
        )
    }else{
        SquareCard(
            text = displayNumber(number),
            color = color,
            modifier = modifier,
            onClick = {onClick(number)}
        )
    }
}

@Composable
fun NumberCard(
    number: CalculationNumber,
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    displayNumber: (Int) -> String = { it.toString() },
    onClick: ((CalculationNumber) -> Unit)? = null,
) {
    if (onClick == null) {
        SquareCard(
            text = displayNumber(number.value),
            color = color,
            modifier = modifier
        )
    } else {
        SquareCard(
            text = displayNumber(number.value),
            onClick = { onClick(number) },
            color = color,
            modifier = modifier
        )
    }
}