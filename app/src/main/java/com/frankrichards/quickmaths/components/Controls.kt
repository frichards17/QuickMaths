package com.frankrichards.quickmaths.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.R
import com.frankrichards.quickmaths.model.CalculationNumber
import com.frankrichards.quickmaths.model.Operation
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme

@Composable
fun Controls(
    nums: Array<CalculationNumber>,
    operationClick: (Operation?) -> Unit,
    numberClick: (CalculationNumber) -> Unit,
    back: () -> Unit,
    reset: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        // Calculation Numbers
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                8.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {

            if(nums.count() > 6) {
                // Ensure index is no larger than 9
                // Don't render the last number, it can't be used for anything and more than 9 looks bad
                val maxIndex = minOf(nums.lastIndex, 9)
                Log.d("ControlsTest", "index: $maxIndex")
                for (num in nums.slice(6..maxIndex)) {
                    if (num.isAvailable) {
                        
                        NumberCard(
                            number = num,
                            onClick = numberClick,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.weight(1f)
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

            for(i in 0..<(10 - nums.count())){
                Box(
                    modifier = Modifier
                        .weight(1f)
                )
            }


            CustomIconButton(
                onClick = back,
                iconID = R.drawable.backspace,
                modifier = Modifier.weight(1f)
            )
            CustomIconButton(
                onClick = reset,
                iconID = R.drawable.reset,
                modifier = Modifier.weight(1f)
            )
        }


        // Selected Numbers
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (num in nums.slice(0..<6)) {
                if (num.isAvailable) {

                    NumberCard(
                        number = num,
                        onClick = numberClick,
                        color = MaterialTheme.colorScheme.secondary,
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ControlButton(
                onClick = operationClick,
                operation = Operation.Divide,
                modifier = Modifier.weight(1f)
            )
            ControlButton(
                onClick = operationClick,
                operation = Operation.Multiply,
                modifier = Modifier.weight(1f)
            )
            ControlButton(
                onClick = operationClick,
                operation = Operation.Subtract,
                modifier = Modifier.weight(1f)
            )
            ControlButton(
                onClick = operationClick,
                operation = Operation.Add,
                modifier = Modifier.weight(1f)
            )
            ControlButton(
                label = "=",
                onClick = operationClick,
                operation = null,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Preview
@Composable
fun Controls_Preview() {
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
            value = 2000
        ),
        CalculationNumber(
            index = 8,
            value = 19
        )
    )
    QuickMathsTheme {
        Controls(
            nums = nums,
            operationClick = {},
            numberClick = {},
            back = {},
            reset = {}
        )
    }
}