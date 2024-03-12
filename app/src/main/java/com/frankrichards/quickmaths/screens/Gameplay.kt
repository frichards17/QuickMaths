package com.frankrichards.quickmaths.screens

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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.components.CalculationDialog
import com.frankrichards.quickmaths.components.CountdownIndicator
import com.frankrichards.quickmaths.model.AppViewModel
import com.frankrichards.quickmaths.model.GameProgress
import com.frankrichards.quickmaths.components.CustomButton
import com.frankrichards.quickmaths.components.CustomCard
import com.frankrichards.quickmaths.components.GameplayComponent
import com.frankrichards.quickmaths.components.NumberCardLayout
import com.frankrichards.quickmaths.components.QuitDialog
import com.frankrichards.quickmaths.components.TargetNum
import com.frankrichards.quickmaths.data.DataStoreManager
import com.frankrichards.quickmaths.model.Calculation
import com.frankrichards.quickmaths.model.CalculationNumber
import com.frankrichards.quickmaths.model.Operation
import com.frankrichards.quickmaths.nav.NavigationItem
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.targetNum
import com.frankrichards.quickmaths.util.Utility
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Gameplay(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel,
    nums: IntArray = Utility.getCardNumbers()
) {

    val context = LocalContext.current

    BackHandler {
        viewModel.playPop(context)
        viewModel.showQuitDialog = true
    }

    if (viewModel.showQuitDialog) {
        QuitDialog(
            quit = {
                viewModel.playClick(context)
                navigateTo(NavigationItem.Menu.route)
                viewModel.quitGame()
            },
            dismiss = {
                viewModel.playClick(context)
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
            if(viewModel.answerCorrect) {
                viewModel.playCorrect(context)
            }
            navigateTo(NavigationItem.Result.route)
        }
    }

    Surface(
        color = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            CountdownIndicator(
                countdown = viewModel.timeLeft,
                max = viewModel.maxTime,
                isInfinite = viewModel.isInfinite,
                skip = { viewModel.skip() },
                skipEnabled = viewModel.gameProgress == GameProgress.Countdown
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {

                CustomCard(
                    title = "Target:",
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.weight(1f)
                ) {
                    TargetNum(
                        num = "$animatedDigit1$animatedDigit2$animatedDigit3".toInt(),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                CustomCard(
                    title = "Answer:",
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.weight(1f)
                ) {
                    val answer = viewModel.calculations.lastOrNull()?.selectedSolution ?: "000"
                    Text(
                        text = answer.toString(),
                        style = MaterialTheme.typography.targetNum
                    )
                }
            }

            AnimatedContent(
                targetState = viewModel.gameProgress,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "Main Gameplay"
            ) { progress ->

                when (progress) {
                    GameProgress.CardSelect -> {

                        Column {
                            NumberCardLayout(
                                numbers = nums,
                                addNumber = {
                                    viewModel.playClick(context)
                                    if (
                                        !viewModel.selectedIndices.contains(it)
                                        && viewModel.selectedIndices.size < 6
                                    ) {
                                        viewModel.selectedIndices += it
                                    }
                                },
                                removeNumber = { i ->
                                    viewModel.playClick(context)
                                    viewModel.selectedIndices =
                                        viewModel.selectedIndices.filter {
                                            it != i
                                        }.toIntArray()
                                },
                                selectedCards = viewModel.selectedIndices
                            )
                            Surface(
                                color = MaterialTheme.colorScheme.background
                            ) {
                                CustomButton(
                                    text = "PLAY",
                                    onClick = {
                                        viewModel.playClick(context)
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
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }

                    }

                    GameProgress.TargetGen -> {
                        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                            GameplayComponent(
                                modifier = Modifier
                                    .alpha(0.5f)
                                    .fillMaxHeight(0.75f),
                                calculationNumbers = viewModel.calculationNumbers,
                                operationClick = {},
                                numberClick = {},
                                calculations = arrayOf(),
                                back = {},
                                reset = {},
                                errorMsg = null
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier.fillMaxHeight(0.75f)
                        ) {
                            Text(
                                "Starting game...",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp)
                            )
                        }

                    }

                    GameProgress.Countdown -> {
                        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                            GameplayComponent(
                                viewModel.calculationNumbers,
                                numberClick = {
                                    viewModel.playNumberClick(context)
                                    viewModel.numberClick(it)
                                },
                                operationClick = {
                                    viewModel.playOperationClick(context)
                                    viewModel.controlButtonClick(it)
                                },
                                calculations = viewModel.calculations,
                                currentNum1 = viewModel.num1?.value,
                                currentOp = viewModel.operation,
                                currentNum2 = viewModel.num2?.value,
                                calcError = viewModel.calcError,
                                back = {
                                    viewModel.playOperationClick(context)
                                    viewModel.back()
                                },
                                reset = {
                                    viewModel.playOperationClick(context)
                                    viewModel.reset()
                                },
                                errorMsg = viewModel.calculationErrMsg,
                                modifier = Modifier.fillMaxHeight(0.75f)
                            )
                        }

                        if (!viewModel.calcError && viewModel.currentCalculation != null) {
                            CalculationDialog(
                                calculation = viewModel.currentCalculation!!,
                                onAnswer = {
                                    viewModel.playNumberClick(context)
                                    viewModel.calculationAnswer(it)
                                }
                            )
                        }
                    }

                    GameProgress.GameOver, GameProgress.Result -> {
                        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
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
                                errorMsg = viewModel.calculationErrMsg,
                                gameOver = true,
                                modifier = Modifier.fillMaxHeight(0.75f)
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
    val v: AppViewModel = AppViewModel(DataStoreManager(LocalContext.current))
    v.goToTargetGen(
        selectedNumbers = intArrayOf(100, 75, 8, 4, 2, 3),
        targetNum = 312
    )
    v.gameProgress = GameProgress.GameOver
    v.goToPlay()
    val c = Calculation(
        number1 = CalculationNumber(index = 0, value = 100),
        operation = Operation.Add,
        number2 = CalculationNumber(index = 1, value = 75)
    )
    c.selectedSolution = 175
    v.calculations += c
    v.calculationNumbers += CalculationNumber(index = 6, value = 175)
    v.num1 = CalculationNumber(6, 100)
    v.operation = Operation.Multiply
    QuickMathsTheme {
        Gameplay({}, v)
    }
}