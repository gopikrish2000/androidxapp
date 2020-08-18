package com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Message

class MyApplicationLifecycleCallbacks(val processName:String = "main") : Application.ActivityLifecycleCallbacks {


    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (processName == "main") {
            showToast("onActivityStarted called for ${activity.javaClass.name}")
        } else {
            MyApplication.messenger?.send(Message.obtain().apply { what = 12 })
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
        if (processName == "main") {
            showToast("onActivityStopped called for ${activity.javaClass.name}")
        } else {
            MyApplication.messenger?.send(Message.obtain().apply { what = 14 })
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

}
