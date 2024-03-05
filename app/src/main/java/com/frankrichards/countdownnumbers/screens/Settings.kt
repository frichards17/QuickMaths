package com.frankrichards.countdownnumbers.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.frankrichards.countdownnumbers.data.DataStoreManager
import com.frankrichards.countdownnumbers.model.AppViewModel

@Composable
fun Settings(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel
){
    val darkModeState by viewModel.settings.darkModeFlow.collectAsState(initial = false)

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column{
            Text("Settings")

            Text("DarkMode: $darkModeState")
            Button(onClick = {
                viewModel.setDarkMode(!darkModeState!!)
            }) {
                Text("Toggle")
            }
        }
    }
}

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun Settings_Preview(){

    Settings(
        {},
        AppViewModel(
            DataStoreManager(
                LocalContext.current
            )
        )
    )
}