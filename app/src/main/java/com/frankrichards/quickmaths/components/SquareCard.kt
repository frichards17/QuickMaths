package com.frankrichards.quickmaths.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SquareCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    color: Color = MaterialTheme.colorScheme.primary,
    content: @Composable() () -> Unit,
) {
    if(onClick != null) {
        Surface(
            modifier =
            modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp)),
            color = color,
            onClick = onClick
        ) {
            content()
        }
    }else{
        Surface(
            modifier =
            modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp)),
            color = color
        ) {
            content()
        }
    }
}

@Composable
fun SquareCard(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    color: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    aspectRatio: Float = 1f
) {
    if(onClick != null) {
        Surface(
            modifier =
            modifier
                .aspectRatio(aspectRatio),
            color = color,
            onClick = onClick,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text,
                    style = textStyle
                )
            }
        }
    }else{
        Surface(
            modifier =
            modifier
                .aspectRatio(aspectRatio)
                .clip(RoundedCornerShape(10.dp)),
            color = color
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text,
                    style = textStyle
                )
            }
        }
    }
}

@Preview
@Composable
fun SquareCard_Preview() {
    
}