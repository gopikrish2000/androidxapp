package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.designThreadLooper;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.startThread;

public class GopiThreadLooperMainTest {

    static GopiHandler gopiHandlerThread1;



    public static void main(String[] args) {
        GopiThreadLooperMainTest obj = new GopiThreadLooperMainTest();


        startThread(() -> {
            GopiLooper.prepare();
            gopiHandlerThread1 = new GopiHandler(GopiLooper.getMyLooper(), new GopiHandlerCallback() {
                @Override
                public void handleMessage(GopiMessage message) {
                    System.out.println("Thread1 handle " + message);
                }
            });
            GopiLooper.loop();
        });

        startThread(() -> {
            int i = 0;
            while (i < 500) {
                ThreadUtils.sleep(1000);
                GopiMessage message = new GopiMessage();
                message.content = i;
                if (i %2 == 0) {
                    gopiHandlerThread1.sendMessage(message);
                } else {
                    gopiHandlerThread1.post(() -> System.out.println("sending gopiRunnable1 "));
                }
                i++;
            }
        });
    }
}
