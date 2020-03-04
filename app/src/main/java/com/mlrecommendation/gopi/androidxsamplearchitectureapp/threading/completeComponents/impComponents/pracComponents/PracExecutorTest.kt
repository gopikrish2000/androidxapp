package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracComponents

import io.reactivex.Observable
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.lang.Thread.sleep as sleep1

class PracExecutorTest {

    fun testExecutor() {
        var count = 1
//        val threadPool = Executors.newFixedThreadPool(2) { Thread(it).apply { name = "gopiThread${count++}" } }
        val threadPool = ThreadPoolExecutor(2,2,10, TimeUnit.SECONDS, LinkedBlockingQueue(), {a -> Thread(a).apply { name = "gopiThread${count++}" } })
        threadPool.allowCoreThreadTimeOut(true)
        val future1 = threadPool.submit<Int> {
            sleep1(4000); println(" executing job1 thread ${Thread.currentThread().name}"); sleep1(4000); return@submit 2
        }
        threadPool.submit { sleep1(4000); println(" executing job2 thread ${Thread.currentThread().name} "); sleep1(4000) }
        val future3 = threadPool.submit { sleep1(4000); println(" executing job3  thread ${Thread.currentThread().name}"); sleep1(4000) }
        threadPool.submit { sleep1(4000); println(" executing job4  thread ${Thread.currentThread().name}"); sleep1(4000) }

        println("first future value is ${future1.get()}")

        Observable.just(1).delay(1, TimeUnit.SECONDS).subscribe { future3.cancel(true) }
//        Observable.just(1).delay(5, TimeUnit.SECONDS).subscribe { threadPool.shutdown() }
    }

    fun testThread() {
        Thread{ while (true){ println("threadmsg"); Thread.sleep(1000)}}.apply { isDaemon = false }.start()  // non deamon threads will block main thread n continue with other.
    }

    fun testScheduledExecutor() {
        var count = 1
        val scheduledThreadPool =
            Executors.newScheduledThreadPool(2) { a -> Thread(a).apply { name = "gopiThread${count++}" ; isDaemon = true} }

        scheduledThreadPool.schedule<String>({ println("printing scheduled task after 3sec "); return@schedule "ww"},3, TimeUnit.SECONDS)
    }
}

fun main() {
    val obj =
        PracExecutorTest()
//    obj.testExecutor()
//    obj.testThread()
    obj.testScheduledExecutor()
    sleep1(4000)

}