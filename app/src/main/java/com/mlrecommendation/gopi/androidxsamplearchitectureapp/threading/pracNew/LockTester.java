package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;
import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep;

public class LockTester {
    public volatile int i = 0;

    public void testReentrant(MyLockInterface obj){
        obj.lock();
        print("inside reentrant method");
        testReentrantNested(obj);
        sleep(300);
        obj.unlock();
    }

    public void testReentrantNested(MyLockInterface obj){
        obj.lock();
        print("inside NESTED reentrant method");
        sleep(300);
        obj.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        LockTester currentObj = new LockTester();
//        MyLockInterface obj = new JavaAPIReentrantLockPrac();
        BasicReentrantLockPrac obj = new BasicReentrantLockPrac();
//        MyLockInterface obj = new SemaphorePrac();

        startThread(() -> {
            while (true) {
                obj.lock();
                while (currentObj.i ==0){
                    print("conditionAwait");
                    obj.conditionAwait();
                }
                print("start");
                sleep(2000);
                currentObj.testReentrant(obj);
                print("End");
                obj.unlock();
                sleep(2000);
            }
        });

        startThread(() -> {
            while (true) {
                print("trying to acquire lock");
                obj.lock();
                while (currentObj.i == 0){
                    currentObj.i = 1;
                    print("conditionSignal");
                    obj.conditionSignal();
                }
                print("start");
                sleep(1000);
                print("End");
                obj.unlock();
                sleep(1000);
            }
        });

        /*startThread(() -> {
            while (true) {
                obj.lock();
                print("start");
                sleep(500);
                print("End");
                obj.unlock();
                sleep(500);
            }
        });
        startThread(() -> {
            while (true) {
                obj.lock();
                print("start");
                sleep(500);
                print("End");
                obj.unlock();
                sleep(500);
            }
        });*/
    }
}
