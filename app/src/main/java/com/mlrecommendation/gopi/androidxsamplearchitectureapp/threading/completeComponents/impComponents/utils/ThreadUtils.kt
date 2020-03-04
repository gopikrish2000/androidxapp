package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CyclicBarrier
import kotlin.random.Random

object ThreadUtils {
    val formater = SimpleDateFormat("h:mm:ss.SSS")
//    val formater = SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM)

    @JvmStatic
    fun threadName() =
        " "+ Thread.currentThread().name + " time " + formater.format(Calendar.getInstance().time)

    @JvmStatic
    fun sleep(time:Long) {
        Thread.sleep(time)
    }

    @JvmStatic
    fun randomNumber() = Random.nextInt(40, 100000)

    @JvmStatic
    fun randomNumberInRange(start: Int, end:Int) = Random.nextInt(start, end)


    @JvmStatic
    fun print(string: String) = System.out.println(string + threadName())

    @JvmStatic
    fun waitObj(obj: Object) {
        try {
            obj.wait(3000)
        } catch (e: InterruptedException) {
        }
    }

    @JvmStatic
    fun startThread(runnable: Runnable) {
        try {
            Thread(runnable).start()
        } catch (e1: InterruptedException){
        } catch (e: Exception) {
        } finally {
        }
    }

    fun awaitBarrier(barrier: CyclicBarrier){
        barrier.await();
    }



}