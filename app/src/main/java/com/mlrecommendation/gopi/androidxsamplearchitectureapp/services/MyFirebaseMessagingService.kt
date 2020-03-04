package com.mlrecommendation.gopi.androidxsamplearchitectureapp.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
        Log.d("MyFirebaseMessagingServ", "datapayload of RemoteNotif is " + remoteMessage.data)
        Log.d("MyFirebaseMessagingServ", "notifpayload of RemoteNotif is title ${remoteMessage.notification?.title}  body ${remoteMessage.notification?.body} icon  ${remoteMessage.notification?.icon} "  )
//        Thread.sleep(1000)

    }

    override fun onMessageSent(message: String) {
        super.onMessageSent(message)
    }

    override fun onNewToken(token: String) {
        Log.d("MyFirebaseMessagingServ", " Token Refreshed new value is $token")
    }
}
