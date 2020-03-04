package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.repository

import androidx.collection.LruCache
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import io.reactivex.Maybe
import io.reactivex.Observable

/**
 * Created by Gopi Krishna on 2020-01-14.
 */
object MemoryDataSource:DataInterface {
    val cache = LruCache<Int, String>(5)


    override fun fetchData(id: Int): Maybe<String> {
        if(cache.get(id) != null){ return Maybe.just(cache.get(id)) }
        return Maybe.empty<String>()
    }
}