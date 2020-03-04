package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import java.util.Random;

public class MyThreadLocalTest {   // Threadlocal useful in case of sharedRunnable to multiple threads.

    static class MyLocalRunnable implements Runnable {
         volatile long a = 20;
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
            @Override
            protected Integer initialValue() {
                return 100;
            }
        };

        @Override
        public void run() {
            a = System.currentTimeMillis();
            threadLocal.set(ThreadUtils.randomNumber());
            System.out.println("a = " + a + " threadlocal value is " + threadLocal.get());
        }
    }

    public static void main(String[] args) {

        MyLocalRunnable runnable = new MyLocalRunnable();
        new Thread(runnable).start(); // when passed same runnable threadlocal gives different values but a prints same value.
        ThreadUtils.sleep(100);
        new Thread(runnable).start();
        new Thread(runnable).start();
        System.out.println("runnable.a = " + runnable.a + " threadlocal  "+ runnable.threadLocal.get());
    }
}
