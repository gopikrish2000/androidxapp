package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1;

import android.os.Looper;

import java.util.concurrent.LinkedBlockingQueue;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class ThreadCallbackToCreaterThread {

    private LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();

    private void doSomethingInMainThread() {
        print("doSomethingInMainThread Start");
        sleep(5000);
        print("doSomethingInMainThread End");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadCallbackToCreaterThread obj = new ThreadCallbackToCreaterThread();

        startThread(() -> {
            print("In BG thread");
            String abc = "sdfs";
            sleep(2000);
            obj.linkedBlockingQueue.add(() -> {
               obj.doSomethingInMainThread();
            });

        });

        while (true) {
            Runnable runnable = obj.linkedBlockingQueue.take();
            runnable.run();
            break;
        }
    }
}
