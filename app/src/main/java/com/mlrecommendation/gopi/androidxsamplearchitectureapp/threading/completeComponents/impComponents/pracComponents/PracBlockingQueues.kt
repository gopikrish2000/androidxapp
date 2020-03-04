package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracComponents

import io.reactivex.Observable
import java.util.concurrent.*

class PracBlockingQueues {
   val countDownLatch = CountDownLatch(1)

    fun blockingQueuesTest() {
        // LinkedBlockingQueue (optional bounding) & ArrayBlockingQueue(bounding) works same way but PriorityQueue is (always unbounded even with capacity provided)
        val blockingQueue = LinkedBlockingQueue<String>(2)/*{a,b ->  a.compareTo(b)}*/
        Observable.just(1).delay(1, TimeUnit.SECONDS).subscribe { blockingQueue.add("22"); }
        Observable.just(1).delay(5, TimeUnit.SECONDS).subscribe { println("elem taken in 5 sec is " + blockingQueue.take()) }
        val elem = blockingQueue.take() // blocking method
        println("taken element is $elem")

        blockingQueue.put("33")
        blockingQueue.put("55")
        blockingQueue.put("44") // only for priorityQueue put is nonBlocking as its unbounded for rest its blocking when full
        println("now queue size is $blockingQueue")

        val list = mutableListOf<String>()
        blockingQueue.drainTo(list)
        println("drained list is $list")
    }

    fun blockingDequeuesTest() {
        // LinkedBlockingDequeue (optional bounding) {Double ended Queue }
        val blockingQueue = LinkedBlockingDeque<String>(2)
        Observable.just(1).delay(1, TimeUnit.SECONDS).subscribe { blockingQueue.add("22"); }
        Observable.just(1).delay(5, TimeUnit.SECONDS).subscribe { println("Dequeue elem taken in 5 sec is " + blockingQueue.take()) }
        val elem = blockingQueue.take() // blocking method
        println("Dequeue taken element is $elem")

        blockingQueue.put("33")
        blockingQueue.put("55")
        blockingQueue.putFirst("44") // only for priorityQueue put is nonBlocking as its unbounded for rest its blocking when full
        println("Dequeue now queue size is $blockingQueue")

        val list = mutableListOf<String>()
        blockingQueue.drainTo(list)
        println("Dequeue drained list is $list")
    }

    fun synchronousQueueTest() {
        val synchronousQueue = SynchronousQueue<String>()  // Queue with no size.
        Observable.just(1).delay(1, TimeUnit.SECONDS).subscribe { println("synchronousQueue elem is " + synchronousQueue.take()); }
        synchronousQueue.put("gopiElem") // blocked until someone removes it.
    }
}

fun main() {
//    print("executing main " + )
    val obj =
        PracBlockingQueues()

//    obj.blockingQueuesTest()
    obj.blockingDequeuesTest()
    obj.synchronousQueueTest()
//    obj.countDownLatch.await()

}