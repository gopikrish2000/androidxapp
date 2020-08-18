package com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyProcessLifecycleObserver : LifecycleObserver {
    var process = "main"

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        showToast("onStart of Process called $process")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        showToast("onStop of Process called $process")
    }

}
