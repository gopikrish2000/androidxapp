package com.mlrecommendation.gopi.androidxsamplearchitectureapp.services.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import androidx.annotation.NonNull;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gopi Krishna on 2020-01-09.
 */
public class MyIPCMessengerService extends Service {
    public static final int REGISTER_CLIENT = 201;
    public static final int UN_REGISTER_CLIENT = 204;

    static List<Messenger> clients = new ArrayList<>();
    GopiMessengerHandler target;

    static class GopiMessengerHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                System.out.println("Producer msg.what + \" msg.arg1 \" + msg.arg1 = " + msg.what + " msg.arg1 " + msg.arg1);
            } else if (msg.what == REGISTER_CLIENT) {
                clients.add(msg.replyTo);         // Register a client, add Messenger to list
                return;
            } else if (msg.what == UN_REGISTER_CLIENT) {
                clients.remove(msg.replyTo);
                return;
            } else {
                System.out.println("Producer OTHER what + \" msg.arg1 \" + msg.arg1 = " + msg.what + " msg.arg1 " + msg.arg1);
            }
            final Message message = Message.obtain();
            message.what = 111;
            message.arg1 = ThreadUtils.randomNumber();
//            message.obj = "Gopi Producer sending to consumer";  // message.obj if u use in IPC it will throw unmarshall exception, So only use bundle.
            message.getData().putString("producerKey", "Gopi Producer sending to consumer");
            // msg.replyTo.send(message);  // try to send in main thread only else msg will be recycled & replyTo will be null.
            for (Messenger messenger : clients) { // can send to multiple registered clients
                try {
                    messenger.send(message);
                } catch (RemoteException e) {
                    clients.remove(messenger); // remove lost connection client.
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("MyIPCMessengerService.onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        target = new GopiMessengerHandler();
        return new Messenger(target).getBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        System.out.println("MyIPCMessengerService.onRebind");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        target.removeCallbacksAndMessages(true);
        target = null;
        System.out.println("MyIPCMessengerService.onDestroy");
    }
}
