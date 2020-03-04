package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals

import java.lang.RuntimeException
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import java.lang.Thread.sleep as sleep1


class MyLock {

    val lockObj = Any()
    var isLocked = false
    var threadName:String? = null
    val reentrantLock = ReentrantLock()
    val condition = reentrantLock.newCondition()

    fun lock(){
        reentrantLock.withLock{
            while (isLocked) condition.await()
            isLocked = true
            threadName = Thread.currentThread().name
//            println("Lock thread $threadName")
            lockSectionCode()
            condition.signalAll()
        }
    }

    fun unlock() {
        reentrantLock.withLock{
            if(threadName != Thread.currentThread().name){
                throw RuntimeException("illegal monitor ")
            }
            println("UNLocked thread $threadName")
            isLocked = false
            condition.signalAll()
        }
    }

    fun lockSectionCode() {  // reentrant lock solves this automatically.
        reentrantLock.withLock {
            println("Lock thread $threadName")
        }
    }
}

class MyLockTest {
    val mylock = MyLock()

    fun criticalSection() {
        Thread{  mylock.lock(); sleep1(2000); println("executing critical ${Thread.currentThread().name}"); mylock.unlock() }.start()
        Thread{  mylock.lock(); sleep1(1000);println("executing critical ${Thread.currentThread().name}"); mylock.unlock() }.start()
        Thread{  mylock.lock(); sleep1(1000); println("executing critical ${Thread.currentThread().name}"); mylock.unlock() }.start()
    }
}

fun main() {
    val obj = MyLockTest()
    obj.criticalSection()
}

/*
fun Any.wait() = (this as java.lang.Object).wait()
fun Any.notify() = (this as java.lang.Object).notify()
fun Any.notifyAll() = (this as java.lang.Object).notifyAll()*/
