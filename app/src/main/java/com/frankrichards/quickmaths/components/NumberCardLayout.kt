package com.frankrichards.quickmaths.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.util.Utility

@Composable
fun NumberCardLayout(
    numbers: IntArray,
    addNumber: (Int) -> Unit,
    removeNumber: (Int) -> Unit,
    selectedCards: IntArray,
    modifier: Modifier = Modifier
){

    val displayNumber: (Int) -> String = {
        if(it > 10) "L" else "S"
    }

    val colorStops1 = arrayOf(
        0.85f to Color.Transparent,
        1f to Color.Black
    )

    val colorStops2 = arrayOf(
        0f to Color.Transparent,
        0.6f to Color.Transparent,
        0.8f to Color.Black.copy(alpha = 0.20f),
        1f to Color.Black.copy(alpha = 1f)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy((-40).dp),
        modifier = modifier
    ) {
        Box{
            CustomCard(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .matchParentSize()
            ){}

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)

            ) {
                numbers.slice(0..<4).forEachIndexed { i, number ->
                    SelectableNumberCard(
                        index = i,
                        number = number,
                        addNumber = addNumber,
                        removeNumber = removeNumber,
                        displayNumber = displayNumber,
                        selected = selectedCards.contains(i),
                        modifier = Modifier
                            .weight(1f),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Brush.verticalGradient(colorStops = colorStops1))
            )
        }

        Box(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            ) {
                numbers.slice(4..<10).forEachIndexed { i, number ->
                    val index = i + 4
                    SelectableNumberCard(
                        index = index,
                        number = number,
                        addNumber = addNumber,
                        removeNumber = removeNumber,
                        displayNumber = displayNumber,
                        selected = selectedCards.contains(index),
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Brush.verticalGradient(colorStops = colorStops2))
            )
        }

        Box(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            ) {
                numbers.slice(10..<17).forEachIndexed { i, number ->
                    val index = i + 10
                    SelectableNumberCard(
                        index = 10 + i,
                        number = number,
                        addNumber = addNumber,
                        removeNumber = removeNumber,
                        displayNumber = displayNumber,
                        selected = selectedCards.contains(index),
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Brush.verticalGradient(colorStops = colorStops2))
            )
        }
        Box(
            modifier = Modifier.padding(top = 12.dp)
        ){
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            ) {
                numbers.slice(17..<24).forEachIndexed { i, number ->
                    val index = i + 17
                    SelectableNumberCard(
                        index = index,
                        number = number,
                        addNumber = addNumber,
                        removeNumber = removeNumber,
                        displayNumber = displayNumber,
                        selected = selectedCards.contains(index),
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NumberCardLayout_Preview() {
    QuickMathsTheme {

        NumberCardLayout(
            numbers = Utility.getCardNumbers(),
            addNumber = {},
            removeNumber = {},
            selectedCards = intArrayOf(0, 5, 11, 20)
        )

    }
}