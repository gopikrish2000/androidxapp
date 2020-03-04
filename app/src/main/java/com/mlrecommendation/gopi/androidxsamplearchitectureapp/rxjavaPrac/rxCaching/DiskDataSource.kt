package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac.rxCaching

import io.reactivex.Observable

class DiskDataSource {

    private var data: Data? = null

    fun getData(): Observable<Data> {
        return Observable.create { emitter ->
            if (data != null) {
                emitter.onNext(data!!)
            }
            emitter.onComplete()
        }
    }

    fun saveToDisk(data: Data) {
        this.data = data.copy();
        this.data?.source = "disk";
    }

}