package com.mlrecommendation.gopi.androidxsamplearchitectureapp.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.asynctaskTest.AsyncTestActivity;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

public class MyForegroundIntentService extends IntentService {

    public MyForegroundIntentService(String name) {
        super(name);
    }

    public MyForegroundIntentService() {
        super("DefaultMyForegroundIntentService");
        System.out.println("MyForegroundIntentService.MyForegroundIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("MyForegroundIntentService.onCreate");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        System.out.println("MyForegroundIntentService.onTaskRemoved");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ThreadUtils.print(" MyForegroundIntentService onHandleIntent firstParamValue = " + intent.getStringExtra("firstParam"));
        Intent intentOther = new Intent(this, AsyncTestActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentOther, 0);
        Notification notification = new NotificationCompat.Builder(this,"GopiChannel")
                .setContentTitle("MyTitle")
                .setContentText("MyText")
//                .setGroup("Gopi")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(7,notification);
        ThreadUtils.sleep(4000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ThreadUtils.print("MyForegroundIntentService.onDestroy");
    }
}
