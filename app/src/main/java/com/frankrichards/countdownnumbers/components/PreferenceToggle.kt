package com.frankrichards.countdownnumbers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme
import com.frankrichards.countdownnumbers.ui.theme.buttonSmall
import com.frankrichards.countdownnumbers.ui.theme.lightText

@Composable
fun PreferenceToggle(
    title: String,
    onButtonPress: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    primaryButtonText: String = "ON",
    secondaryButtonText: String = "OFF",
    isPrimarySelected: Boolean = true
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.lightText,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        CustomButton(
            text = primaryButtonText,
            onClick = { onButtonPress(true) },
            textStyle = MaterialTheme.typography.buttonSmall,
            textPadding = 2.dp,
            color = if(isPrimarySelected){
                MaterialTheme.colorScheme.primary
            }else{
                MaterialTheme.colorScheme.surface
            },
            textColor = if(isPrimarySelected){
                MaterialTheme.colorScheme.onPrimary
            }else{
                MaterialTheme.colorScheme.onSurface
            }
        )

        CustomButton(
            text = secondaryButtonText,
            onClick = { onButtonPress(false) },
            textStyle = MaterialTheme.typography.buttonSmall,
            textPadding = 2.dp,
            color = if(isPrimarySelected){
                MaterialTheme.colorScheme.surface
            }else{
                MaterialTheme.colorScheme.primary
            },
            textColor = if(isPrimarySelected){
                MaterialTheme.colorScheme.onSurface
            }else{
                MaterialTheme.colorScheme.onPrimary
            }
        )


    }
}

@Preview
@Composable
private fun TogglePreference_Preview() {

    var darkMode by remember {
        mutableStateOf(false)
    }

    CountdownNumbersTheme {
        PreferenceToggle(
            title = "Theme",
            onButtonPress = {
                darkMode = !it
            },
            primaryButtonText = "LIGHT",
            secondaryButtonText = "DARK",
            isPrimarySelected = !darkMode
        )
    }
}