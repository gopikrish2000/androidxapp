package com.mlrecommendation.gopi.androidxsamplearchitectureapp.KOTLIN.Basics

/**
 * Created by Gopi Krishna on 2020-02-19.
 */
/**
 * Created by Gopi Krishna on 2020-02-19.
 */

class ThirdKotlinBasics {

    var counter = 0 // "BackingField" Note: the initializer assigns the backing field directly
        get() = field + 5 // don't use this.counter else it will be an infinite loop.
        set(value) {
            if (value >= 0) field = value
        }

    private var _table: Map<String, Int>? = null
    val table: Map<String, Int>  // backing property
        get() {
            if (_table == null) {
                _table = HashMap() // Type parameters are inferred
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }

    /*
    * A backing field will be generated for a property if it uses the default implementation of at least one of the accessors, or if a custom accessor references it through the field identifier.
    * */
    val isEmpty: Boolean
        get() = this.counter == 0  // no backing field for this property as "field" variabe is not used

    //const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"  // const used in topLevel or Object classes only

    lateinit var abc: String

    fun doSomething() {
        if (::abc.isInitialized.not()) { // lateinit initialization condition.
            return
        }
    }
}

interface Named {
    val name: String
        get() = "sdf" // interface variables can be either abstract or initialized via get()
    val otherAbstractName:String

    fun abstractMethod() // abstract method must be impleemented

    fun normalMethod(){ // abstract method may or maynot be overriden

    }
}

class Person(override val otherAbstractName: String) :Named {
    override fun abstractMethod() {
    } //Implementors have to implement abstract fields

    override fun normalMethod() {
        super.normalMethod()
    }

    fun doSomething(){
        val (first,second) = Pair<String,String>("ss","ee") //destructor
    }
}

interface A {
    fun foo() { print("A") }
    fun bar()
}

interface B {
    fun foo() { print("B") }
    fun bar() { print("bar") }
}

class C : A {
    override fun bar() { print("bar") }
}

class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super<B>.bar()
    }
}

sealed class Expr
data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()

fun eval(expr: Expr): Double = when(expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    NotANumber -> Double.NaN
    // the `else` clause is not required because we've covered all the cases
}