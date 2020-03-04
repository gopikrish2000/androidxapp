package com.mlrecommendation.gopi.androidxsamplearchitectureapp.KOTLIN.Basics

/**
 * Created by Gopi Krishna on 2020-02-18.
 */
/**
 * Created by Gopi Krishna on 2020-02-18.
 */
// Any (Parent Kotlin Class) has three methods: equals(), hashCode() and toString() but no wait, notify
open class Parent public constructor(
    var name: String,
    open val id: Int,
    age: Int
) { // var name => this.name = name ; age without var/val => local variable which is ignored n cannot be used.
    var myAge: Int = 0
    var surname: String = ""
    var ageCopy = age + 5 // primary constructor parameter can be used in variable declarations & in init()

    var otherName:String = "default"
        get() = this.otherName
        set(value) { if(value.length > 3) field = value } // This generates backing field to update the value when len > 3 else ignored.

    constructor(name: String, id: Int, age: Int, surname: String) : this(name, id, age) {
        this.surname = surname
    }

//    constructor() : this("",1,1)  // U can create default Constructor with empty values of primary

    init {
        name = "werwr"
//        age = 22  // compileError treated like val
        myAge = age  // age is local variable of constructor can only be used inside init ...
    }

    open fun doSomething() {
        // name + id + age // compileError age is local variable of constructor can only be used inside init
    }
}

/**
 * If the derived class has a primary constructor, the base class can (and must) be initialized right there, using the parameters of the primary constructor.

If the derived class has no primary constructor, then each secondary constructor has to initialize the base type using the super keyword, or to delegate to another constructor which does that. Note that in this case different secondary constructors can call different constructors of the base type:
 */
class Child(name: String = "defaultName", override var id: Int, age: Int) :
    Parent(name, id, age) { // if u dont declare var/val then u dont have to open/override.

    init { // init block is always called with/ without primary constructor & before the secondary constructor

    }

    final override fun doSomething() {  // override fun is by default open to its subclasses if u want to prevent it use "final"
        super.doSomething()
    }
}

class ChildWithoutPrimary : Parent {

    constructor(name: String, id: Int, age: Int) : super(
        name,
        id,
        age
    ) {  // secondary constructors cannot have val/var for params

    }

    constructor(name: String, id: Int, age: Int, surname: String) : super(name, id, age, surname) {
    }

    fun sample() {
        (name + ageCopy + id).also(::println)
    }
}

/*
* Multiple Inheritance conflict
* */
open class Rectangle {
    open fun draw() { /* ... */
    }
}

interface Polygon {
    fun draw() { /* ... */
    } // interface members are 'open' by default
}

class Square() : Rectangle(), Polygon {
    // The compiler requires draw() to be overridden:
    override fun draw() {
        super<Rectangle>.draw() // call to Rectangle.draw()
        super<Polygon>.draw() // call to Polygon.draw()
    }
}

// Multiple Inheritance conflict End

open class Base(val name: String) {

    open val size: Int =
        name.length.also { println("Initializing size in Base: $it") }

    init {
        println("Initializing Base")
    }
}

class Derived(
    name: String,
    val lastName: String
) : Base(name.capitalize().also { println("Argument for Base: $it") }) {

    init {
        println("Initializing Derived")
    }

    override val size: Int =
        (super.size + lastName.length).also { println("Initializing size in Derived: $it") }
}

/*
*  Output for Derived("Hello", "World") is
*
* Argument for Base: Hello
Initializing size in Base: 5 // variable declaration & init happens in the order of the written code.
Initializing Base
Initializing Derived
Initializing size in Derived: 10
* */

fun main() {
    "abc".also(::println)  // prints abc
    "abc".also { println(it) } // prints abc

    Derived("Hello", "World")

}