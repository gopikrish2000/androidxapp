package com.mlrecommendation.gopi.androidxsamplearchitectureapp.Test

class LoggerTest {
    var student: Student? = null

    class Student(var name:String, var id:Int){
        init {
//            println(" creating new Student with name  $name & id  $id")
        }

        fun execute(student: Student) {
            print("executing this " + student)

        }
    }
}

fun main() {
    val obj = LoggerTest()

    LoggerTest.Student("first", 1)
    obj.student?.execute(LoggerTest.Student("sdss", 2))

}