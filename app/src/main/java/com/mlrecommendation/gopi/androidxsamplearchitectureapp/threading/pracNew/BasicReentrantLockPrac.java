package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.print;
import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.waitObj;

public class BasicReentrantLockPrac implements MyLockInterface {

    private final Object lock = new Object();
    long currentThreadId = -1;
    int count = 0;
    boolean isLocked = false;

    private final Object conditionObj = new Object();

    public void lock() {
        synchronized (lock) {
            while (isLocked && (currentThreadId != Thread.currentThread().getId())) {
                waitObj(lock);
            }
            isLocked = true;
            count++;
            currentThreadId = Thread.currentThread().getId();
        }
    }

    public void unlock() {
        synchronized (lock) {
            if (!isLocked) {
                throw new RuntimeException("lock not called before exception");
            }
            count--;
            if (count == 0) {
                isLocked = false;
                currentThreadId = -1;
                lock.notifyAll();
            }
        }
    }

    public void conditionAwait() {
        synchronized (lock) {
            while (true) {
                try {
                    print("waiting here");
                    lock.wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void conditionSignal() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }
}


