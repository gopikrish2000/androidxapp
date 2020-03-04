package com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons

import android.os.Handler
import android.os.HandlerThread

object HandlerUtil {
    private val handler: Handler

    init {
        val handlerThread = HandlerThread("HandlerUtil")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }

    fun post(runnable: Runnable) = handler.post(runnable)
    fun postDelayed(delay: Long, runnable: () -> Unit) = handler.postDelayed(runnable, delay)
}