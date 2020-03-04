package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import androidx.annotation.NonNull;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import java.util.Timer;
import java.util.TimerTask;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.print;

public class BasicThreadConcepts {


    public static void main(String[] args) {
        final Thread thread = new Thread(() -> {
            //try {
                int i=0;
                while (true){
                    print("i =" + i++);
                    //Thread.sleep(2000);
                    /*if (Thread.currentThread().isInterrupted()) {
                        break;
                    }*/
                    throw new Error();
                }
            /*} catch (Throwable e) {
                e.printStackTrace();
            }*/
        });

        thread.start();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                print("crashed thread = " + t.getName() + " with throwable "+ e.getStackTrace()[0].toString());
                System.exit(0);
            }
        });
        /*thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {

            }
        });*/
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                print("inside timertask");
//                throw new RuntimeException();
//                thread.interrupt();
//                timer.cancel();
//                System.out.println("aaa");
            }
        }, 20, 1000);

        throw new RuntimeException();

    }
}
