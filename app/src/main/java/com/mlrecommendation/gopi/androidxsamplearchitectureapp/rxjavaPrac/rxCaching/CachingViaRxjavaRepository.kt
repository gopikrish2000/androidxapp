package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac.rxCaching

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


object CachingViaRxjavaRepository {
    private val memoryDataSource: MemoryDataSource
    private val diskDataSource: DiskDataSource
    private val networkDataSource: NetworkDataSource

    init {
        this.memoryDataSource = MemoryDataSource()
        this.diskDataSource = DiskDataSource()
        this.networkDataSource = NetworkDataSource()
    }

    fun getDataFromMemory(): Observable<Data> {
        return memoryDataSource.getData()
    }

    fun getDataFromDisk(): Observable<Data> {
        return diskDataSource.getData().doOnNext { data -> memoryDataSource.cacheInMemory(data) }
    }

    fun getDataFromNetwork(): Observable<Data> {
        return networkDataSource.data.doOnNext { data ->
            diskDataSource.saveToDisk(data)
            memoryDataSource.cacheInMemory(data)
        }
    }

    fun aggregateDataSources() {  // get from cache in order & take first what ever it gives value
        val memory = getDataFromMemory()
        val disk = getDataFromDisk()
        val network = getDataFromNetwork()

        Observable.concat<Data>(memory, disk, network) // concat operator to maintain the order of observables which is first check in memory, then in disk and finally in network.
            .firstElement() // takes first element what ever it comes.
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .toObservable()
            .subscribe(Consumer {  })
    }
}

data class Data(var source: String)