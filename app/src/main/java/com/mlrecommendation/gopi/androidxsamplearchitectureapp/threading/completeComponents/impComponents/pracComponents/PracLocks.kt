package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracComponents

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock

class PracLocks {

    val reentrantLock = ReentrantLock()
    val readWriteLock = ReentrantReadWriteLock()


    fun testReentrantLock() {
//        reentrantLock.lock()
        val tryLock =
            reentrantLock.tryLock()  // reentrantLock.tryLock() non blocking , so multiple threads will go inside if not handled properly
        if(!tryLock){
            return
        }
        println("in critical section Enter thread ${Thread.currentThread().name} time ${System.currentTimeMillis()}")
        Thread.sleep(2000)
        println("in critical section Mid thread ${Thread.currentThread().name} time ${System.currentTimeMillis()}")
        Thread.sleep(1000)
        println("in critical section Exit thread ${Thread.currentThread().name} time ${System.currentTimeMillis()}")
        reentrantLock.unlock()
    }

    fun reentrantWrapper() {
        Observable.range(1, 100).delay(2, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).subscribe { testReentrantLock() }
        Observable.range(1, 100).delay(3, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).subscribe { testReentrantLock() }
        Observable.range(1, 100).delay(1, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).subscribe { testReentrantLock() }
    }

    fun testReadWriteLock() {
        Observable.range(1, 100).delay(2, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).subscribe { readContent() }
        Observable.range(1, 100).delay(3, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).subscribe { readContent() }
        Observable.range(1, 100).delay(1, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).subscribe { readContent() }
        Observable.range(1, 100).delay(2, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).subscribe { readContent() }

        Observable.range(1, 10000).delay(100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.computation()).subscribe { writeContent() }
        Observable.range(1, 10000).delay(100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.computation()).subscribe { writeContent() }
    }

    fun readContent() {
        readWriteLock.readLock().lock()
        println("READ in critical section Enter thread ${Thread.currentThread().name} time ${System.currentTimeMillis()}")
        Thread.sleep(2000)
        println("READ in critical section Mid thread ${Thread.currentThread().name} time ${System.currentTimeMillis()}")
        Thread.sleep(1000)
        println("READ in critical section Exit thread ${Thread.currentThread().name} time ${System.currentTimeMillis()}")
        readWriteLock.readLock().unlock()
    }

    fun writeContent() {
        readWriteLock.writeLock().lock()
        println("WRITE in critical section Enter thread ${Thread.currentThread().name} time ${System.currentTimeMillis()}")
        Thread.sleep(2000)
        println("WRITE in critical section Mid thread ${Thread.currentThread().name} time ${System.currentTimeMillis()}")
        Thread.sleep(1000)
        println("WRITE in critical section Exit thread ${Thread.currentThread().name} time ${System.currentTimeMillis()}")
        readWriteLock.writeLock().unlock()
    }
}

fun main() {
    val obj =
        PracLocks()
    obj.reentrantWrapper()
//    obj.testReadWriteLock()
    Thread.sleep(300000)
}