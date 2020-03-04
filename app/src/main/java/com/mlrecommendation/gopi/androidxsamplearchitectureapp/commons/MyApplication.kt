package com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.ActivityManager
import android.os.Environment
import android.os.Process.myPid
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.BuildConfig
import java.io.File


class MyApplication : Application() {
    val TAG = "MyGopiApplication"
    val isStrictModeAllowed = false


    companion object {
        lateinit var inst: MyApplication

        fun getInstance(): MyApplication {
            return inst
        }
    }

    override fun onCreate() {
        if(checkIfServiceRemoteProcess(baseContext)){
            println(" Application onCreate called again for new IPC Service Process")
            super.onCreate()
            return
        }

        if(isStrictModeAllowed) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
        super.onCreate()
        val path:String = Environment.getExternalStorageDirectory().path + File.separator +"ss"
        println("path is $path")
        inst = this
        createNotificationChannel()
        firebaseTokenReceive()
        handleAllExceptions()
    }


    fun checkIfServiceRemoteProcess(context: Context): Boolean {
        val pid = android.os.Process.myPid()
        try {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningAppProcesses = manager.runningAppProcesses
            if (runningAppProcesses != null && runningAppProcesses.size > 0) {
                for (processInfo in runningAppProcesses) {
                    Log.d(TAG, processInfo.processName)
                    if (processInfo.pid == pid && processInfo.processName == BuildConfig.APPLICATION_ID+":remote")
                        return true
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }


    private fun handleAllExceptions() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("gopi","DEFAULTEXCEPTIONHANDLER thread ${thread.name} with exception ${throwable.message}")
        }
    }

    private fun firebaseTokenReceive() {
//        FirebaseInstanceId.getInstance().getToken()
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.string.msg_token_fmt, token)
                Log.d(TAG, msg)
                // Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
    }

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "GopiChannel"
            val descriptionText = "Gopi Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(name, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}