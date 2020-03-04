package com.mlrecommendation.gopi.androidxsamplearchitectureapp.services.messenger;

import android.content.*;
import android.os.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.content.ContextCompat;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.BuildConfig;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.CommonUtils;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.MyApplication;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.atomic.AtomicBoolean;

public class MessengerServiceTestActivity extends AppCompatActivity {

    Class serviceClass = MyIPCMessengerService.class;
    Messenger messengerService, inComingMessenger;
    AtomicBoolean isServiceConnected = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_service_test);
        inComingMessenger = new Messenger(new InComingMessegeHandler());

        findViewById(R.id.foregroundBtn).setOnClickListener(v -> {
            bindToMessenger();
        });

        findViewById(R.id.stopForegroundBtn).setOnClickListener(v -> {
            if(!isServiceConnected.get()){
                CommonUtils.Companion.showToast("Already service closed, so not executing again");
                return;
            }
            System.out.println("btn unbinding serviceConnection " + serviceConnection);
            unbindService(serviceConnection);
            isServiceConnected.getAndSet(false);
            messengerService = null; // if we dont do this messenger is not becoming null & its handler still is able to send messages.
        });

        findViewById(R.id.sendMessaggeBtn).setOnClickListener(v -> {
            if(messengerService == null){
                CommonUtils.Companion.showToast("messenger is null");
                return;
            }
            Completable.create(source -> {
                final Message message = Message.obtain();
                message.what = 1;
                message.arg1 = ThreadUtils.randomNumber();
                message.replyTo = inComingMessenger;
                try {
                    messengerService.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).subscribeOn(Schedulers.io()).subscribe();

        });
    }

    static class InComingMessegeHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 111) {
                System.out.println("Incoming Message arg = " + msg.arg1 + " msg = " + msg + " bundleData= "+ msg.getData().getString("producerKey"));
            } else {
                System.out.println(" Rest ALL Incoming Messages other than 111 " + msg);
            }
        }
    }

    private void bindToMessenger() {
//        final Intent intent = new Intent(this, serviceClass);
        final Intent intent = new Intent("a.b.c.MY_INTENT");
        intent.setPackage(BuildConfig.APPLICATION_ID);
        final int val = ThreadUtils.randomNumber();
        intent.putExtra("firstParam", "firstVal "+ val);
        intent.putExtra("secondParam", "secondVal "+ val);
        bindService(intent, serviceConnection,Context.BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messengerService = new Messenger(service);
            System.out.println("Service Connected with " + " className " + name.getClassName());
            isServiceConnected.getAndSet(true);
            final Message message = Message.obtain();
            message.what = 201;
            message.replyTo = inComingMessenger;
            try {
                messengerService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {  // wont be called on every unbind, but only in extreme case like crash of service...
            messengerService = null;
            System.out.println("aa Service DISConnected " + " className " + name.getClassName());
        }

        @Override
        public void onBindingDied(ComponentName name) {
            System.out.println("ForegroundServiceTestActivity.onBindingDied ");
        }

        @Override
        public void onNullBinding(ComponentName name) {
            System.out.println("MessengerServiceTestActivity.onNullBinding");
        }
    };

    @Override
    public void onDestroy() {  // called when stopSelf() or stopService() is called
        super.onDestroy();
        System.out.println("Activity DESTROY called" );
    }
}
