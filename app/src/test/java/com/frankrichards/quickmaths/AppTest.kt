package com.frankrichards.quickmaths

import com.frankrichards.quickmaths.model.Operation
import com.frankrichards.quickmaths.model.SimpleCalculation
import com.frankrichards.quickmaths.util.Utility
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppTest {

    @Test
    fun solveTest() = runTest {
        val nums = intArrayOf(
            2, 5, 10, 25, 50, 75
        )
        val target = 5 * 75
        val solved = Utility.solve(target, nums)
        val calc = solved.first()

        assertEquals(1, solved.count())
        assertEquals(5, calc.n1)
        assertEquals(Operation.Multiply, calc.op)
        assertEquals(75, calc.n2)
        assertEquals(5 * 75, calc.ans)
    }
}

