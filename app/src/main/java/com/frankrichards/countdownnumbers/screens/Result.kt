package com.frankrichards.countdownnumbers.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frankrichards.countdownnumbers.components.Banner
import com.frankrichards.countdownnumbers.components.CustomButton
import com.frankrichards.countdownnumbers.components.LabelText
import com.frankrichards.countdownnumbers.model.AppViewModel
import com.frankrichards.countdownnumbers.model.GameProgress
import com.frankrichards.countdownnumbers.nav.NavigationItem
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme
import com.frankrichards.countdownnumbers.ui.theme.calculation
import com.frankrichards.countdownnumbers.ui.theme.positive
import com.frankrichards.countdownnumbers.ui.theme.targetNum

@Composable
fun Result(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel
) {
    val msg = if (viewModel.answerCorrect) "Correct!" else "Game Over"
    val targetColor =
        if (viewModel.answerCorrect) MaterialTheme.colorScheme.positive else MaterialTheme.colorScheme.error

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Banner(
                msg,
                modifier = Modifier.padding(top = 32.dp)
            )
            Text(
                viewModel.targetNum.toString(),
                style = MaterialTheme.typography.targetNum,
                textAlign = TextAlign.Center,
                color = targetColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            AnimatedContent(
                targetState = viewModel.gameProgress,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = ""
            ) { progress ->
                if (progress == GameProgress.GeneratingBest) {
                    LabelText(
                        "Generating best solution...",
                        align = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    if (viewModel.answerCorrect) {

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            LabelText("Your correct answer:")

                            for (c in viewModel.calculations) {
                                Text(
                                    "${c.number1.value} ${c.operation.label} ${c.number2.value} = ${c.selectedSolution}",
                                    style = MaterialTheme.typography.calculation,
                                    color = if (c.solution == c.selectedSolution) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.error
                                    }
                                )
                            }
                        }
                    } else {

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            if (viewModel.answerValid) {
                                LabelText("You were ${viewModel.diffFromCorrect} away from a correct answer:")
                            } else {
                                LabelText("Your answer was invalid:")
                            }

                            for (c in viewModel.calculations) {
                                Text(
                                    "${c.number1.value} ${c.operation.label} ${c.number2.value} = ${c.selectedSolution}",
                                    style = MaterialTheme.typography.calculation,
                                    color = if (c.solution == c.selectedSolution) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.error
                                    }
                                )
                            }

                            LabelText("Best solution:")
                            for (c in viewModel.bestSolution) {
                                Text(
                                    "${c.n1} ${c.op.label} ${c.n2} = ${c.ans}",
                                    style = MaterialTheme.typography.calculation
                                )
                            }
                        }


                    }
                }
            }
            CustomButton(
                text = "PLAY AGAIN",
                onClick = {
                    viewModel.resetGame()
                    navigateTo(NavigationItem.Gameplay.route)
                },
            )
            CustomButton(
                text = "MAIN MENU",
                onClick = {
                    viewModel.resetGame()
                    navigateTo(NavigationItem.Menu.route)
                }
            )
        }
    }

}

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun Result_Preview() {

    val v: AppViewModel = viewModel()
    v.answerCorrect = false
//    v.gameProgress = GameProgress.GeneratingBest

    CountdownNumbersTheme {
        Result({}, v)
    }
}

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun Result_Preview2() {

    val v: AppViewModel = viewModel()
    v.answerCorrect = true

    CountdownNumbersTheme {
        Result({}, v)
    }
}