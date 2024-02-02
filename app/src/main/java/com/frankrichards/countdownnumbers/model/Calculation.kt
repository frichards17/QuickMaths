package com.frankrichards.countdownnumbers.model

import com.frankrichards.countdownnumbers.util.Utility.toIntArray
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
        var nums = intArrayOf()
        while(nums.count() < 3){
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

        nums += actualSolution
        return nums.toList().shuffled().toIntArray()
    }

    private fun randomNumberNear(n: Int): Int {

        val lower = if(n-3 > 1) {
            n-3
        }else{
            1
        }

        return (lower..n+3).toIntArray().filter { it != n }.random()
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