package com.frankrichards.quickmaths.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.frankrichards.quickmaths.model.Calculation
import com.frankrichards.quickmaths.model.CalculationNumber
import com.frankrichards.quickmaths.model.Operation
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme

@Composable
fun CalculationDialog(
    calculation: Calculation,
    onAnswer: (Int) -> Unit
) {
    Dialog(onDismissRequest = {}) {
        CalculationCard(calculation = calculation, onAnswer = onAnswer)
    }
}

@Composable
fun CalculationCard(
    calculation: Calculation,
    onAnswer: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                calculation.getQuestion(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for(option in calculation.possibleSolutions!!){
                    NumberCard(
                        number = option,
                        onClick = onAnswer,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CalculationDialog_Preview() {
    QuickMathsTheme {
        CalculationDialog(
            calculation = Calculation(
                CalculationNumber(0, 75),
                Operation.Multiply,
                CalculationNumber(1, 50)
            ),
            {}
        )
    }
}