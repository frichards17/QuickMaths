package com.frankrichards.countdownnumbers.util

import android.util.Log
import com.frankrichards.countdownnumbers.model.Operation
import com.frankrichards.countdownnumbers.model.SimpleCalculation
import kotlin.math.abs

object Utility {

    private val operations =
        arrayOf(Operation.Add, Operation.Subtract, Operation.Multiply, Operation.Divide)

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

    fun getCombinations(nums: IntArray): Array<SimpleCalculation> {
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

    private fun solveForNum(
        target: Int,
        currentNum: Int,
        otherNums: IntArray,
        currentSolution: Array<SimpleCalculation> = arrayOf(),
        bestSolution: Array<SimpleCalculation> = arrayOf()
    ): Array<SimpleCalculation> {
        Log.v("SolveTest", "Recurred - solution size ${currentSolution.size}")

        var best = bestSolution
        val othersCombined = getCombinations(otherNums)

        // Don't go any deeper if shorter solution found
        if(
            best.isNotEmpty() &&
            best.last().ans == target
            && best.size <= currentSolution.size
            ){
//            Log.v("SolveTestShort", "Current solution shorter - skipping")
            return best
        }

        for (i in otherNums.indices) {
            val num = otherNums[i]
            val others = otherNums.remove(i)
            for (op in operations) {
                op.calculate(currentNum, num)?.let { ans ->
                    val newCalc = SimpleCalculation(currentNum, op, num, ans)
                    // If target found - return
                    if (ans == target) {
                        best = currentSolution + newCalc
                        Log.v("SolveTestShort", "Answer found: ${best.joinToString { it.toString() }}")
                        Log.v("SolveTestShort", "Answer length: ${best.size}")
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
            && best.size <= currentSolution.size + 1
        ){
            return best
        }

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
                        Log.v("SolveTestShort", "Answer found: ${best.joinToString { it.toString() }}")
                        Log.v("SolveTestShort", "Answer length: ${best.size}")
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

    suspend fun solve(target: Int, nums: IntArray): Array<SimpleCalculation> {
        var bestAnswer = 0
        var bestSolution = arrayOf<SimpleCalculation>()

        Log.v("SolveTest", "Starting solve")

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

        return bestSolution
    }

//    fun solve(target: Int, nums: IntArray): Array<SimpleCalculation> {
//        val operations =
//            arrayOf(Operation.Add, Operation.Subtract, Operation.Multiply, Operation.Divide)
//
//        var bestAnswer = 0
//        var bestSolution = arrayOf<SimpleCalculation>()
//
//        Log.v("SolveTest", "Starting solve")
//        for (i in nums.indices) {
//            val others = nums.slice(0..<i) + nums.slice(i+1..nums.lastIndex)
//            var n1 = nums[i]
//            for (op in operations) {
//                var solution = arrayOf<SimpleCalculation>()
//                n1 = nums[i]
//
//                for (n2 in others) {
//                    op.calculate(n1, n2)?.let {ans ->
//                        solution += SimpleCalculation(
//                            n1,
//                            op,
//                            n2,
//                            ans
//                        )
//                        val s = solution.joinToString { "${it.n1} ${it.op.label} ${it.n2} = ${it.ans}, " }
//                        if(ans == target){
//                            return solution
//                        }else if(
//                            abs(target-ans) < abs(target-bestAnswer)
//                        ){
//                            bestAnswer = ans
//                            bestSolution = solution
//                        }
//                        n1 = ans
//
//                    }
//
//                }
//            }
//
//        }
//        return bestSolution
//
//    }

}