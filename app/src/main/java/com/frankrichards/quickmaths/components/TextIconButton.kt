package com.frankrichards.quickmaths.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frankrichards.quickmaths.ui.theme.lightText

@Composable
fun TextIconButton(
    text: String,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.lightText,
    textColor: Color = MaterialTheme.colorScheme.secondary
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = textColor
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .clip(CircleShape)
                .background(color)
                .wrapContentHeight(Alignment.CenterVertically)
        )

    }
}