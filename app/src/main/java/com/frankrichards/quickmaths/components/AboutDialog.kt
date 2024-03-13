package com.frankrichards.quickmaths.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.frankrichards.quickmaths.R
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.lightText

@Composable
fun AboutDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.menu_logo

                    ),
                    contentDescription = "Logo",
                    modifier = Modifier//.padding(bottom = 16.dp)
                )
                Text(
                    "Version 1.0.0",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.lightText,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text(
                    "© 2024, Frank Richards",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.lightText,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    "Music by Matthew Atkinson",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.lightText
                )
            }
        }
    }
}

@Preview
@Composable
private fun AboutDialog_Preview() {
    QuickMathsTheme {
        AboutDialog(onDismissRequest = {})

    }
}