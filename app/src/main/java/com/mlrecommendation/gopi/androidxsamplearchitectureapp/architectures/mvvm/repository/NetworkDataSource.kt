package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.repository

import androidx.collection.LruCache
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import io.reactivex.Maybe
import io.reactivex.Observable

/**
 * Created by Gopi Krishna on 2020-01-14.
 */
object NetworkDataSource: DataInterface {
    val networkCache = LruCache<Int, String>(2)

    override fun fetchData(id: Int): Maybe<String> {
        if(networkCache.get(id) != null){ return Maybe.just(networkCache.get(id)) }
        return Maybe.fromCallable {
            ThreadUtils.sleep(3000)
            val str = "network" + ThreadUtils.randomNumber()
            DiskDataSource.diskCache.put(id,str+"DD")
            MemoryDataSource.cache.put(id,str+"MM")
            return@fromCallable str
        }
    }
}

interface DataInterface {
    fun fetchData(id: Int): Maybe<String>
}