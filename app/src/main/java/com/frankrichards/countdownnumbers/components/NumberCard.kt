package com.frankrichards.countdownnumbers.components

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.frankrichards.countdownnumbers.model.CalculationNumber
import com.frankrichards.countdownnumbers.ui.theme.quicksand

@Composable
fun NumberCard(
    number: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    displayNumber: (Int) -> String = { it.toString() },
    onClick: ((Int) -> Unit)? = null,
    aspectRatio: Float = 1f
) {
    val digitCount = number.toString().count()
    val fontSize = (28 - 4*(digitCount-2)).sp
    val textStyle = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize
    )
    if(onClick == null) {
        SquareCard(
            text = displayNumber(number),
            color = color,
            modifier = modifier,
            textStyle = textStyle,
            aspectRatio = aspectRatio
        )
    }else{
        SquareCard(
            text = displayNumber(number),
            color = color,
            modifier = modifier,
            onClick = {onClick(number)},
            textStyle = textStyle,
            aspectRatio = aspectRatio
        )
    }
}

@Composable
fun NumberCard(
    number: CalculationNumber,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    displayNumber: (Int) -> String = { it.toString() },
    onClick: ((CalculationNumber) -> Unit)? = null
) {
    val digitCount = number.value.toString().count()
    val fontSize = (28 - 4*(digitCount-2)).sp
    val textStyle = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize
    )
    if (onClick == null) {
        SquareCard(
            text = displayNumber(number.value),
            color = color,
            modifier = modifier,
            textStyle = textStyle
        )
    } else {
        SquareCard(
            text = displayNumber(number.value),
            onClick = {
                Log.v("ClickTest", "CLICKED!")
                onClick(number)
                      },
            color = color,
            modifier = modifier,
            textStyle = textStyle
        )
    }
}

@Preview
@Composable
private fun NumberCard_Preview() {
    NumberCard(
//        number = CalculationNumber(index = 0, value = 200),
        number = 200,
        onClick = { },
        color = MaterialTheme.colorScheme.secondary,
        aspectRatio = 0.5f
    )
}