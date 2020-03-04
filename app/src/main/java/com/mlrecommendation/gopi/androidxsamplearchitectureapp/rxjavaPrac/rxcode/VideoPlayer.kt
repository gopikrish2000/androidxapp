package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac.rxcode

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observables.ConnectableObservable
import java.util.concurrent.TimeUnit

class VideoPlayer {

    var position = 0
    var state:PlayerState = PlayerState.NotPrepared
    lateinit var playerObservable: ConnectableObservable<Int>
    var disposable: Disposable? = null

    init {
        playerObservable()
        doProcess()
    }

    fun playerObservable(): ConnectableObservable<Int> {
        return Observable.interval(1, TimeUnit.SECONDS).map { position }.distinctUntilChanged().publish()
    }

    fun doProcess() {
        playerObservable().subscribe{ println(" update ui with $it")}
    }

    fun prepare(){

    }

    fun play() {
        disposable = Observable.interval(1, TimeUnit.SECONDS).subscribe {playerObservable.connect(); position++ }
    }

    fun pause() {
        disposable?.dispose()
    }
}

sealed class PlayerState{
    object NotPrepared: PlayerState()
    object Prepared: PlayerState()
    object Playing: PlayerState()
    object Stopped: PlayerState()
}