package com.frankrichards.countdownnumbers.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color? = null,
    align: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.labelMedium
) {
    if(color != null) {
        Text(
            text,
            modifier = modifier,
            color = color,
            textAlign = align,
            style = style
        )
    }else{
        Text(
            text,
            modifier = modifier,
            textAlign = align,
            style = style
        )
    }
}