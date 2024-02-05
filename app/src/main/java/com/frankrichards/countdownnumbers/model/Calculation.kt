package com.frankrichards.countdownnumbers.model

import android.util.Log
import com.frankrichards.countdownnumbers.util.Utility.toIntArray
import kotlin.math.roundToInt
import kotlin.random.Random

// For use in gameplay
class Calculation(val number1: CalculationNumber, val operation: Operation, val number2: CalculationNumber){

    val isError: Boolean
    var solution: Int?
    var selectedSolution: Int? = null
    var possibleSolutions: IntArray?

    init {
        val calc = calculate(number1.value, number2.value)

        if(calc == null) {
            isError = true
            solution = null
            possibleSolutions = null
        } else {
            isError = false
            solution = calc
            possibleSolutions = generateSolutions(calc)
        }

    }

    private fun calculate(number1: Int, number2: Int): Int?{
        return operation.calculate(number1, number2)
    }

    private fun generateSolutions(actualSolution: Int): IntArray {
        if(operation == Operation.Divide){
            return generateDivideSolutions(actualSolution)
        }

        var nums = intArrayOf(actualSolution)
        while(nums.count() < 4){
            val changeFirst = Random.nextBoolean()
            val result = if(changeFirst){
                calculate(randomNumberNear(number1.value), number2.value)
            }else{
                calculate(number1.value, randomNumberNear(number2.value))
            }
            if(result != null && !nums.contains(result)){
                nums += result
            }
        }

        return nums.toList().shuffled().toIntArray()
    }

    private fun generateDivideSolutions(actualSolution: Int): IntArray {
        var nums = intArrayOf(actualSolution)
        while(nums.count()  < 4){
            val dividend = randomDividendNear(
                actualDividend = number1.value,
                actualSolution = actualSolution
            )
            val result = calculate(dividend, number2.value)
            Log.v("Calculate:Divide", "Dividend: $dividend, Num2: ${number2.value}, Result: $result")
            if(result != null && !nums.contains(result)){
                nums += result
            }
        }
        return nums
    }

    private fun randomNumberNear(n: Int): Int {
        val variance = (0.25 * n).roundToInt()

        val lower = if(n-variance > 1) {
            n-3
        }else{
            1
        }

        val near = (lower..n+variance).toIntArray().filter { it != n }.random()
        Log.v("Calculate:Variance", "N: $n, Variance: $variance, Near: $near")
        return near
    }

    private fun randomDividendNear(actualDividend: Int, actualSolution: Int): Int{
        val range = if (actualSolution < 10) {
            1..8
        } else {
            val variance = (0.5 * actualSolution).roundToInt()
            -variance..variance
        }

        return actualDividend + (range.random() * number2.value)

    }

    fun getQuestion(): String {
        return "${number1.value} ${operation.label} ${number2.value} = ?"
    }



}

class SimpleCalculation(val n1: Int, val op: Operation, val n2: Int, val ans: Int){
    override fun toString(): String {
        return "$n1 ${op.label} $n2 = $ans"
    }
}