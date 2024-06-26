package com.frankrichards.quickmaths.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.secondaryOnBackground

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    color: Color = MaterialTheme.colorScheme.background,
    padding: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = modifier.shadow(10.dp, RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
        ) {
            if(title != null) {
                Text(
                    title,
                    color = MaterialTheme.colorScheme.secondaryOnBackground,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                content()
            }
        }


    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomCard_Preview() {
    QuickMathsTheme {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomCard(
                title = "Target",
                modifier = Modifier.weight(1f),
                padding = 8.dp
            ) {

            }
            CustomCard(
                title = "Target",
                modifier = Modifier.weight(1f),
                padding = 8.dp
            ) {
                TargetNum(num = 312)
            }
        }
    }

}