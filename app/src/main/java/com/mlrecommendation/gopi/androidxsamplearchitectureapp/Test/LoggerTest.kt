package com.mlrecommendation.gopi.androidxsamplearchitectureapp.Test

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.print

class LoggerTest {
    var student: Student? = null

    data class Student(var name:String, var id:Int, var buffer: StringBuffer? = StringBuffer("qqq")){
        init {
//            println(" creating new Student with name  $name & id  $id")
        }

        fun execute(student: Student) {
            print("executing this " + student)

        }
    }

     data class Temp(var name:String, var id:Int, var student: Student?){

    }
}

fun main() {
    val obj = LoggerTest()

    val student = LoggerTest.Student("first", 1)
    val temp = LoggerTest.Temp("firstt", 1, student)

    var studentFromObj: LoggerTest.Student? = temp.student
    studentFromObj = null

    temp.print()

//    obj.student?.execute(LoggerTest.Student("sdss", 2))

}