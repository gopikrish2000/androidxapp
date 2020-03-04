package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MyFairLockTestWithOriginal {
    private ReentrantLock reentrantLock = new ReentrantLock(true);


    public void acquireFairLock() {
        reentrantLock.lock();
    }

    public void releaseFairLock() {
        reentrantLock.unlock();
    }

    public static void main(String[] args) {
        MyFairLockTestWithOriginal obj = new MyFairLockTestWithOriginal();
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
                sleep(2000);
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
                sleep(3000);
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
                sleep(100);
            }
        });
        /*startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });*/
    }
}
