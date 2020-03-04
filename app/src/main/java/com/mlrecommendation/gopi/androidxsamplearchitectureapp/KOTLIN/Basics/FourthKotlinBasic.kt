package com.mlrecommendation.gopi.androidxsamplearchitectureapp.KOTLIN.Basics

/**
 * Created by Gopi Krishna on 2020-02-19.
 */
/**
 * Created by Gopi Krishna on 2020-02-19.
 */
class FourthKotlinBasic { // Scope can be for classes/innerClasses & extension functions & custom scope which we have written
    var name = "sdsf"

    inner class MyInnerClass {
        var age:Int = 24

        fun doSomething(){
            this@FourthKotlinBasic.name
            this@MyInnerClass.age // same as this.age
            var rrr = 6

            "a".run { // inside extension so (this@run or this) is a new scope.
                var kkk = 3333
                this@MyInnerClass.age
                this@FourthKotlinBasic.name

                this.substring(2)
                this@run.substring(4)
                val meth = customLabelNotScope@ {
                    val a = 22
                }
            }
        }
    }
}

class AA { // implicit label @A
    inner class B { // implicit label @B

        fun Int.foo() { // implicit label @foo ; As extention will have custom scope.
            val a = this@AA // A's this
            val b = this@B // B's this

            val c = this // foo()'s receiver, an Int
            val c1 = this@foo // foo()'s receiver, an Int ; this & this@foo are same.

            val funLit = lambda@ fun String.() {
                val d = this // funLit's receiver String as its extension func & Higher order func. Same as this@lambda
//                this@lambda
            }

            val funLit2 = { s: String ->
                // foo()'s receiver, since enclosing lambda expression
                // doesn't have any receiver
                val d1 = this
            }
            "a".let {
                this // refer to Int as let is not extension so no new scope.
            }
        }
    }
}


