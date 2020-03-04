package com.mlrecommendation.gopi.androidxsamplearchitectureapp.KOTLIN.Basics

import android.app.Activity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.Test.LoggerTest
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.emptyLine
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.print
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.printAll

/**
 * Created by Gopi Krishna on 2020-02-15.
 */
class FirstKotlinBasic {

    fun first(){
        var int = 25
        var long:Long = 344L
        var double:Double = 35.0 // floating point is by default double
       // var double1:Double = 34 // Compile time error double should have decimal; So convert to 34.0
        var float = 35.2f // declare float by "f"; when no "f" its treated as double

       // double = int   // Compile Error there are no implicit widening conversions for numbers in Kotlin, So float,int cannot be converted to Double

       // int = long   // Compile Error there are no implicit cast ; use long.toInt()
       // long = int   // Compile Error there are no implicit cast ; use int.toLong()

        var char = 'c'
        if(char == 'c'){}

        var infinityDouble = 35.0/0.0  // infinity = 2/0
        infinityDouble.print()

        var nanDouble = 0.0 / 0.0
        nanDouble.print() //nanDouble = 0/0

        nanDouble.isNaN().printAll(nanDouble.isInfinite())

        """
            This is what 
            i'm writing in the code simply  $nanDouble
        """.trimIndent().print()
    }

    fun arrays(){
        val intAry = intArrayOf(1, 2, 3, 66)
        intAry.forEachIndexed{index, value ->  print("($index, $value), ")}.also { emptyLine() } // also for postprocessing after all iterations r done this is called.
        val list = mutableListOf<Int>(1, 2, 3, 66,55)
        intAry.joinToString().print()  // converts array/list to String form with , default

        spreadValues(*intAry)
        spreadValues(*list.toIntArray()) // convert list to array for spreading


        val arrayOf = arrayOf<LoggerTest.Student>(LoggerTest.Student("ss",23), LoggerTest.Student("rrr",66)) // In size u cannot increase size of this without cloning it like using plus method.
        arrayOf[1] = LoggerTest.Student("yy",11)
        arrayOf.forEach { it.name.print() }

        val intCustomizedAry = Array(5) { it * 4 } // Array with size + initialization.

        intCustomizedAry.plus(4).joinToString().print() // plus clones the array n adds this element as last n returns new Array

        val studentArray = arrayOfNulls<LoggerTest.Student>(5) // If u want to work with it like java use arrayOfNulls with size
        studentArray[2] = LoggerTest.Student("cc",49)
        studentArray.clone().joinToString().print()  // U can close a array with clone()
    }

    fun logicalConditions(){
        val student = LoggerTest.Student("ww", 44)

        val whenValue = when {  // u can use when similar to if else & in a better way.
            student.name.equals("yy") -> student.print()
            student is LoggerTest.Student -> student.print()
            student.id in 0..10  -> println("less than 10")  // lly !in , !is r present
            else -> false // here u can return anything n value is ignored
        }

        whenValue.print() // returns kotlin.Unit when ur not returning a value inside when

        val int = 33
        when (int){
            0,1 -> print("ss")
            in 10..20 -> print("wr")
            !is Int -> print(int.toString()+"wer") // smart casts for is ...
            greaterThan(int) -> print("value is 33")  // U can use custom method but that should return same type as int, So it checks int == value..
        }
    }

    fun loops(){
        for(i in 0..3){ // 0 , 3 included
            println(i)
        }

        for (i in 0 until 3){ // 0 included , 3 excluded
            println("i iis $i")
        }

        for(i in 10 downTo 2 step 2) print("i val is $i ") // 10, 8,6,4,2 { included >= 2 }
        emptyLine()
        for(i in 0 until 12 step 2) print("i val1 is $i ") // 10, 8,6,4,2 { 12 exluded. }

        val array = arrayOf("a", "b", "c")
        for ((index, value) in array.withIndex()) {
            println("the element at $index is $value")
        }
        array.filter { it =="b" }.forEach {  }
        array.forEachIndexed { index, value -> println("the element at $index is $value") }.also { emptyLine() }

        for (index in array.indices) { // just with index
            println("the element at $index  val is ${array[index]}")
        }
    }

    fun returnsConcepts(){
        var int:Int? = 334
        int ?: return  // if int is null this will return from function
        int.print()

        val find = intArrayOf(1, 2, 33, 4, 56).find { return@find it > 4 } // This will return only from local scope
        find?.print()
        //val find1 = intArrayOf(1, 2, 33, 4, 56).find { return } // This will return the method, always use @localScope to return from inside.
        "a".print() // This wontt be printed as find1 will return

        loop@ for (i in 1..100) {
            for (j in 1..100) {
                if (i == 20) break@loop
            }
        }
        emptyLine()
        returnsConcepts1()
    }

    fun returnsConcepts1() {

        /*listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {  // anonymous function
            if (value == 3) return  // goes out of the metthod
            print(value.toString() + " ")
        })*/

        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach // just return will go out of the method;;; return@forEach {explicit label } => 1,2,4,5 printing this { acts like continue }
            print(it.toString() + " ")
        }
        println("printing this")

        listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {  // anonymous function
            if (value == 3) return  // local return to the caller of the anonymous fun, i.e. the forEach loop => 1,2,4,5, printing this
            print(value.toString() + " ")
        })
        println("printing this")

        listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {  // anonymous function
            if (value == 3) return  // local return to the caller of the anonymous fun, i.e. the forEach loop => 1,2,4,5, printing this
            print(value.toString() + " ")
        })

        run customLabel@ { // add run lable@ for a custom scope
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@customLabel  // this will act like break ; outtput 1,2
                print(it.toString() + " ")
            }
        }
    }

    private fun greaterThan(value: Int): Int {
        return when{
            value > 30 -> value
            else -> 33
        }
    }

    fun spreadValues(vararg int: Int){ // vararg only take spread from array.
        int.joinToString().print()
    }
}

fun main() {
    val obj = FirstKotlinBasic()
//    obj.first()
//    obj.arrays()
//    obj.logicalConditions()
//    obj.loops()
    obj.returnsConcepts()
}