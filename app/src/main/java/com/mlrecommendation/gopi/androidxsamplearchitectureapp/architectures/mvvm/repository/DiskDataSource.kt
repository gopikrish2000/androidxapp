package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.repository

import androidx.collection.LruCache
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import io.reactivex.Maybe
import io.reactivex.Observable

/**
 * Created by Gopi Krishna on 2020-01-14.
 */
object DiskDataSource:DataInterface {
    val diskCache = LruCache<Int, String>(30)

    override fun fetchData(id: Int): Maybe<String> {
        if(diskCache.get(id) != null){ return Maybe.just(diskCache.get(id)) }
        return Maybe.empty()
    }
}