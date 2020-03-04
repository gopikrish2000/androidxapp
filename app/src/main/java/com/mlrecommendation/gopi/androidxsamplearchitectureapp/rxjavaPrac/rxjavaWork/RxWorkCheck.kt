package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac.rxjavaWork

import io.reactivex.Observable

class RxWorkCheck {

    fun checkCrash(){
//        Observable.fromCallable<String> { throw InterruptedException(); print("ss");  "a"}.subscribe()
//        Observable.fromCallable<String> { "ss" as Int; print("ss");  "a"}.subscribe()
        Observable.fromCallable<String> { null}.subscribe({}){error -> print(error)}
//        Observable.create<String> { it.onNext(RxJavaWork.returnnull()); }.subscribe()
    }

}

fun main() {
    val obj = RxWorkCheck()
    obj.checkCrash()
}