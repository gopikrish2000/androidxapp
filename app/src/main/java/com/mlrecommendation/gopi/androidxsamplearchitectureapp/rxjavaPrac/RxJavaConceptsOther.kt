package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.print
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

/**
 * Created by Gopi Krishna on 31/03/20.
 */
class RxJavaConceptsOther {

    fun testRxZipExceptions(){
        Observable.zip<Int,Int,Int>(Observable.fromArray(1,3).delay(2,TimeUnit.SECONDS), Observable.create<Int> { Thread.sleep(2000); throw RuntimeException("Gopi Exception"); },
            BiFunction { t1, t2 -> t1 }).subscribeOn(Schedulers.io()).subscribe ({ it.print() }){ error -> error.printStackTrace()}
    }

}

fun main() {
    RxJavaConceptsOther().testRxZipExceptions()
}