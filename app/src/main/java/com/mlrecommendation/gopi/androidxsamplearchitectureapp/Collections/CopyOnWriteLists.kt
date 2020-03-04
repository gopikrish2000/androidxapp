package com.mlrecommendation.gopi.androidxsamplearchitectureapp.Collections

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.print
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Gopi Krishna on 2020-02-17.
 */
class CopyOnWriteLists {
}

fun main() {
    val list = CopyOnWriteArrayList<String>(mutableListOf("1","2","fd","34"))
    Collections.sort(list, kotlin.Comparator { o1, o2 -> o1.compareTo(o2) })

    list.set(2,"4rt4")
    list.print()
}