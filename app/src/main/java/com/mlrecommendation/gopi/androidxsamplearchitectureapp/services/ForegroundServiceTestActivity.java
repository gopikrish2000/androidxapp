package com.mlrecommendation.gopi.androidxsamplearchitectureapp.services;

import android.content.*;
import android.os.IBinder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;
import kotlin.random.RandomKt;

import java.util.Random;

public class ForegroundServiceTestActivity extends AppCompatActivity {

    Object lock = new Object();
    Class serviceClass = MyForeGroundService.class;
    MyForeGroundService.GopiBinder serviceObj;
    BroadcastReceiver receiver;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service_test);

        findViewById(R.id.foregroundBtn).setOnClickListener(v -> {
            startForegroundService();
        });

        findViewById(R.id.stopForegroundBtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, serviceClass);
//            stopService(intent);
            System.out.println("btn unbinding serviceConnection " + serviceConnection);
            unbindService(serviceConnection);
//            stopService(intent);
        });

//        ThreadUtils.sleep(15000);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (serviceObj != null) {
                    System.out.println("received event from broadcast serviceObj.getVal() = " + serviceObj.getVal());
                } else {
                    System.out.println("received event from broadcast serviceObj NULL = ");
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,new IntentFilter("gopibroadcastaction"));
    }

    @Override
    protected void onStop() {
        super.onStop();

//        startForegroundService();
    }

    private void startForegroundService() {
        final Intent intent = new Intent(this, serviceClass);
        final int val = ThreadUtils.randomNumber();
        intent.putExtra("firstParam", "firstVal "+ val);
        intent.putExtra("secondParam", "secondVal "+ val);
        ContextCompat.startForegroundService(ForegroundServiceTestActivity.this, intent);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                serviceObj = (MyForeGroundService.GopiBinder) service;
                System.out.println("Service Connected with serviceObj.getVal() = " + serviceObj.getVal() + " componentName package " + name.getPackageName() + " className " + name.getClassName());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                serviceObj = null;
                System.out.println("aa Service DISConnected " + " componentName package " + name.getPackageName() + " className " + name.getClassName());
            }

            @Override
            public void onBindingDied(ComponentName name) {
                System.out.println("ForegroundServiceTestActivity.onBindingDied ");
            }
        };
        bindService(intent, serviceConnection,Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {  // called when stopSelf() or stopService() is called
        super.onDestroy();
        System.out.println("Activity DESTROY called" );
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
