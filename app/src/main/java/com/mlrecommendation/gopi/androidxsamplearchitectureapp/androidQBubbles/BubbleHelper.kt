package com.mlrecommendation.gopi.androidxsamplearchitectureapp.androidQBubbles

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Person
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import java.util.*

object BubbleHelper {
    var notificationId = 4;

    @TargetApi(Build.VERSION_CODES.O)
    fun createBubbleNotification(context: Context) {
        val randomId = notificationId
        val target = Intent(context, BubbleActivity::class.java)
        target.putExtra("title", "Gopi notification random id $randomId")
        target.putExtra("subtitle", "Subtitle with $randomId")
        val bubbleIntent = PendingIntent.getActivity(context, 0, target, 0 /* PendingIntent.FLAG_NO_CREATE flags */)

// Create bubble metadata
        val bubbleData = Notification.BubbleMetadata.Builder()
            .setDesiredHeight(1600)
            // Note: although you can set the icon is not displayed in Q Beta 2
            .setIcon(Icon.createWithResource(context, R.drawable.computer))
            .setAutoExpandBubble(true)
            .setIntent(bubbleIntent)
            .build()

// Create notification
        val chatBot = Person.Builder()
            .setBot(true)
            .setName("BubbleBot")
            .setImportant(true)
            .build()

        val builder = Notification.Builder(context, "GopiChannel")
            .setContentIntent(bubbleIntent)
            .setContentTitle("Gopi notification random id $randomId")
            .setContentText("Gopi Context with random id $randomId")
            .setSmallIcon(R.drawable.computer)
            .setGroup("GOPI")
//            .setStyle()
            .setGroupSummary(true)
            .setBubbleMetadata(bubbleData)
            .addPerson(chatBot)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
//        notificationManager.notify(notificationId++, builder.build())
        NotificationManagerCompat.from(context).notify(notificationId++, builder.build())
    }
}