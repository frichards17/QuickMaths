package com.frankrichards.countdownnumbers.util

import android.util.Log
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frankrichards.countdownnumbers.model.Operation
import com.frankrichards.countdownnumbers.model.SimpleCalculation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.abs

object Utility {

    private val operations =
        arrayOf(Operation.Add, Operation.Subtract, Operation.Multiply, Operation.Divide)

    fun getRandomCalculations(n: Int): Array<SimpleCalculation> {
        var calcs = arrayOf<SimpleCalculation>()
        for(i in 1..n){
            val op = operations.random()
            var num1 = 0
            var num2 = 0
            var ans = 0
            when(op){
                Operation.Divide -> {
                    ans = (2..10).random()
                    num2 = (2..100).random()
                    num1 = ans * num2
                }
                Operation.Subtract -> {
                    val x = (0..100).random()
                    val y = (0..100).random()
                    num1 = if(x > y) x else y
                    num2 = if(x < y) x else y
                    ans = num1 - num2
                }
                else -> {
                    num1 = (0..100).random()
                    num2 = (0..100).random()
                    ans = op.calculate(x = num1, y = num2)!!
                }

            }
            calcs += SimpleCalculation(
                num1,
                op,
                num2,
                ans
            )
        }

        return calcs
    }

    fun getRandomOffsets(n: Int, max: Int = 100): Array<Dp>{
        var offsets = arrayOf<Dp>()
        for(i in 1..n){
            offsets += (0..max).random().dp
        }
        return offsets
    }


    fun getCardNumbers(): IntArray {
        val large: IntArray = listOf(25, 50, 75, 100).shuffled().toIntArray()
        val small: IntArray = ((1..10) + (1..10)).shuffled().toIntArray()

        return large + small
    }

    fun IntRange.toIntArray(): IntArray {
        val arr = IntArray(this.count())
        this.forEachIndexed { i, element ->
            arr[i] = element
        }

        return arr
    }

    fun IntArray.remove(index: Int): IntArray {
        return (this.slice(0..<index) + this.slice(index + 1..this.lastIndex)).toIntArray()
    }

    inline fun <reified T> Array<T>.remove(index: Int): Array<T> {
        return (this.slice(0..<index) + this.slice(index + 1..this.lastIndex)).toTypedArray()
    }

    // Get all combinations of nums for non sequential solving
    private fun getCombinations(nums: IntArray): Array<SimpleCalculation> {
        var combos = arrayOf<SimpleCalculation>()
        for (i in nums.indices) {
            val n1 = nums[i]
            for (n2 in nums.remove(i))
                for (op in operations) {
                    op.calculate(n1, n2)?.let {
                        combos += SimpleCalculation(n1, op, n2, it)
                    }
                }
        }

        return combos
    }

    // Get best solution starting with a given number
    private suspend fun solveForNum(
        target: Int,
        currentNum: Int,
        otherNums: IntArray,
        currentSolution: Array<SimpleCalculation> = arrayOf(),
        bestSolution: Array<SimpleCalculation> = arrayOf()
    ): Array<SimpleCalculation> {

        var best = bestSolution
        val othersCombined = getCombinations(otherNums)

        // Don't go any deeper if shorter solution found
        if(
            best.isNotEmpty() &&
            best.last().ans == target
            && best.size <= currentSolution.size + 1
            ){
            return best
        }

        // For each other num, try all sequential solutions (i.e. 1, 2, 3, 4 -> 1 + 2 + 3 + 4)
        for (i in otherNums.indices) {
            val num = otherNums[i]
            val others = otherNums.remove(i)
            for (op in operations) {
                op.calculate(currentNum, num)?.let { ans ->
                    val newCalc = SimpleCalculation(currentNum, op, num, ans)
                    // If target found - return
                    if (ans == target) {
                        best = currentSolution + newCalc
                    }

                    // If target closer - update best answer
                    if (best.isEmpty()) {
                        best = currentSolution + newCalc
                    } else if (
                        abs(target - ans) < abs(target - best.last().ans)
                    ) {
                        best = currentSolution + newCalc
                    }

                    best = solveForNum(
                        target,
                        ans,
                        others,
                        currentSolution + newCalc,
                        best
                    )

                }
            }
        }

        // Don't go any deeper if shorter solution found
        if(
            best.isNotEmpty() &&
            best.last().ans == target
            && best.size <= currentSolution.size + 2
        ){
            return best
        }

        // For each other num, try all non-sequential solutions (i.e. 1, 2, 3, 4 -> (1 x 2) + (3 x 4))
        for(i in othersCombined.indices) {
            val numCalc = othersCombined[i]
            val others = arrayListOf<Int>()
            otherNums.forEach { others.add(it) }
            others.remove(numCalc.n1)
            others.remove(numCalc.n2)

            for (op in operations) {
                op.calculate(currentNum, numCalc.ans)?.let { ans ->
                    val newCalc = SimpleCalculation(currentNum, op, numCalc.ans, ans)
                    // If target found - return
                    if (ans == target) {
                        best = currentSolution + numCalc + newCalc
                    }

                    // If target closer - update best answer
                    if (
                        abs(target - ans) < abs(target - best.last().ans)
                    ) {
                        best = currentSolution + numCalc + newCalc
                    }

                    best = solveForNum(
                        target,
                        ans,
                        others.toIntArray(),
                        currentSolution + numCalc + newCalc,
                        best
                    )

                }
            }
        }

        return best
    }

    // Solve for target with given nums
    suspend fun solve(target: Int, nums: IntArray): Array<SimpleCalculation> = withContext(
        Dispatchers.Default) {
        var bestSolution = arrayOf<SimpleCalculation>()

        for (i in nums.indices) {
            val num = nums[i]
            val others = nums.remove(i)
            bestSolution = solveForNum(
                target,
                num,
                others,
                bestSolution = bestSolution
            )
        }

        bestSolution
    }

}