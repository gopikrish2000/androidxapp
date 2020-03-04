package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac.rxCaching

import io.reactivex.Observable

class MemoryDataSource {

    private var data: Data? = null

    fun getData(): Observable<Data> {
        return Observable.create { emitter ->
            data?.run { emitter.onNext(this) }
            emitter.onComplete()
        }
    }

    fun cacheInMemory(data: Data) {
        this.data = data.copy();
        this.data?.source = "memory";
    }

}