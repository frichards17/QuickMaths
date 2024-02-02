package com.frankrichards.countdownnumbers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                        .weight(1f)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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