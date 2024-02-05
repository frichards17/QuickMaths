package com.frankrichards.countdownnumbers.model

import android.util.Log

interface Operation {
    fun calculate(x: Int, y: Int): Int?
    val label: String


    companion object All {
        fun get(): Array<Operation> {
            return arrayOf(Add, Subtract, Multiply, Divide)
        }
    }

    object Add: Operation {
        override fun calculate(x: Int, y: Int) : Int{
            return x + y
        }

        override val label: String
            get() = "+"
    }

    object Subtract: Operation {
        override fun calculate(x: Int, y: Int) : Int?{
            return if(x > y) {
                x - y
            } else null
        }

        override val label: String
            get() = "-"
    }

    object Multiply: Operation {
        override fun calculate(x: Int, y: Int): Int {
            return x * y
        }

        override val label: String
            get() = "×"
    }

    object Divide: Operation {
        override fun calculate(x: Int, y: Int): Int? {

            return if(x.mod(y) == 0){
                x / y
            } else {
                Log.v("Calculate", "Divide Null")
                null
            }

        }

        override val label: String
            get() = "÷"
    }
}