package com.frankrichards.countdownnumbers.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme

@Composable
fun SelectableNumberCard(
    number: Int,
    modifier: Modifier = Modifier,
    index: Int = -1,
    displayNumber: (Int) -> String = { it.toString() },
    selected: Boolean = false,
    addNumber: (Int) -> Unit = {},
    removeNumber: (Int) -> Unit = {},
    onClick: (Int) -> Unit = {},
) {
    NumberCard(
        number = number,
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp)),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        onClick = {
            onClick(number)

            if (selected) {
                removeNumber(index)
            } else {
                addNumber(index)
            }
        },
        displayNumber = displayNumber
    )

}

@Preview
@Composable
fun SelectableNumberCard_Preview() {
    CountdownNumbersTheme {
        SelectableNumberCard(
            1,
            displayNumber = { it.toString() },
            selected = false
        )
    }
}

@Preview
@Composable
fun SelectableNumberCard_Selected_Preview() {
    CountdownNumbersTheme {
        SelectableNumberCard(
            1,
            displayNumber = { it.toString() },
            selected = true
        )
    }
}

