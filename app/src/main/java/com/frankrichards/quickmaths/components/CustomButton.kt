package com.frankrichards.quickmaths.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.button

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = MaterialTheme.typography.button,
    textPadding: Dp = 8.dp
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text.uppercase(),
            textAlign = TextAlign.Center,
            style = textStyle,
            modifier = Modifier.padding(vertical = textPadding),
            color = if(enabled) textColor else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun MenuButton_Preview(){
    QuickMathsTheme {
        CustomButton("Click Me", onClick = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MenuButton_Preview2(){
    QuickMathsTheme {
        CustomButton("Click Me", onClick = {}, enabled = false)
    }
}