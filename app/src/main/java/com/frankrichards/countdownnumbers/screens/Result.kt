package com.frankrichards.countdownnumbers.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frankrichards.countdownnumbers.components.Banner
import com.frankrichards.countdownnumbers.components.LabelText
import com.frankrichards.countdownnumbers.model.AppViewModel
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme

@Composable
fun Result(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel
) {
    val msg = if(viewModel.answerCorrect) "Correct!" else "Game Over"

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            if(viewModel.answerCorrect){
                Banner(
                    msg,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }else{
                Banner(
                    msg,
                    modifier = Modifier.padding(top = 32.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    LabelText("Your solution:")

                    LabelText("Best solution:")
                }


            }
        }
    }

}

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun Result_Preview() {

    val v: AppViewModel = viewModel()
    v.answerCorrect = false

    CountdownNumbersTheme {
        Result({}, v)
    }
}