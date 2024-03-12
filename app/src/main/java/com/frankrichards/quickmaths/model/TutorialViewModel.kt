package com.frankrichards.quickmaths.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TutorialViewModel : ViewModel() {
    var stage by mutableIntStateOf(1)
    var text by mutableStateOf("Select your numbers. Top row is large numbers (25, 50, 75, 100), the rest are small (1 to 10).")

    var selectedNums by mutableStateOf(intArrayOf())

    var num50 = CalculationNumber(
    index = 4,
    value = 50
    )
    var num100 = CalculationNumber(
        index = 5,
        value = 100
    )

    var calculationNums by mutableStateOf(
        arrayOf(
            CalculationNumber(
                index = 0,
                value = 10
            ),
            CalculationNumber(
                index = 1,
                value = 9
            ),
            CalculationNumber(
                index = 2,
                value = 2
            ),
            CalculationNumber(
                index = 3,
                value = 25
            ),
            num50,
            num100
        )
    )

    private var countdownBlock: suspend CoroutineScope.() -> Unit = {
        withContext(Dispatchers.Default) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
        }
    }

    private var countdownJob = viewModelScope.launch(
        start = CoroutineStart.LAZY,
        block = countdownBlock
    )

    var timeLeft by mutableIntStateOf(15)


    var calculations by mutableStateOf(arrayOf<Calculation>())

    var num1 by mutableStateOf<CalculationNumber?>(null)
    var num2 by mutableStateOf<CalculationNumber?>(null)
    var operation by mutableStateOf<Operation?>(null)

    var calculation = Calculation(num100, Operation.Add, num50)

    //region TUTORIAL
    fun next() {
        when (stage) {
            1 -> next1()
            2 -> next2()
            3 -> next3()
            4 -> next4()
            5 -> next5()
        }
    }

    private fun next1() {
        viewModelScope.launch {
            selectedNums += 1
            delay(300)
            selectedNums += 2
            delay(300)
            selectedNums += 5
            delay(300)
            selectedNums += 6
            delay(300)
            selectedNums += 7
            delay(300)
            selectedNums += 8
            delay(1000)
            text = "Use the available numbers and operations to try and reach the target number."

            stage = 2
        }
    }

    private fun next2(){
        viewModelScope.launch {
            calculationNums[5].isAvailable = false
            num1 = num100
            delay(300)
            operation = Operation.Add
            delay(300)
            calculationNums[4].isAvailable = false
            num2 = num50
            delay(1000)
            text = "Pick the correct answer for your calculations."

            stage = 3
        }
    }

    private fun next3(){
        calculation.selectedSolution = 150
        num1 = null
        operation = null
        num2 = null
        calculationNums += CalculationNumber(index = 6, value = 150)
        calculations += calculation
        text = "The numbers you create will be added to your available numbers."

        stage = 4
    }

    private fun next4(){
        text = "Skip to the result if you can't do any better."
        countdownJob.start()

        stage = 5
    }

    private fun next5(){
        stage = 6
    }
    //endregion

}