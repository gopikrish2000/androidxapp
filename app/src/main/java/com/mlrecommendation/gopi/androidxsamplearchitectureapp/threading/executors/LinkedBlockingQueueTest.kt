package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.executors

import io.reactivex.Observable
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

object LinkedBlockingQueueTest {

    fun linkedBlockingQueueTest() {
//        val queue = LinkedBlockingQueue<String>(1)
       /* queue.add("1")
        queue.add("2")
        val offer = queue.offer("3")
        queue.offer("4") // Offer, peak are non blocking methods u can pass a custom wait period until when it should wait.
        println("offer is $offer " + queue.peek())*/
//        removalInBlockingQueue()
//        additionForLinkedBlockingQueue()
//        KotlinCheckedExceptions.checkedExceptionTest()
        rxText()
    }

    fun rxText() {
        println("LinkedBlockingQueueTest.rxText")
        Observable.create<Int> { for (i in 1..6) it.onNext(i) }.toList().subscribe { list, t2 -> println("list is $list") }
    }

    private fun removalInBlockingQueue() {
        val queue = LinkedBlockingQueue<String>(1)
        Observable.just(1).delay(5, TimeUnit.SECONDS).map { queue.add("222") }.subscribe()
        println("value of poll ${queue.poll()}")
        println("value of take: " + queue.take())  // queue.take() is a blocking method which will wait for value to be present until then it blocks the thread.
    }

    private fun additionForLinkedBlockingQueue() {
        val queue = LinkedBlockingQueue<String>(1)
        queue.add("ttt")
        Observable.just(1).delay(5, TimeUnit.SECONDS).map { queue.poll() }.subscribe()
        queue.put("www")  // put is blocking method which blocks thread.
        Observable.just(1).delay(7, TimeUnit.SECONDS).subscribe { println("peek val " + queue.peek()) } // prints latest element.
    }
}