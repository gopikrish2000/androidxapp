package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracComponents

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class PracAtomic {

    fun atomicIntTest() {
        val atomic = AtomicInteger(1)
        val compareAndSet = atomic.compareAndSet(1, 100)
        val incrementAndGet = atomic.incrementAndGet()
        println("value  is $incrementAndGet ;; ${atomic.get()}")

        val atomicBoolean = AtomicBoolean(false)
        atomicBoolean.compareAndSet(false, true)
    }
}

fun main() {
    val pracAtomic = PracAtomic()
    pracAtomic.atomicIntTest()
}