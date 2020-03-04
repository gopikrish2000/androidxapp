package com.mlrecommendation.gopi.androidxsamplearchitectureapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.asynctaskTest.AsyncTestActivity;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

public class MyForeGroundBindService extends Service { // U can check Running Services in DeveloperOptions -> Running Services.
    private String defaultString = "default";
    GopiBinder gopiBinder = new GopiBinder();

    public MyForeGroundBindService() { // Called Every time before onCreate => New Service class created after onDestroy.
        System.out.println("MyForeGroundService.MyForeGroundService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("onCreate of service defaultStr is "+ defaultString);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("values in Service are firstParam is "+ intent.getStringExtra("firstParam") + " startId is "+ startId);
        Intent intentOther = new Intent(this, AsyncTestActivity.class);
        defaultString = "otherrr";

        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentOther, 0);
        Notification notification = new NotificationCompat.Builder(this,"GopiChannel")
                .setContentTitle("MyTitle")
                .setContentText("MyText")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(7,notification);

        gopiBinder.val = ThreadUtils.randomNumber() + "";

        Intent broadcastIntent = new Intent("gopibroadcastaction");
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return gopiBinder;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) { // called every time when application is swiped off from stack.
        super.onTaskRemoved(rootIntent);
        System.out.println("MyForeGroundService.onTaskRemoved"); // If u want foreground service to be killed in this state call stopSelf().
        //stopSelf();
    }

    @Override
    public void onDestroy() {  // called when stopSelf() or stopService() is called
        super.onDestroy();
        System.out.println("DESTROY called" );
    }

    class GopiBinder extends Binder {
        String val = "default";

        public String getVal() {
            return val;
        }
    }
}
