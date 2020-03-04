package com.mlrecommendation.gopi.androidxsamplearchitectureapp.KOTLIN.Basics

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.print
import java.lang.NullPointerException

/**
 * Created by Gopi Krishna on 2020-02-21.
 */
class LetRunApplyOperators {

    fun checkOperators(){
        val listWithNulls: List<String?> = listOf("123", null,"456")
        var type:Any? = null
        for (item in listWithNulls) {
            val letType: Int? = item?.let { println(it); it.toInt() } // prints 123 and ignores null ; can change return type to Int here
            val runType: Int? = item?.run { println(this); this.toInt() } // prints 123 and ignores null; can change return type to Int here
            val applyType: String? = item?.apply { println(this); this.toInt() } // prints 123 and ignores null ; cannot change return type String here
            val alsoType: String? = item?.also { println(it); it.toInt() } // prints 123 and ignores null ; cannot change return type String here

        }
    }
    fun checkNullability():Boolean{
        // If either `person` or `person.department` is null, then assignemnt is skipped => getManager() is not called:
        // person?.department?.head = managersPool.getManager()
        val myStudent = MyStudent()
        myStudent.name = "dss"
        myStudent.name ?: return false  // will return false here as name is null no further code is executed
        myStudent.name ?: throw NullPointerException("")

        val nullableList: List<Int?> = listOf(1, 2, null, 4)
        val intList: List<Int> = nullableList.filterNotNull() // returns list of nonnull values.
        return true
    }
}

class MyStudent(var name:String? = null, var age:Int? = 20)

fun main() {
    var obj = LetRunApplyOperators()
//    obj.apply { checkOperators() }
    obj.checkNullability().print()
}