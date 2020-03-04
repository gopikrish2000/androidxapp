package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.repository

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Gopi Krishna on 2020-01-14.
 */
object MvvmRepository {

    fun fetchData(id:Int): Maybe<String> {
        return Maybe.concat(MemoryDataSource.fetchData(id), DiskDataSource.fetchData(id), NetworkDataSource.fetchData(id)).first("ss").toMaybe().subscribeOn(Schedulers.io())
    }
}