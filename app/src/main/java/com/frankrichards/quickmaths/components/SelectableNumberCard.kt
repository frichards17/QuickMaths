package com.frankrichards.quickmaths.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme

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
    aspectRatio: Float = 1f,
    fontSize: TextUnit? = null
) {

    val topPadding by animateDpAsState(
        if(selected){
            0.dp
        }else{
            24.dp
        }, label = "Card Selection"
    )

    val bottomPadding by animateDpAsState(
        if(selected){
            24.dp
        }else{
            0.dp
        }, label = "Card Selection"
    )

    val color by animateColorAsState(
        if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary
        }, label = "Card Selection"
    )

    NumberCard(
        number = number,
        modifier = modifier
            .padding(
                top = topPadding,
                bottom = bottomPadding
            )
            .then(if(selected){
                Modifier.shadow(
                    10.dp,
                    shape = RoundedCornerShape(10.dp),
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
            }else{
                Modifier
            })
            ,
        color = color,
        onClick = {
            onClick(number)

            if (selected) {
                removeNumber(index)
            } else {
                addNumber(index)
            }
        },
        displayNumber = displayNumber,
        aspectRatio = aspectRatio,
        fontSize = fontSize
    )

}

@Preview
@Composable
fun SelectableNumberCard_Preview() {
    QuickMathsTheme {
        SelectableNumberCard(
            1,
            displayNumber = { it.toString() },
            selected = false,
            aspectRatio = 1f
        )
    }
}

@Preview
@Composable
fun SelectableNumberCard_Selected_Preview() {
    QuickMathsTheme {
        SelectableNumberCard(
            1,
            displayNumber = { it.toString() },
            selected = true
        )
    }
}

