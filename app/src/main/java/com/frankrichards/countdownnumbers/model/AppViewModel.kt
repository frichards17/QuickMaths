package com.frankrichards.countdownnumbers.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frankrichards.countdownnumbers.util.Utility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.time.Duration.Companion.seconds

class AppViewModel : ViewModel() {

    private var selectedNumbers by mutableStateOf(intArrayOf())
    var targetNum by mutableIntStateOf(0)
    var gameProgress by mutableStateOf(GameProgress.CardSelect)

    // Card Selection
    var selectedIndices by mutableStateOf(intArrayOf())
    var displayedTargetNum by mutableIntStateOf(0)

    // Gameplay
    private var countdownBlock: suspend CoroutineScope.() -> Unit = {
        withContext(Dispatchers.Default) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            checkAnswer()
            goToResult()
        }
    }

    private var countdownJob = viewModelScope.launch(
        start = CoroutineStart.LAZY,
        block = countdownBlock
    )

    var num1 by mutableStateOf<CalculationNumber?>(null)
    var num2 by mutableStateOf<CalculationNumber?>(null)
    var operation by mutableStateOf<Operation?>(null)
    var calcError by mutableStateOf(false)
    var currentCalculation by mutableStateOf<Calculation?>(null)
    var calculations by mutableStateOf(arrayOf<Calculation>())
    var calculationNumbers by mutableStateOf(arrayOf<CalculationNumber>())
    var calculationErrMsg by mutableStateOf("")
    var timeLeft by mutableStateOf(30)
    var showQuitDialog by mutableStateOf(false)

    // Result
    private var answerValid by mutableStateOf(false)
    var answerCorrect by mutableStateOf(false)
    var bestAnswer by mutableStateOf(0)
    var bestSolution by mutableStateOf(arrayOf<SimpleCalculation>())

    fun resetGame() {
        gameProgress = GameProgress.CardSelect
        targetNum = 0
        displayedTargetNum = 0
        selectedIndices = intArrayOf()
        selectedNumbers = intArrayOf()
        bestSolution = arrayOf()
        timeLeft = 30
        countdownJob = viewModelScope.launch(
            start = CoroutineStart.LAZY,
            block = countdownBlock
        )
        showQuitDialog = false
        reset()
    }

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
        startCountdown()
    }

    private fun startCountdown() {
        countdownJob.start()
    }

    fun quitGame() {
        showQuitDialog = false
        countdownJob.cancel()
    }

    private fun goToResult() {
        countdownJob.cancel()
        currentCalculation = null
        gameProgress = GameProgress.GameOver
        getSolution()
        if (!answerCorrect) {
            var dif = targetNum
            var best = 0
            for (c in calculationNumbers) {
                if (c.isAvailable) {
                    val n = abs(
                        targetNum -
                                c.value
                    )
                    if (n < dif) {
                        dif = n
                        best = c.value
                    }
                }
            }
            bestAnswer = best
        }else{
            bestAnswer = targetNum
        }
        viewModelScope.launch {
            delay(3.seconds)
            Log.v("ResultTest", "Delayed. Game Progress: ${gameProgress.name}")
            gameProgress = GameProgress.Result
        }

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
        calcError = false
        calculations = arrayOf()
        calculationNumbers = getAvailableNumbers(selectedNumbers)
        currentCalculation = null
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
            calculationNumbers = calculationNumbers.slice(0..<calculationNumbers.lastIndex).toTypedArray()
            num1 = c.number1
            operation = c.operation
            num2 = c.number2
            currentCalculation = null
        } else if (calculations.isNotEmpty()) {
            val c = calculations.last()
            calculations = arrayOf()
            calculationNumbers = getAvailableNumbers(selectedNumbers)
            calculationNumbers[c.number1.index].isAvailable = false
            calculationNumbers[c.number2.index].isAvailable = false
            num1 = c.number1
            operation = c.operation
            num2 = c.number2
            currentCalculation = null
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
            checkAnswer()

            if (answerValid) {
                goToResult()
            } else {
                calculationErrMsg = "One of your calculations is wrong!"
            }
        }
    }

    private fun checkAnswer() {
        answerCorrect = false
        calculations.lastOrNull()?.let{
            answerCorrect = it.selectedSolution == targetNum
        }

        answerValid = true
        for (c in calculations) {
            if (c.solution != c.selectedSolution) {
                answerValid = false
                break
            }
        }
    }

    private fun getSolution() {
        viewModelScope.launch {
            bestSolution = Utility.solve(
                targetNum,
                selectedNumbers
            )
        }
    }

}

// Remove given element from array
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
    GameOver,
    Result
}
