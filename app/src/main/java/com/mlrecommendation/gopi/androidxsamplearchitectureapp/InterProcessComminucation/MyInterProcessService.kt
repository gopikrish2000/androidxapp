package com.mlrecommendation.gopi.androidxsamplearchitectureapp.InterProcessComminucation

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.MyApplication
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.showToast

class MyInterProcessService : Service() {


    override fun onBind(intent: Intent): IBinder {
        return Messenger(MyIPCHandler()).binder
    }


    inner class MyIPCHandler: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            when(msg.what){
                12 -> {
                    val childProcessActivity = ChildProcessActivity::class.java.newInstance()
                    MyApplication.getInstance().myApplicationLifecycleCallbacks.onActivityStarted(childProcessActivity)
//                    showToast("message received of 12 ")
                }
                14 -> {
                    val childProcessActivity = ChildProcessActivity::class.java.newInstance()
                    MyApplication.getInstance().myApplicationLifecycleCallbacks.onActivityStopped(childProcessActivity)
                }
                else -> {}
            }
        }
    }

}
