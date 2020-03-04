package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.executors;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.CommonUtils;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import java.util.concurrent.LinkedBlockingQueue;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep;
import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.startThread;

public class LinkedBlockingAdvancedTest {

    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        startThread(()-> {
            int i = 0;
            while (i++ < 100) {
                queue.add(ThreadUtils.randomNumber() + "");
                System.out.print(i + " ");
                sleep(250);
            }
        });

        startThread(()-> {
            sleep(4000);
            System.out.println("queue.size() = " + queue.size());
            String message = queue.poll();
            while (message != null) {
                System.out.println("message = " + message);
                message = queue.poll();
            }
        });
    }
}
