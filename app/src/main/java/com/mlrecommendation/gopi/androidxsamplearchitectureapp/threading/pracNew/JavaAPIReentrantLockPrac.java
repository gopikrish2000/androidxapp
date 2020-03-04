package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;
import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep;

public class JavaAPIReentrantLockPrac implements MyLockInterface {

    private ReentrantLock lock = new ReentrantLock();
    private ReentrantLock lockOther = new ReentrantLock();
    private ReentrantLock lockOther1 = new ReentrantLock();

    Condition lockCondition = lock.newCondition();
    int i = 0;

    public void lock() {
        lock.lock();
       /* final int queueLength = lock.getQueueLength();
        final boolean bool = lock.hasQueuedThread(Thread.currentThread());
        final int waitQueueLength = lock.getWaitQueueLength(lockCondition);
        System.out.printf("%d %b %d  ||", queueLength, bool, waitQueueLength);*/
    }

    public void unlock() {
        lock.unlock();
    }

    public void doSomething() {
        try {
            lock();

            while (i == 0){
                print("waiting");
//                lockCondition.awaitUninterruptibly(); // ignores interrupts
                lockCondition.await();
            }
            print("running");

            unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doNotifySomething() {
        lock();
        i = 1;
        print("notifying");
//        lock.
        lockCondition.signalAll();
        unlock();
    }

    public static void main(String[] args) {
        JavaAPIReentrantLockPrac obj = new JavaAPIReentrantLockPrac();

        /*startThread(() -> {
            while (true) {
                obj.lock.lock();
                obj.lockOther.lock();

                print("start");
                sleep(2000);
                print("End");
                obj.lock.unlock();
                sleep(5000);
                obj.lockOther1.lock();
                obj.lockOther.unlock();
                obj.lockOther1.unlock();
            }
        });*/

        /*startThread(() -> {
            while (true) {
                synchronized (obj.lock){
                    synchronized (obj.lockOther){
                        print("start");
                        sleep(2000);
                        print("End");
                    }
                }

                obj.lock.unlock();
                obj.lockOther1.lock();
                obj.lockOther.unlock();
                obj.lockOther1.unlock();
//                obj.unlock();
//                sleep(2000);
            }
        });*/

        startThread(() -> {
            while (true) {
                obj.lock();
                print("start");
                obj.doSomething();
                sleep(2000);
                print("End");
                obj.unlock();
                sleep(2000);
            }
        });

        startThread(() -> {
            while (true) {
                obj.lock();
                print("start");
                obj.doNotifySomething();
                sleep(1000);
                print("End");
                obj.unlock();
                sleep(1000);
            }
        });

        /*startThread(() -> {
            while (true) {
                obj.lock();
                obj.doSomething();
                print("start");
                sleep(500);
                print("End");
                obj.unlock();
                sleep(500);
            }
        });*/
    }
}


