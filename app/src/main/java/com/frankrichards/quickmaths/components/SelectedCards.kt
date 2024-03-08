package com.frankrichards.quickmaths.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.model.CalculationNumber
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme

@Composable
fun SelectedCards(
    numbers: Array<CalculationNumber>,
    numberClick: (CalculationNumber) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (number in numbers) {
            
            if (number.isAvailable) {
                NumberCard(
                    number = number,
                    onClick = numberClick,
                    modifier = Modifier
                        .weight(1f)
                )
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(10.dp)
                        )
                )
            }
        }
    }

}

@Preview
@Composable
fun SelectedCards_Preview() {
    QuickMathsTheme {
        SelectedCards(
            arrayOf(),
            numberClick = {}
        )
    }
}
