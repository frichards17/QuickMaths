package com.frankrichards.countdownnumbers.screens

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
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.countdownnumbers.R
import com.frankrichards.countdownnumbers.components.CustomIconButton
import com.frankrichards.countdownnumbers.components.PreferenceSlider
import com.frankrichards.countdownnumbers.components.ScrollingMaths
import com.frankrichards.countdownnumbers.components.TogglePreference
import com.frankrichards.countdownnumbers.data.DataStoreManager
import com.frankrichards.countdownnumbers.model.AppViewModel
import com.frankrichards.countdownnumbers.nav.NavigationItem
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme
import com.frankrichards.countdownnumbers.ui.theme.lightText

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
    val SFXState by viewModel.settings.SFXFlow.collectAsState(initial = true)
    val musicState by viewModel.settings.musicFlow.collectAsState(initial = true)



    Surface(
        color = MaterialTheme.colorScheme.secondary
    ) {

        ScrollingMaths()

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
                verticalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.CenterVertically),
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
                    TogglePreference(
                        title = "Theme",
                        onButtonPress = {
                            viewModel.setDarkMode(!it)
                        },
                        primaryButtonText = "LIGHT",
                        secondaryButtonText = "DARK",
                        isPrimarySelected = !darkModeState,
                        modifier = Modifier.weight(1f)
                    )
                    TogglePreference(
                        title = "SFX",
                        onButtonPress = {
                            viewModel.setSFX(it)
                        },
                        isPrimarySelected = SFXState,
                        modifier = Modifier.weight(1f)
                    )
                    TogglePreference(
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

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun Settings_Preview(){

    CountdownNumbersTheme {
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