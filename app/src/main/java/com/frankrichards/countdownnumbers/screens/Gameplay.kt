package com.frankrichards.countdownnumbers.screens

import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frankrichards.countdownnumbers.components.CalculationDialog
import com.frankrichards.countdownnumbers.model.AppViewModel
import com.frankrichards.countdownnumbers.model.GameProgress
import com.frankrichards.countdownnumbers.components.CustomButton
import com.frankrichards.countdownnumbers.components.GameplayComponent
import com.frankrichards.countdownnumbers.components.NumberCardLayout
import com.frankrichards.countdownnumbers.components.QuitDialog
import com.frankrichards.countdownnumbers.components.TargetNum
import com.frankrichards.countdownnumbers.nav.NavigationItem
import com.frankrichards.countdownnumbers.ui.theme.CountdownNumbersTheme
import com.frankrichards.countdownnumbers.util.Utility
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Gameplay(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel,
    nums: IntArray = Utility.getCardNumbers()
) {

    BackHandler {
        viewModel.showQuitDialog = true
    }

    if (viewModel.showQuitDialog) {
        QuitDialog(
            quit = {
                navigateTo(NavigationItem.Menu.route)
                viewModel.quitGame()
            },
            dismiss = {
                viewModel.showQuitDialog = false
            }
        )
    }

    val scope = rememberCoroutineScope()

    val animatedDigit1 by animateIntAsState(
        targetValue = viewModel.displayedTargetNum.getDigit(0),
        animationSpec = tween(
            durationMillis = 2000,
            easing = EaseOut
        ),
        label = ""
    )
    val animatedDigit2 by animateIntAsState(
        targetValue = viewModel.displayedTargetNum.getDigit(1),
        animationSpec = tween(
            durationMillis = 2500,
            easing = EaseOut
        ),
        label = ""
    )
    val animatedDigit3 by animateIntAsState(
        targetValue = viewModel.displayedTargetNum.getDigit(2),
        animationSpec = tween(
            durationMillis = 3000,
            easing = EaseOut
        ),
        label = ""
    )

    val getRandom: () -> Int = {
        (101..999).random()
    }

    LaunchedEffect(viewModel.gameProgress, viewModel.bestSolution) {
        if (viewModel.gameProgress == GameProgress.Result
            && (viewModel.bestSolution.isNotEmpty() ||
                    viewModel.answerCorrect)
        ) {
            navigateTo(NavigationItem.Result.route)
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            TargetNum(
                num = "$animatedDigit1$animatedDigit2$animatedDigit3".toInt(),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            AnimatedContent(
                targetState = viewModel.gameProgress,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "Main Gameplay"
            ) { progress ->
                when (progress) {
                    GameProgress.CardSelect -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            NumberCardLayout(
                                numbers = nums,
                                addNumber = {
                                    if (
                                        !viewModel.selectedIndices.contains(it)
                                        && viewModel.selectedIndices.size < 6
                                    ) {
                                        viewModel.selectedIndices += it
                                    }
                                },
                                removeNumber = { i ->
                                    viewModel.selectedIndices = viewModel.selectedIndices.filter {
                                        it != i
                                    }.toIntArray()
                                },
                                selectedCards = viewModel.selectedIndices
                            )

                            CustomButton(
                                text = "PLAY",
                                onClick = {
                                    val selectedNums = viewModel.selectedIndices.map {
                                        nums[it]
                                    }.toIntArray()
                                    viewModel.displayedTargetNum = getRandom()

                                    viewModel.goToTargetGen(
                                        selectedNums,
                                        viewModel.displayedTargetNum
                                    )

                                    scope.launch {
                                        delay(5000)
                                        viewModel.goToPlay()
                                    }

                                },
                                enabled = viewModel.gameProgress == GameProgress.CardSelect &&
                                        viewModel.selectedIndices.count() == 6,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    GameProgress.TargetGen -> {

                        GameplayComponent(
                            modifier = Modifier.alpha(0.5f),
                            calculationNumbers = viewModel.calculationNumbers,
                            operationClick = {},
                            numberClick = {},
                            calculations = arrayOf(),
                            back = {},
                            reset = {},
                            errorMsg = null
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxHeight(0.75f)
                        ) {
                            Text(
                                "Starting game...",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )
                        }

                    }

                    GameProgress.Countdown -> {
                        Column {
                            Text("Time left: ${viewModel.timeLeft} seconds")
                            GameplayComponent(
                                viewModel.calculationNumbers,
                                numberClick = {
                                    viewModel.numberClick(it)
                                },
                                operationClick = {
                                    viewModel.controlButtonClick(it)
                                },
                                calculations = viewModel.calculations,
                                currentNum1 = viewModel.num1?.value,
                                currentOp = viewModel.operation,
                                currentNum2 = viewModel.num2?.value,
                                calcError = viewModel.calcError,
                                back = { viewModel.back() },
                                reset = { viewModel.reset() },
                                errorMsg = viewModel.calculationErrMsg
                            )
                        }

                        if (!viewModel.calcError && viewModel.currentCalculation != null) {
                            CalculationDialog(
                                calculation = viewModel.currentCalculation!!,
                                onAnswer = {
                                    viewModel.calculationAnswer(it)
                                }
                            )
                        }
                    }

                    GameProgress.GameOver -> {
                        Column {
                            Text("Game Over!")
                            GameplayComponent(
                                viewModel.calculationNumbers,
                                numberClick = {},
                                operationClick = {},
                                calculations = viewModel.calculations,
                                currentNum1 = null,
                                currentOp = null,
                                currentNum2 = null,
                                calcError = false,
                                back = {},
                                reset = {},
                                errorMsg = viewModel.calculationErrMsg
                            )
                        }
                    }

                    else -> {
                        Text(
                            "ELSE",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }


        }
    }
}

fun Int.getDigit(index: Int): Int {
    return String.format("%03d", this)
        .getOrNull(index)
        .toString()
        .toInt()
}

@Preview(widthDp = 480, heightDp = 800)
@Preview(widthDp = 480, heightDp = 800, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Gameplay_Preview() {
    CountdownNumbersTheme {
        Gameplay({}, viewModel())
    }
}