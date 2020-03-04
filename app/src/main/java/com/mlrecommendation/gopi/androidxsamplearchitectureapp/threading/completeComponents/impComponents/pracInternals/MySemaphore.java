package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep;
import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.threadName;

public class MySemaphore {

    volatile int count = 0;
    int MAX = 2;

    public void criticalSection(){
        acquire();
        System.out.println("executing Start critical section " + threadName());
        sleep(2000);
        System.out.println("executing Mid critical section " + threadName());
        sleep(4000);
        System.out.println("executing End critical section " + threadName());
        release();
    }

    private void acquire() {
        synchronized (this){
            while (count >= MAX) {
                ThreadUtils.waitObj(this);
            }
            count++;
        }
    }

    private void sample(int value, int changeValue) {
        if(count == value){
            count = changeValue;
        }
    }

    private void release() {
        synchronized (this){
            count --;
            notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MySemaphore obj = new MySemaphore();
        new Thread(obj::criticalSection).start();
        new Thread(obj::criticalSection).start();
        new Thread(obj::criticalSection).start();
        new Thread(obj::criticalSection).start();
        new Thread(obj::criticalSection).start();
        new Thread(obj::criticalSection).start();

    }
}
