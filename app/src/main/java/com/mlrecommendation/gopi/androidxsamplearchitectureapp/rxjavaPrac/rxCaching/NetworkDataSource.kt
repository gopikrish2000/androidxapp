package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac.rxCaching

import io.reactivex.Observable

class NetworkDataSource {

    val data: Observable<Data>
        get() = Observable.create { emitter ->
            val data = Data("network")
            emitter.onNext(data)
            emitter.onComplete()
        }

}