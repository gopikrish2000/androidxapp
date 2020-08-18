package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.print
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Gopi Krishna on 31/03/20.
 */
class RxJavaConceptsOther {

    fun testRxZipExceptions(){
        Observable.fromCallable { return@fromCallable 1 }.switchMap {
            Observable.zip<Int, Int, Int>(Observable.fromArray(1, 3).delay(2, TimeUnit.SECONDS),
                Observable.create<Int> {
                    Thread.sleep(2000); throw RuntimeException("Gopi Exception");
//            it.onNext(2)
                },
                BiFunction { t1, t2 -> t1 + t2 })
        }

            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<Int>() {
                override fun onNext(result: Int) {

                }

                override fun onError(error: Throwable) {
                    "gopiPrinting ${error.message}".print()
                }

                override fun onComplete() {}
            })
//            .subscribe ({ it.print() }){ error -> "gopiPrinting ${error.message}".print()}
    }



    fun testKotlinOther(){
        try {
            throw RuntimeException("gopiexception")
        } catch (e: Exception) {
            val illegalArgumentException = IllegalArgumentException("gopiIllegal exc").initCause(e) // initCause makes the crashes interlinked with cause
            throw illegalArgumentException
        }
        /*var string : String? = null
        string ?: kotlin.run {
            //string = "other"
        }

        string.printAll()*/


    }



}

fun main() {
    println("sdf")
    val obj = RxJavaConceptsOther()
   /* Thread.setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler { t, throwable ->
        //Log.e("gopi","DEFAULTEXCEPTIONHANDLER thread ${throwable.localizedMessage} with exception ${throwable.getStackTraceAsString()}")
//       "Cause is ${throwable.javaClass?.name?.contains("java.lang.RuntimeException")}".print()
       "Cause is ${throwable}".print()
        throwable.printStackTrace()
    })*/
//    obj.testRxZipExceptions();
    try {
        obj.testKotlinOther()
    } catch (e: Exception) {
        "original exception is ${e}".print()
        "cause is ${e.cause}".print()
    }

    ThreadUtils.sleep(5000)
}