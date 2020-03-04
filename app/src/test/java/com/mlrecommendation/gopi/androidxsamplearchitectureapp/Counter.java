package com.mlrecommendation.gopi.androidxsamplearchitectureapp;

public class Counter {
    volatile int requestCount = 0;

    public synchronized void firstMethod() {
        requestCount++;
        if(requestCount == 3) System.out.println("count is 3");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        requestCount--;
    }

    public synchronized void secondMethod() {
        requestCount++;
        if(requestCount == 3) System.out.println("count is 3");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        requestCount--;
    }

    public synchronized void thirdMethod() {
        requestCount++;
        if(requestCount == 3) System.out.println("count is 3");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        requestCount--;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter obj = new Counter();
        new Thread(() -> { obj.firstMethod();}).start();
        new Thread(() -> { obj.secondMethod();}).start();
        new Thread(() -> { obj.thirdMethod();}).start();

        Thread.sleep(18000);
    }


}