package com.frankrichards.quickmaths.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frankrichards.quickmaths.components.CustomButton
import com.frankrichards.quickmaths.components.CustomCard
import com.frankrichards.quickmaths.components.NumberCardLayout
import com.frankrichards.quickmaths.model.TutorialViewModel
import com.frankrichards.quickmaths.ui.theme.QuickMathsTheme
import com.frankrichards.quickmaths.util.Utility
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frankrichards.quickmaths.components.CalculationCard
import com.frankrichards.quickmaths.components.CountdownIndicator
import com.frankrichards.quickmaths.components.GameplayComponent
import com.frankrichards.quickmaths.data.DataStoreManager
import com.frankrichards.quickmaths.model.AppViewModel
import com.frankrichards.quickmaths.nav.NavigationItem
import com.frankrichards.quickmaths.util.Animation
import com.frankrichards.quickmaths.util.SoundManager

@Composable
fun Tutorial(
    navigateTo: (route: String) -> Unit,
    appViewModel: AppViewModel,
    viewModel: TutorialViewModel = viewModel(),
) {

    BackHandler {
        appViewModel.playPop()
        navigateTo(NavigationItem.Menu.route)
    }

    val buttonText = if (viewModel.stage == 5 || viewModel.stage == 6) {
        "DONE"
    } else {
        "NEXT"
    }

    LaunchedEffect(key1 = viewModel.stage) {
        if (viewModel.stage == 6) {
            if(appViewModel.helpClicked){
                navigateTo(NavigationItem.Menu.route)
                appViewModel.helpClicked = false
            }else{
                appViewModel.setTutorialViewed(true)
                navigateTo(NavigationItem.Gameplay.route)
            }
        }
    }

    Column {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {

            CustomCard(
                title = "Tutorial",
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                Text(
                    viewModel.text,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)
                )
            }
            CustomButton(
                text = buttonText,
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = {
                    viewModel.next()
                }
            )
        }

        AnimatedVisibility(
            visible = viewModel.stage == 1,
            exit = Animation.exitHorizontal
        ) {

        }

        AnimatedContent(
            targetState = viewModel.stage,
            transitionSpec = {
                Animation.enterHorizontal togetherWith Animation.exitHorizontal
            }, label = "Tutorial Stages"
        ) { stage ->
            when (stage) {
                1 -> {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NonClickable {
                            NumberCardLayout(
                                numbers = Utility.getCardNumbers(),
                                addNumber = {},
                                removeNumber = {},
                                selectedCards = viewModel.selectedNums
                            )
                        }
                    }
                }
                2 -> {
                    CustomCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        NonClickable {
                            GameplayComponent(
                                calculationNumbers = viewModel.calculationNums,
                                numberClick = {},
                                operationClick = {},
                                back = {},
                                reset = {},
                                calculations = viewModel.calculations,
                                currentNum1 = viewModel.num1?.value,
                                currentNum2 = viewModel.num2?.value,
                                currentOp = viewModel.operation
                            )
                        }
                    }
                }

                3 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        NonClickable {
                            CalculationCard(
                                calculation = viewModel.calculation,
                                onAnswer = {}
                            )
                        }
                    }

                }

                4 -> {
                    CustomCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        NonClickable {
                            GameplayComponent(
                                calculationNumbers = viewModel.calculationNums,
                                numberClick = {},
                                operationClick = {},
                                back = {},
                                reset = {},
                                calculations = viewModel.calculations,
                                currentNum1 = viewModel.num1?.value,
                                currentNum2 = viewModel.num2?.value,
                                currentOp = viewModel.operation
                            )
                        }
                    }
                }

                5, 6 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        NonClickable {
                            CountdownIndicator(
                                countdown = viewModel.timeLeft,
                                max = 15,
                                skip = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NonClickable(content: @Composable () -> Unit) {
    Box {
        content()
        Box(modifier = Modifier
            .matchParentSize()
            .pointerInput(Unit) {})
    }
}

@Preview(device = Devices.PIXEL_7)
@Composable
private fun Tutorial_Preview1() {
    val v: TutorialViewModel = viewModel()
    v.stage = 4

    val appV = AppViewModel(
        DataStoreManager(LocalContext.current),
        SoundManager(LocalContext.current)
    )
    QuickMathsTheme {
        Tutorial({}, viewModel = v, appViewModel = appV)
    }
}