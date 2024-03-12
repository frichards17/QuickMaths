package com.frankrichards.quickmaths.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.components.Banner
import com.frankrichards.quickmaths.components.CustomButton
import com.frankrichards.quickmaths.components.CustomCard
import com.frankrichards.quickmaths.data.DataStoreManager
import com.frankrichards.quickmaths.model.AppViewModel
import com.frankrichards.quickmaths.model.Calculation
import com.frankrichards.quickmaths.model.CalculationNumber
import com.frankrichards.quickmaths.model.Operation
import com.frankrichards.quickmaths.model.SimpleCalculation
import com.frankrichards.quickmaths.nav.NavigationItem
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.ui.theme.calculation
import com.frankrichards.quickmaths.ui.theme.positive
import com.frankrichards.quickmaths.ui.theme.targetNum
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Result(
    navigateTo: (route: String) -> Unit,
    viewModel: AppViewModel,
    playAgain: Boolean = false
) {
    val msg = if (viewModel.answerCorrect) "SOLUTION FOUND!" else "GAME OVER!"

    val listState = rememberLazyListState()

    var playAgain by remember { mutableStateOf(playAgain) }

    val context = LocalContext.current

    LaunchedEffect(playAgain) {
        if (playAgain) {
            launch {
                delay(1500)
                viewModel.resetGame()
                navigateTo(NavigationItem.Gameplay.route)
            }
        }
    }

    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedVisibility(
            visible = !playAgain,
            exit = fadeOut(
                animationSpec = tween(300)
            )
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Banner(
                    msg,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(top = 32.dp)
                )

                val topBottomFade = Brush.verticalGradient(
                    0f to Color.Transparent,
                    0.05f to MaterialTheme.colorScheme.secondary,
                    0.95f to MaterialTheme.colorScheme.secondary,
                    1f to Color.Transparent
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fadingEdge(topBottomFade)
                ) {
                    item {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)
                        ) {

                            CustomCard(
                                title = "Target:",
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = viewModel.targetNum.toString(),
                                    style = MaterialTheme.typography.targetNum,
                                    maxLines = 1,
                                    modifier = Modifier.padding(24.dp)
                                )
                            }

                            CustomCard(
                                title = "Answer:",
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier.weight(1f)
                            ) {
                                val text = if (viewModel.calculationNumbers.count() > 6) {
                                    viewModel.bestAnswer.toString()
                                } else {
                                    "?"
                                }
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.targetNum,
                                    maxLines = 1,
                                    modifier = Modifier.padding(24.dp),
                                    color = if (viewModel.answerCorrect) {
                                        MaterialTheme.colorScheme.positive
                                    } else {
                                        MaterialTheme.colorScheme.error
                                    }
                                )

                            }
                        }
                    }

                    if (viewModel.calculations.isNotEmpty()) {
                        item {

                            CustomCard(
                                title = "Your solution:",
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    viewModel.calculations.forEach { calc ->
                                        Text(
                                            calc.toString(),
                                            style = MaterialTheme.typography.calculation,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (!viewModel.answerCorrect) {
                        item {
                            CustomCard(
                                title = "Best solution:",
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    viewModel.bestSolution.forEach { calc ->
                                        Text(
                                            calc.toString(),
                                            style = MaterialTheme.typography.calculation
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier.height(32.dp)
                        )
                    }
                }


                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Bottom),
                    modifier = Modifier
                        .padding(8.dp)
                    //                    .weight(1f)
                ) {
                    CustomButton(
                        text = "PLAY AGAIN",
                        onClick = {
                            viewModel.playClick(context)
                            playAgain = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    CustomButton(
                        text = "MAIN MENU",
                        onClick = {
                            viewModel.playPop(context)
                            navigateTo(NavigationItem.Menu.route)
                        },
                        color = MaterialTheme.colorScheme.background,
                        textColor = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = playAgain,
            enter = fadeIn(
                animationSpec = tween(300)
            )
        ) {
//            Surface(
//                color = Color.Transparent
//            ) {
////                ScrollingMaths()
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        "Loading game...",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
//            }



        }
    }

}

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun Result_Preview() {

    val c = Calculation(
        number1 = CalculationNumber(index = 0, value = 100),
        operation = Operation.Add,
        number2 = CalculationNumber(index = 1, value = 75)
    )

    val c2 = SimpleCalculation(
        n1 = 1,
        n2 = 2,
        op = Operation.Add,
        ans = 3
    )

    val v: AppViewModel = AppViewModel(DataStoreManager(LocalContext.current))
    v.answerCorrect = false
    v.targetNum = 12055
    v.calculations += c
    v.calculations += c
    v.calculations += c
    v.calculations += c
    v.bestSolution += c2
    v.bestSolution += c2
    v.bestSolution += c2
    v.bestSolution += c2
    v.bestSolution += c2

    QuickMathsTheme {
        Result({}, v)
    }
}

@Preview(widthDp = 480, heightDp = 800)
@Composable
fun Result_Preview2() {

    val v: AppViewModel = AppViewModel(DataStoreManager(LocalContext.current))
    v.answerCorrect = true


    QuickMathsTheme {
        Result({}, v, true)
    }
}