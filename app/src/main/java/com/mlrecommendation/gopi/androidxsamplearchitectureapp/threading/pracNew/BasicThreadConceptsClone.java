package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import androidx.annotation.NonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.print;

public class BasicThreadConceptsClone {


    public static void main(String[] args) {
        final Thread thread = new Thread(() -> {
            throw new RuntimeException(); // throw on bg thread
        });
        thread.start();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));
                print("crashed thread = " + t.getName() + " with throwable "+ stackTrace.toString());
                System.exit(0); // exits the process when crash occurs.
            }
        });

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                print("inside timertask"); // This is printed on new Timer thread. Even after crashes it keeps on printing.
//                timer.cancel();
            }
        }, 20, 1000); // Schedule with initial delay of 20ms & periodically run every 1000ms

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                print("executes one time afterr 1500 ms");
            }
        }, 1500);

        throw new RuntimeException(); // throw on main thread.
    }
}
