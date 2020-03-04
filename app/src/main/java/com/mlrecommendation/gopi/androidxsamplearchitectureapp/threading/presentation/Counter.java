package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.presentation;

public class Counter {
    volatile int requestCount = 0;

    public void setValue(int otherValue) {
        requestCount = otherValue;
    }

    public void incrementCounter() {
        requestCount++;
    }

    public synchronized void firstMethod() throws InterruptedException {
        requestCount++;
        if(requestCount == 3) System.out.println("count is 3");
        Thread.sleep(5000);
        requestCount--;
    }

    public synchronized void secondMethod() throws InterruptedException {
        requestCount++;
        if(requestCount == 3) System.out.println("count is 3");
        Thread.sleep(5000);
        requestCount--;
    }

    public synchronized void thirdMethod()throws InterruptedException {
        requestCount++;
        if(requestCount == 3) System.out.println("count is 3");
        Thread.sleep(5000);
        requestCount--;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter obj = new Counter();
        new Thread(() -> {
            try {
                obj.firstMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                obj.secondMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                obj.thirdMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(18000);
    }
}