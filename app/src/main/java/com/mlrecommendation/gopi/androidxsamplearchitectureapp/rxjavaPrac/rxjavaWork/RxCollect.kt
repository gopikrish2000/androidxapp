package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac.rxjavaWork

import io.reactivex.Observable
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Gopi Krishna on 2020-01-17.
 */
class RxCollect {

    fun doCollect(){
        val hashMap = hashMapOf<String, String>()

        Observable.fromIterable( mutableListOf<String>("1","2","3","4") ).collect<HashMap<String,String>>(
            {hashMapOf()} ,{ a, b ->
                a[b] = b;
            }
        ).toObservable().subscribe({ println(it)}){}

      /*  Observable.fromIterable(mutableListOf<String>("1","2","") )
            .collect<HashMap<String, String>>({ hashMapOf() }, { contactsMap, onlineContactInfo -> contactsMap.put(onlineContactInfo,onlineContactInfo) })
            .toObservable()*/
    }
}

fun main() {
    RxCollect().doCollect()
}