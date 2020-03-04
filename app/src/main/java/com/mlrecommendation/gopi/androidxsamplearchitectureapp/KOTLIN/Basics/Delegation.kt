package com.mlrecommendation.gopi.androidxsamplearchitectureapp.KOTLIN.Basics

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.print

/**
 * Created by Gopi Krishna on 2020-02-20.
 */
/**
 * Created by Gopi Krishna on 2020-02-20.
 */

interface BaseInterface {
    fun print()
    fun other()
    var interfaceNumber:Int
}

class BaseInterfaceImpl(val x: Int) : BaseInterface {
    override var interfaceNumber: Int = 22

    override fun print() { print(x) }
    override fun other() { print("other" + x + " interfaceNumber is $interfaceNumber")}
}

class DerivedImpl(var b: BaseInterface) : BaseInterface by b { // u dont have to override the Interface instead it delegates all implementation to b; but u can still override if u want.
    override fun print() { print( (b as BaseInterfaceImpl).x.toString() + "overridden") } // method will be overriden

    override var interfaceNumber: Int = 145 // variable override willnot take effect in this delegation.
}

fun main() {
    val b = BaseInterfaceImpl(10)
    DerivedImpl(b).print() // prints 10overridden
    DerivedImpl(b).other() // prints other10 interfaceNumber is 22 // variable override willnot take effect in this delegation, so its 22 not 145
}
