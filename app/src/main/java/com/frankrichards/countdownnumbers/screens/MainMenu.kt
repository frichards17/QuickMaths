package com.frankrichards.countdownnumbers.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frankrichards.countdownnumbers.components.CustomButton
import com.frankrichards.countdownnumbers.model.AppViewModel
import com.frankrichards.countdownnumbers.nav.NavigationItem
import com.frankrichards.countdownnumbers.util.Utility
import kotlinx.coroutines.launch

@Composable
fun MainMenu(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel = viewModel()
){
    val scope = rememberCoroutineScope()

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp)
        ) {
            Title(
                modifier = Modifier.weight(1f),
                title="Title",
                subtitle = "Subtitle"
            )
            CustomButton(
                text = "PLAY",
                onClick = {
                    navigateTo(NavigationItem.Gameplay.route)
                }
            )
            CustomButton(
                text = "SETTINGS",
                onClick = {
                    viewModel.getSolution()
                }
            )

        }
    }
}

@Composable
fun Title(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        if (subtitle != null) {
            Text(
                subtitle,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }

}

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun MainMenu_Preview(){
    MainMenu({})
}