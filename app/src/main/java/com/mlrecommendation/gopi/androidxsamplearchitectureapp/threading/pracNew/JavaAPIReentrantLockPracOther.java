package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class JavaAPIReentrantLockPracOther implements MyLockInterface {

    private ReentrantLock lock = new ReentrantLock();
    private ReentrantLock lockOther = new ReentrantLock();
    private ReentrantLock lockOther1 = new ReentrantLock();

    Condition lockCondition = lock.newCondition();
    Condition lockOtherCondition = lockOther.newCondition();
    int i = 0;
    CyclicBarrier barrier = new CyclicBarrier(2);

    Object normalObj = new Object();

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public void doSomething() {
        try {
            lock.lock();
            while (i == 0) {
                sleep(2000);
                print("waiting tt");
                lockCondition.await();
            }
            print("running");
            lock.unlock();
        } catch (InterruptedException e) { e.printStackTrace(); };
    }

    public void doNotifySomething() {
        try {
            lock.lock();
            i = 1;
            print("notifying");
            lockCondition.signalAll();
            lock.unlock();
        } catch (Exception e) { e.printStackTrace();}
    }

    public void doMySomethingWait(){
        synchronized (normalObj){
            while (i == 0) {
                sleep(2000);
                print("waiting tt");
                try {
                    normalObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            print("running");
        }
    }

    public void doMySomethingNotify(){
        synchronized (normalObj) {
            try {
                i = 1;
                print("notifying");
                normalObj.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JavaAPIReentrantLockPracOther obj = new JavaAPIReentrantLockPracOther();


        startThread(() -> {
            while (true) {
                print("start");
//                obj.doSomething();
                obj.doMySomethingWait();
                sleep(500);
                print("End");
                sleep(500);
            }
        });

        startThread(() -> {
            while (true) {
                print("start");
                obj.doMySomethingNotify();
                sleep(1000);
                print("End");
                sleep(1000);
            }
        });
    }
}


