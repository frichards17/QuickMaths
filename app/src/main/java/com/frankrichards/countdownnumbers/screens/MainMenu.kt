package com.frankrichards.countdownnumbers.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frankrichards.countdownnumbers.R
import com.frankrichards.countdownnumbers.components.CustomButton
import com.frankrichards.countdownnumbers.components.ScrollingMaths
import com.frankrichards.countdownnumbers.data.DataStoreManager
import com.frankrichards.countdownnumbers.model.AppViewModel
import com.frankrichards.countdownnumbers.nav.NavigationItem
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme
import kotlinx.coroutines.delay

@Composable
fun MainMenu(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel = AppViewModel(DataStoreManager(LocalContext.current))
){
    val scope = rememberCoroutineScope()
    var showButtons by remember { mutableStateOf(false) }

    LaunchedEffect(showButtons){
        delay(1500)
        showButtons = true
    }

    Surface(
        color = MaterialTheme.colorScheme.secondary
    ) {

        ScrollingMaths()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.weight(1f))
        }

        AnimatedVisibility(
            visible = showButtons,
            enter = slideInVertically(animationSpec = tween(500)) { it }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Bottom),
                modifier = Modifier.fillMaxSize().padding(8.dp),
            ){
                CustomButton(
                    text = "PLAY",
                    onClick = {
                        viewModel.resetGame()
                        navigateTo(NavigationItem.Gameplay.route)
                    },
                )
                CustomButton(
                    text = "SETTINGS",
                    onClick = {
                        navigateTo(NavigationItem.Settings.route)
                    },
                    color = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@Preview(widthDp = 480, heightDp = 800, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(widthDp = 480, heightDp = 800)

@Composable
fun MainMenu_Preview(){
    CountdownNumbersTheme {
        MainMenu({})
    }
}