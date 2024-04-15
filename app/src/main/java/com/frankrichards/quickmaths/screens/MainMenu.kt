package com.frankrichards.quickmaths.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frankrichards.quickmaths.R
import com.frankrichards.quickmaths.components.AboutDialog
import com.frankrichards.quickmaths.components.CustomButton
import com.frankrichards.quickmaths.components.TextIconButton
import com.frankrichards.quickmaths.data.DataStoreManager
import com.frankrichards.quickmaths.model.AppViewModel
import com.frankrichards.quickmaths.nav.NavigationItem
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.lightText
import com.frankrichards.quickmaths.util.SoundManager
import kotlinx.coroutines.delay

@Composable
fun MainMenu(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel = AppViewModel(
        DataStoreManager(LocalContext.current),
        SoundManager(LocalContext.current)
    )
) {
    var showButtons by remember { mutableStateOf(false) }

    val darkModeState by viewModel.settings.darkModeFlow.collectAsState(initial = false)

    var showAboutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(showButtons) {
        delay(1000)
        showButtons = true
    }

    if (showAboutDialog) {
        AboutDialog(
            darkMode = darkModeState,
            onDismissRequest = {
                viewModel.playPop()
                showAboutDialog = false
            }
        )
    }

    Surface(
        color = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(
                    id = if (darkModeState) {
                        R.drawable.menu_logo_dark
                    } else {
                        R.drawable.menu_logo
                    }
                ),
                contentDescription = "Logo",
                modifier = Modifier.weight(1f)
            )
        }

        AnimatedVisibility(
            visible = showButtons,
            enter = slideInVertically(animationSpec = tween(500)) { it }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Bottom),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
                CustomButton(
                    text = "PLAY",
                    onClick = {
                        viewModel.playClick()
                        viewModel.startGame(navigateTo)
                    },
                )
                CustomButton(
                    text = "SETTINGS",
                    onClick = {
                        viewModel.playClick()
                        navigateTo(NavigationItem.Settings.route)
                    },
                    color = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy((-24).dp, alignment = Alignment.End),
            modifier = Modifier.fillMaxHeight()
        ) {
            TextIconButton(
                text = "i",
                onClick = {
                    viewModel.playClick()
                    showAboutDialog = true
                }
            )

            TextIconButton(
                text = "?",
                onClick = {
                    viewModel.playClick()
                    viewModel.helpClicked = true
                    navigateTo(NavigationItem.Tutorial.route)
                }
            )
        }


    }
}

@Preview(widthDp = 480, heightDp = 800, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(widthDp = 480, heightDp = 800)

@Composable
fun MainMenu_Preview() {
    QuickMathsTheme {
        MainMenu({})
    }
}