package com.mlrecommendation.gopi.androidxsamplearchitectureapp.notificationsdemo

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.InterProcessComminucation.MainProcessActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.showToast
import kotlinx.android.synthetic.main.activity_notification_demo.*

class NotificationDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_demo)

        showNotifBtn.setOnClickListener {
            showToast("clicked")
            val pendingIntent =
                PendingIntent.getActivity(this, 2, Intent(this, MainProcessActivity::class.java), 0)
            val notification = NotificationCompat.Builder(this,"GopiChannel")
                .setContentTitle("MyTitle")
                .setContentText("MyText")
//                .setOngoing(true)  //set ongoing will make notification sticky ( cannot be cleared )
                .setSmallIcon(R.drawable.ic_notif_img)
                .setContentIntent(pendingIntent)
                .build();
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(44, notification)
        }
    }
}
