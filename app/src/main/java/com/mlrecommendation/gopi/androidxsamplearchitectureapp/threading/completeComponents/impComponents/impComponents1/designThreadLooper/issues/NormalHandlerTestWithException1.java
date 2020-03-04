package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.designThreadLooper.issues;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class NormalHandlerTestWithException1 {


    private static Handler handler;

    public static void main(String[] args) { // This exception is happening bcoz when ur NOTRUNNING ON DEVICE BUT ON main() we get stub exception.

        /*
        *
        * Exception in thread "Thread-0" java.lang.RuntimeException: Stub!
	at android.os.Looper.prepare(Looper.java:67)
	at com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.designThreadLooper.NormalHandlerTestWithException.lambda$main$0(NormalHandlerTestWithException.java:18)
	at java.lang.Thread.run(Thread.java:745)
Exception in thread "Thread-1" java.lang.RuntimeException: Stub!
	at android.os.Message.<init>(Message.java:41)
	at com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.designThreadLooper.NormalHandlerTestWithException.lambda$main$2(NormalHandlerTestWithException.java:41)
	at java.lang.Thread.run(Thread.java:745)

        * */

        startThread(() -> {
            Looper.prepare();
            handler = new Handler(Looper.myLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    if (msg.what == 1) {
                        System.out.println("msg1 = " + msg);
                    } else if (msg.what == 2) {
                        System.out.println("msg2 = " + msg);
                    } else {
                        System.out.println("msg" + msg.what + " = " + msg);
                    }
                    return false;
                }
            });
            Looper.loop();
        });

        startThread(() -> {
            int i = 0;
            while (i < 500) {
                sleep(500);

                if (i%2 == 0) {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } else {
                    handler.post(() -> print("printing random"));
                }

            }
        });
    }
}
