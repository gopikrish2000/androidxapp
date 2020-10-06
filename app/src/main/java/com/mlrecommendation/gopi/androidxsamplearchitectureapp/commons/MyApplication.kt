package com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons

import android.app.ActivityManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.BuildConfig
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.InterProcessComminucation.MyInterProcessService
import java.io.File


class MyApplication : Application() {
    val TAG = "MyGopiApplication"
    val isStrictModeAllowed = false
    lateinit var myApplicationLifecycleCallbacks:MyApplicationLifecycleCallbacks


    companion object {
        lateinit var inst: MyApplication
        var messenger:Messenger? = null

        fun getInstance(): MyApplication {
            return inst
        }
    }

    override fun onCreate() {
        inst = this
        if(checkIfServiceRemoteProcess(baseContext)){
            println(" Application onCreate called again for new IPC Service Process")
            super.onCreate()
            //ProcessLifecycleOwner.get().lifecycle.addObserver(MyProcessLifecycleObserver().apply { process = "remote" })

            bindService(Intent(this, MyInterProcessService::class.java), object : ServiceConnection{
                override fun onServiceDisconnected(name: ComponentName?) {
                }

                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    messenger = Messenger(service)
                    messenger?.send(Message.obtain().apply { what = 19 })
                }

            }, Context.BIND_AUTO_CREATE)
            myApplicationLifecycleCallbacks = MyApplicationLifecycleCallbacks("child")
            registerActivityLifecycleCallbacks(myApplicationLifecycleCallbacks)
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
//        handleAllExceptions()
        super.onCreate()
        val path:String = Environment.getExternalStorageDirectory().path + File.separator +"ss"
        println("path is $path")

        createNotificationChannel()
        firebaseTokenReceive()

        myApplicationLifecycleCallbacks = MyApplicationLifecycleCallbacks()
//        registerActivityLifecycleCallbacks(myApplicationLifecycleCallbacks)
//        ProcessLifecycleOwner.get().lifecycle.addObserver(MyProcessLifecycleObserver())
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
            Log.e("gopi","DEFAULTEXCEPTIONHANDLER thread ${throwable.localizedMessage} with exception ${throwable.getStackTraceAsString()}")
            throwable.cause?.javaClass.print()
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "GopiSilentChannel"
            val descriptionText = "Gopi Silent Channel"
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