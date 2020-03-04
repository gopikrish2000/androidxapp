package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracComponents

import io.reactivex.Observable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class PracLocksSynchronization {

    val countDownLatch = CountDownLatch(2)
    val cyclicBarrier = CyclicBarrier(3){ println("all threads waited at barrier. ")}  // success runnable
    val semaphore = Semaphore(1)

     fun latestCountdownTest() {
         countDownLatch.countDown()  // takes care of missing signals.
        Observable.just(1).delay(2, TimeUnit.SECONDS).subscribe { println("in latch observable ");countDownLatch.countDown() }
//    Thread.sleep(2000)
        countDownLatch.await()
    }

    fun cyclicBarrierTest() {
        Observable.just(1).delay(2, TimeUnit.SECONDS).subscribe { println("in barrier observable1 ");cyclicBarrier.await() }
        Observable.just(1).delay(4, TimeUnit.SECONDS).subscribe { println("in barrier observable2 ");cyclicBarrier.await() }
        println("waiting for cyclicBarrier in next line")
        cyclicBarrier.await()
        println("PracLocksSynchronization.cyclicBarrierTest end")
//        cyclicBarrier.reset()
    }

    fun semaphoreTest() {

        semaphore.tryAcquire(1)  // try acquire behaving wierdly like not has lock ...
        println(" in critical sectionStart thread ${Thread.currentThread().name} at ${System.currentTimeMillis()}" )
        Thread.sleep(4000)
        println(" in critical sectionMid thread ${Thread.currentThread().name} at ${System.currentTimeMillis()}" )
        Thread.sleep(1000)
        println(" in critical sectionEnd thread ${Thread.currentThread().name} at ${System.currentTimeMillis()}" )
        semaphore.release()
    }

    fun semaphoreWrapper() {
        Observable.just(1).delay(2, TimeUnit.SECONDS).subscribe { println("in semaphore observable1 "); semaphoreTest() }
        Observable.just(1).delay(2, TimeUnit.SECONDS).subscribe { println("in semaphore observable2 "); semaphoreTest() }
        Observable.just(1).delay(2, TimeUnit.SECONDS).subscribe { println("in semaphore observable3 "); semaphoreTest() }
    }

}

fun main() {

    val obj =
        PracLocksSynchronization()
//    obj.latestCountdownTest()
//    obj.cyclicBarrierTest()
    obj.semaphoreWrapper()
    print("completed")
    Thread.sleep(20000)
}

