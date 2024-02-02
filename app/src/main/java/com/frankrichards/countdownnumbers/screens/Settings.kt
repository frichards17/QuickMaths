package com.frankrichards.countdownnumbers.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Settings(
    navigateTo: (route: String) -> Unit
){
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column{
            Text("Settings")
        }
    }
}

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun Settings_Preview(){
    Settings{}
}