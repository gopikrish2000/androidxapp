package com.mlrecommendation.gopi.androidxsamplearchitectureapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.asynctaskTest.AsyncTestActivity;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import java.util.concurrent.TimeUnit;

public class MyForeGroundService extends Service { // U can check Running Services in DeveloperOptions -> Running Services.
    private String defaultString = "default";
    volatile int lastStartId = -1;
    GopiBinder gopiBinder = new GopiBinder();

    public MyForeGroundService() { // Called Every time before onCreate => New Service class created after onDestroy.
        System.out.println("MyForeGroundService.MyForeGroundService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, AsyncTestActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(this,"GopiSilentChannel")
                .setContentTitle("MyTitleSilent")
                .setContentText("MyTextSilent")
                .setSmallIcon(R.drawable.ic_notif_img)
                //                .setGroup("Gopi")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(7,notification);
        System.out.println("onCreate of service defaultStr is "+ defaultString);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("values in Service are firstParam is "+ intent.getStringExtra("firstParam") + " startId is "+ startId);
        Intent intentOther = new Intent(this, AsyncTestActivity.class);
        defaultString = "otherrr";

        ThreadUtils.sleep(2000);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentOther, 0);
        Notification notification = new NotificationCompat.Builder(this,"GopiChannel")
                .addAction(new NotificationCompat.Action(R.drawable.ic_notif_img, "GopiAction", PendingIntent.getActivity(getBaseContext(), 44, intentOther,0)))
                .setContentTitle("MyTitleNormal")
                .setContentText("MyTextNormal")
                .setSmallIcon(R.drawable.computer)
                //                .setGroup("Gopi")
//                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .build();

        /*
        *
        *  NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), channelId)
                        .setContentTitle(
                                getApplicationContext()
                                        .getResources()
                                        .getString(R.string.google_assistant_verification_notification_title))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);*/

        startForeground(7,notification);

        gopiBinder.val = ThreadUtils.randomNumber() + "";

        Intent broadcastIntent = new Intent("gopibroadcastaction");
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);

//        ThreadUtils.sleep(1000);

       /* //if (startId == 3) { // .observeOn(AndroidSchedulers.mainThread())
            Observable.just(1).delay(3, TimeUnit.SECONDS).subscribe(((item) -> {
//                ThreadUtils.print("after delay 3 sec stopping");
                stopSelf(startId);
            }));
        //}*/


        /*Observable.just(1).delay(3, TimeUnit.SECONDS).subscribe((item) -> {
            stopForeground(false); // This doesn't stop the service. Will reduce the priority to normal service where doze timelimits apply. With this notification can be swiped off.
            stopForeground(true); // This doesn't stop the service. Will reduce the priority to normal service where doze timelimits apply. Notification is removed with true.
            stopSelf(); // stop the service.
        });*/

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

    public class GopiBinder extends Binder {
        String val = "default";

        public String getVal() {
            return val;
        }
    }
}
