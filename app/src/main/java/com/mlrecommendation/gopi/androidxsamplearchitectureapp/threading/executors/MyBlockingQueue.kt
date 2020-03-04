package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.executors

class MyBlockingQueue(val limit: Int, val queue:MutableList<Any> = mutableListOf<Any>()) {
    @Synchronized  // Synchronized as annotation in Kotlin.
    @Throws(InterruptedException::class)  // This is similar to enqueue throws InterruptedException
    fun enqueue(item: Any) {
        while (this.queue.size == this.limit) wait()

        val isToNotify = queue.isEmpty()
        this.queue.add(item)
        if (isToNotify) notifyAll()  // Do this only when queue size is 0
    }

    @Synchronized @Throws(InterruptedException::class)
    fun dequeue(): Any {
        while (this.queue.size == 0)  wait()

        val isToNotify = this.queue.size == this.limit
        val remove = this.queue.removeAt(0)
        if (isToNotify) notifyAll() // Do this only when queue is Full
        return remove
    }
}
private fun Any.wait() = (this as java.lang.Object).wait()  // Kotlin doesn't support wait n notify by default.
private fun Any.notifyAll() = (this as java.lang.Object).notifyAll()