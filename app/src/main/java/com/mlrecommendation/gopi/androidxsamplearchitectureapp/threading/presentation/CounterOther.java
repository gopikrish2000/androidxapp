package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.presentation;

public class CounterOther {
    int requestCount = 0;

    public synchronized void firstMethod() {
        requestCount++;
        System.out.println("requestCounter value is "+ requestCount);
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            new CounterOther().firstMethod();
        }).start();

        new Thread(() -> {
            new CounterOther().firstMethod();
        }).start();

        Thread.sleep(5000);
    }
}