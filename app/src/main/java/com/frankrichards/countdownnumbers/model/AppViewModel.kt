package com.frankrichards.countdownnumbers.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frankrichards.countdownnumbers.util.Utility
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    var selectedNumbers by mutableStateOf(intArrayOf())
    var targetNum by mutableIntStateOf(0)
    var gameProgress by mutableStateOf(GameProgress.CardSelect)

    // Card Selection
    var selectedIndices by mutableStateOf(intArrayOf())
    var displayedTargetNum by mutableIntStateOf(0)

    // Gameplay
    var num1 by mutableStateOf<CalculationNumber?>(null)
    var num2 by mutableStateOf<CalculationNumber?>(null)
    var operation by mutableStateOf<Operation?>(null)
    var calcError by mutableStateOf(false)
    var currentCalculation by mutableStateOf<Calculation?>(null)
    var calculations by mutableStateOf(arrayOf<Calculation>())
    var calculationNumbers by mutableStateOf(arrayOf<CalculationNumber>())
    var calculationErrMsg by mutableStateOf("")
    var answerCorrect by mutableStateOf(false)

    // Result
    var bestSolution by mutableStateOf(arrayOf<SimpleCalculation>())

    // GAME PROGRESS

    fun goToTargetGen(selectedNumbers: IntArray, targetNum: Int) {

        calculationNumbers = getAvailableNumbers(selectedNumbers)
        this.selectedNumbers = selectedNumbers
        this.targetNum = targetNum
        gameProgress = GameProgress.TargetGen

    }

    private fun getAvailableNumbers(selectedNumbers: IntArray): Array<CalculationNumber> {
        var arr = arrayOf<CalculationNumber>()
        for (i in selectedNumbers.indices) {
            arr += CalculationNumber(
                index = i,
                value = selectedNumbers[i]
            )
        }
        return arr
    }

    fun goToPlay() {
        gameProgress = GameProgress.Countdown
    }

    fun goToResult() {
        gameProgress = GameProgress.Result
    }

    // CONTROLS

    fun numberClick(n: CalculationNumber) {
        if (
            !calculationNumbers.contains(n)
            || !n.isAvailable
        ) return

        if (setNum(n)) {
            calculationErrMsg = ""
            calculationNumbers[n.index].isAvailable = false
        }
    }

    private fun setNum(n: CalculationNumber): Boolean {
        if (num1 == null) {
            num1 = n
            return true
        } else if (num2 == null && operation != null) {
            num2 = n
            return true
        }
        return false
    }

    fun controlButtonClick(op: Operation?) {
        if (op != null) {
            if (num1 != null && num2 == null) {
                operation = op
                calculationErrMsg = ""
            }
        } else {
            if (
                num1 != null
                && operation != null
                && num2 != null
            ) {
                currentCalculation = Calculation(
                    number1 = num1!!,
                    operation = operation!!,
                    number2 = num2!!
                )

                calcError = currentCalculation!!.isError
            }
        }
    }

    fun reset() {
        num2 = null
        operation = null
        num1 = null
        calculations = arrayOf()
        calculationNumbers = getAvailableNumbers(selectedNumbers)
    }

    fun back() {
        calculationErrMsg = ""
        if (num2 != null) {
            calculationNumbers[num2!!.index].isAvailable = true
            num2 = null
        } else if (operation != null) {
            operation = null
        } else if (num1 != null) {
            calculationNumbers[num1!!.index].isAvailable = true
            num1 = null
        } else if (calculations.size > 1) {
            val c = calculations.last()
            calculations = calculations.slice(0..<calculations.lastIndex).toTypedArray()
            num1 = c.number1
            operation = c.operation
            num2 = c.number2
        } else if (calculations.isNotEmpty()) {
            val c = calculations.last()
            calculations = arrayOf()
            num1 = c.number1
            operation = c.operation
            num2 = c.number2
        }
    }

    // DIALOG ANSWER

    fun calculationAnswer(answer: Int) {
        currentCalculation!!.selectedSolution = answer
        calculationNumbers += CalculationNumber(
            index = calculationNumbers.lastIndex + 1,
            value = answer
        )
        calculations += currentCalculation!!
        currentCalculation = null
        num1 = null
        operation = null
        num2 = null

        if (answer == targetNum) {
            checkFinalAnswer()
        }
    }

    private fun checkFinalAnswer() {
        var isIncorrect = false
        for (c in calculations) {
            if (c.solution != c.selectedSolution) {
                isIncorrect = true
                break
            }
        }

        if (isIncorrect) {
            calculationErrMsg = "One of your calculations is wrong!"
        } else {
            answerCorrect = true
            goToResult()
        }
    }

    fun getSolution() {
        viewModelScope.launch {
            bestSolution = Utility.solve(
                189,
                intArrayOf(9, 25, 5, 8, 8, 2)
            )
        }
    }

}

// Remove given number from array
inline fun <reified T> Array<T>.remove(n: T): Array<T> {
    var arr = arrayOf<T>()
    for (i in indices) {
        if (this[i] == n) {
            arr += this.slice(i + 1..<this.size)
            break
        } else {
            arr += this[i]
        }
    }
    return arr
}

enum class GameProgress {
    CardSelect,
    TargetGen,
    Countdown,
    Result
}
