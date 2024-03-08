package com.frankrichards.quickmaths.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.components.PreferenceSlider
import com.frankrichards.quickmaths.components.PreferenceToggle
import com.frankrichards.quickmaths.data.DataStoreManager
import com.frankrichards.quickmaths.model.AppViewModel
import com.frankrichards.quickmaths.nav.NavigationItem
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.lightText

class Difficulty(val id: Float, val label: String, val seconds: Int){

    companion object{
        val EASY = Difficulty(1f, "Easy", 60)
        val MEDIUM = Difficulty(2f, "Medium", 45)
        val HARD = Difficulty(3f, "Hard", 30)

        const val S_EASY = "easy"
        const val S_MEDIUM = "medium"
        const val S_HARD = "hard"

        fun getFromString(s: String): Difficulty?{
            when(s.lowercase()){
                S_EASY -> {
                    return EASY
                }
                S_MEDIUM -> {
                    return MEDIUM
                }
                S_HARD -> {
                    return HARD
                }
            }
            return null
        }

        fun getFromID(f: Float): Difficulty?{
            when(f){
                1f -> {
                    return EASY
                }
                2f -> {
                    return MEDIUM
                }
                3f -> {
                    return HARD
                }
            }
            return null
        }
    }

}

@Composable
fun DifficultySlider(
    difficulty: Difficulty,
    sliderValueChanged: (Difficulty) -> Unit
){
    PreferenceSlider(
        title = "Difficulty",
        steps = 1,
        value = difficulty.id,
        valueRange = 1f..3f,
        thumbLabel = difficulty.label,
        thumbSubLabel = "${difficulty.seconds}s",
        sliderValueChanged = { id ->
            Difficulty.getFromID(id)?.let {
                sliderValueChanged(it)
            }
        }
    )
}

@Composable
fun Settings(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel
){
    val difficultyStringState by viewModel.settings.difficultyFlow.collectAsState(initial = Difficulty.S_MEDIUM)
    val darkModeState by viewModel.settings.darkModeFlow.collectAsState(initial = false)
    val sfxState by viewModel.settings.SFXFlow.collectAsState(initial = true)
    val musicState by viewModel.settings.musicFlow.collectAsState(initial = true)



    Surface(
        color = Color.Transparent
    ) {
        Column{
            IconButton(
                onClick = { navigateTo(NavigationItem.Menu.route) },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    "Back Icon",
                    tint = MaterialTheme.colorScheme.lightText,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(32.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .border(
                        color = MaterialTheme.colorScheme.tertiary,
                        width = 4.dp,
                        shape = RoundedCornerShape((32.dp))
                    )
                    .padding(32.dp)
            ){
                Text(
                    "Settings",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.lightText
                )

                DifficultySlider(
                    difficulty = Difficulty.getFromString(difficultyStringState) ?: Difficulty.MEDIUM,
                    sliderValueChanged = {
                        viewModel.setDifficulty(it)
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ){
                    PreferenceToggle(
                        title = "Theme",
                        onButtonPress = {
                            viewModel.setDarkMode(!it)
                        },
                        primaryButtonText = "LIGHT",
                        secondaryButtonText = "DARK",
                        isPrimarySelected = !darkModeState,
                        modifier = Modifier.weight(1f)
                    )
                    PreferenceToggle(
                        title = "SFX",
                        onButtonPress = {
                            viewModel.setSFX(it)
                        },
                        isPrimarySelected = sfxState,
                        modifier = Modifier.weight(1f)
                    )
                    PreferenceToggle(
                        title = "Music",
                        onButtonPress = {
                            viewModel.setMusic(it)
                        },
                        isPrimarySelected = musicState,
                        modifier = Modifier.weight(1f)
                    )
                }

            }
        }
    }
}

@Preview(device = Devices.PIXEL_3A)
@Composable
fun Settings_Preview(){

    QuickMathsTheme {
        Settings(
            {},
            AppViewModel(
                DataStoreManager(
                    LocalContext.current
                )
            )
        )
    }

}