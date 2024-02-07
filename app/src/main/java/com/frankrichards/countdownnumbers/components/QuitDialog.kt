package com.frankrichards.countdownnumbers.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme

@Composable
fun QuitDialog(
    quit: () -> Unit,
    dismiss: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Quit Game")
        },
        text = {
            Text(text = "Are you sure you want to quit the game and return to the menu?")
        },
        onDismissRequest = dismiss,
        confirmButton = {
            TextButton(
                onClick = quit
            ) {
                Text("Quit")
            }
        },
        dismissButton = {
            TextButton(
                onClick = dismiss
            ) {
                Text("Cancel")
            }
        },
        shape = RoundedCornerShape(10.dp),
        containerColor = MaterialTheme.colorScheme.background
    )
}

@Preview
@Composable
fun QuitDialog_Preview() {
    CountdownNumbersTheme {
        QuitDialog({}, {})
    }
}