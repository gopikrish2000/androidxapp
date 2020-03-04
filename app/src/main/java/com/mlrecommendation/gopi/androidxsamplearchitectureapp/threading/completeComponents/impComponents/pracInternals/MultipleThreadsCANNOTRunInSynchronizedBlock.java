package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.threadName;

public class MultipleThreadsCANNOTRunInSynchronizedBlock {  // THIS IS WRONG as multiple threads cannot Run in a single

    final Object lock = new Object();
    volatile boolean state = true;

    public void synchronizedSection() throws InterruptedException {
        synchronized (lock) {
            if(state) {
                System.out.println("Waiting" + threadName());
                lock.wait();
                System.out.println("Waiting DONE" + threadName());
                Thread.sleep(3000);
            }
//            state = true;
            System.out.println("START" + threadName());
            Thread.sleep(1000);
            System.out.println("Mid" + threadName());
            Thread.sleep(10000);
            System.out.println("ENDED" + threadName());
            System.out.println("***");
        }
    }

    public void notifySection() {
        synchronized (lock) {
            state = false;
            lock.notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MultipleThreadsCANNOTRunInSynchronizedBlock obj = new MultipleThreadsCANNOTRunInSynchronizedBlock();

        new Thread(() -> {
            try {
                obj.synchronizedSection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                obj.synchronizedSection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                obj.synchronizedSection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                obj.synchronizedSection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(5000);
        new Thread(() -> {
            try {
                obj.notifySection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
