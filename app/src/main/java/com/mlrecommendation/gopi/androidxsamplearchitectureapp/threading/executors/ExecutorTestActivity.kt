package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.executors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.Thread.sleep
import java.util.concurrent.*
/* Process of ThreadPool threads creation.
If the thread pool has not reached the core size, it creates new threads.
If the core size has been reached and there is no idle threads, it queues tasks.
If the core size has been reached, there is no idle threads, and the queue becomes full, it creates new threads (until it reaches the max size).
If the max size has been reached, there is no idle threads, and the queue becomes full, the rejection policy kicks in.
*/
class ExecutorTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_executor_test)
//        basicExecutor()
//        basicExecutor1()
        LinkedBlockingQueueTest.linkedBlockingQueueTest()
//        basicExecutor2()
//        blockingQueueTest()
    }

    private fun blockingQueueTest() {
        val queue = MyBlockingQueue(10)
        Observable.just(1).delay(5,TimeUnit.SECONDS).subscribe { queue.enqueue(10); println("elemented enqued is 10") }
        val elem = queue.dequeue()
        println("dequeue elem is $elem")

        /* Observable.interval(100, TimeUnit.MILLISECONDS).map { queue.enqueue(it); it }.subscribeOn(Schedulers.newThread()).subscribe { println(Thread.currentThread().name + " value is $it") }
         Observable.interval(50, TimeUnit.MILLISECONDS).map { queue.dequeue() }.subscribeOn(Schedulers.newThread()).subscribe { println(Thread.currentThread().name + " value is $it") }
         Observable.interval(60, TimeUnit.MILLISECONDS).map { queue.dequeue() }.subscribeOn(Schedulers.newThread()).subscribe { println(Thread.currentThread().name + " value is $it") }*/
    }

    private fun basicExecutor2() {
        Observable.create<String> { emitter ->
            println("Create1 " + Thread.currentThread().getName())
            emitter.onNext("1")
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
            .flatMap { Observable.fromCallable { it.substring(1); println(" FlatMap1 "+ Thread.currentThread().getName()) } }
            .subscribeOn(Schedulers.computation()).subscribe { println(" subscribe "+ Thread.currentThread().getName()) }

        Observable.create<String> { emitter ->
            println("Create1 " + Thread.currentThread().getName())
            emitter.onNext("1")
            emitter.onComplete()
        }.observeOn(Schedulers.io())
            .flatMap { Observable.fromCallable { it.substring(1); println(" FlatMap1 "+ Thread.currentThread().getName()) } }
            .observeOn(Schedulers.computation()).subscribe { println(" subscribe "+ Thread.currentThread().getName()) }
    }

    private fun basicExecutor1() {
        val threadPoolExecutor = ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS, LinkedBlockingQueue()) // this executes sequentially as after coolPool it fills the queue; here queue is unbounded(infinite size). So it uses only corePool threads 1
        val threadPoolExecutor2 = ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, LinkedBlockingQueue(1)) // throws rejectException as corePool =1 , queue fill = 1 ; maxpool will be filled 2 ( 1 more from core) ; but 5 requests > (2+1) rejectException
        val threadPoolExecutor3 = ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS, LinkedBlockingQueue(1)) // works fine runs parallely.
        threadPoolExecutor.execute { sleep(6000); println(Thread.currentThread().name + " first thread") }
        threadPoolExecutor.execute { sleep(6000); println(Thread.currentThread().name + " sec thread") }
        threadPoolExecutor.execute { sleep(6000); println(Thread.currentThread().name + " third thread") }
        threadPoolExecutor.execute { sleep(6000); println(Thread.currentThread().name + " four thread") }
        threadPoolExecutor.execute { sleep(6000); println(Thread.currentThread().name + " five thread") }
    }

    private fun basicExecutor() {
//        Executors.new
        Executors.newCachedThreadPool()
        Executors.newFixedThreadPool(3)
        val threadPoolExecutor = ThreadPoolExecutor(
            2,
            4,
            5,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),
            ThreadFactory { Thread(it).apply { name = "MyThread" } }, ThreadPoolExecutor.DiscardPolicy())
//        val scheduler = Schedulers.from(threadPoolExecutor)
//        val scheduler = Schedulers.from(Executors.newSingleThreadExecutor(ThreadFactory { Thread(it).apply { name = "SingleThread" }  }))
//        threadPoolExecutor.allowCoreThreadTimeOut()
        val scheduler = Schedulers.single()

        Observable.fromCallable { Thread.sleep(1000); return@fromCallable 1 }.subscribeOn(scheduler).subscribe { println("value is $it ${Thread.currentThread().name}") }

        Observable.fromCallable { Thread.sleep(10); return@fromCallable 1 }.subscribeOn(scheduler).subscribe { println("value11 is $it ${Thread.currentThread().name}") }
    }
}
