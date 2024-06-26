package com.frankrichards.quickmaths.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.R

@Composable
fun CustomIconButton(
    onClick: () -> Unit,
    iconID: Int,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10.dp),
    color: Color = MaterialTheme.colorScheme.primary
) {
    Surface(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        color = color,
        shape = shape,
    ) {
        Icon(
            painterResource(iconID),
            contentDescription = "Icon",
            modifier = Modifier.padding(16.dp)
        )


    }
}

@Preview
@Composable
fun CustomIcon_Preview() {
    CustomIconButton(
        onClick = { /*TODO*/ },
        iconID = R.drawable.backspace
    )
}