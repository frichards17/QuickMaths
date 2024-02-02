package com.frankrichards.countdownnumbers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
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
import com.frankrichards.countdownnumbers.model.Operation

@Composable
fun ControlButton(
    onClick: (Operation?) -> Unit,
    operation: Operation?,
    modifier: Modifier = Modifier,
    label: String? = null,
    aspectRatio: Float = 1f,
    color: Color = MaterialTheme.colorScheme.primary
) {

    val text = operation?.label ?: (label ?: "")

    Surface(
        modifier =
        modifier
            .aspectRatio(aspectRatio)
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(10.dp)),
        color = color,
        onClick = {onClick(operation)}
    ) {
        Row(
            horizontalArrangement =Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun ControlButton_Preview() {
    ControlButton(
        label = "+",
        onClick = { /*TODO*/ },
        operation = Operation.Add,
        modifier = Modifier.width(48.dp)
    )
}