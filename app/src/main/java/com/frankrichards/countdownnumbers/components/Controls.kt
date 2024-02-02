package com.frankrichards.countdownnumbers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.countdownnumbers.model.Operation

@Composable
fun Controls(
    buttonClick: (Operation?) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ControlButton(
            onClick = buttonClick,
            operation = Operation.Add,
            modifier = Modifier
                .weight(1f)
        )
        ControlButton(
            onClick = buttonClick,
            operation = Operation.Subtract,
            modifier = Modifier
                .weight(1f)
        )

        ControlButton(
            onClick = buttonClick,
            operation = Operation.Multiply,
            modifier = Modifier
                .weight(1f)
        )

        ControlButton(
            onClick = buttonClick,
            operation = Operation.Divide,
            modifier = Modifier
                .weight(1f)
        )

        ControlButton(
            label = "=",
            onClick = buttonClick,
            operation = null,
            modifier = Modifier
                .weight(1f)
        )
    }


}

@Preview
@Composable
fun Controls_Preview() {
    Controls(
        buttonClick = {}
    )
}