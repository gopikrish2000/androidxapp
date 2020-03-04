package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.print;
import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep;

public class MyDeadLock {
    Object lock = new Object();

    private void firstFunc() {
        print("Start MyDeadLock.firstFunc");
        synchronized (this) {
            print("lock acquired MyDeadLock.firstFunc");
            sleep(4000);
            synchronized (lock) {
                sleep(2000);
                print("Critical MyDeadLock.firstFunc");
            }
        }
    }
    
    private void secondFunc() {
        print("Start MyDeadLock.secondFunc");
        synchronized (lock) {
            print("lock acquired MyDeadLock.secondFunc");
            sleep(4000);
            synchronized (this) {
                sleep(2000);
                print("Critical MyDeadLock.firstFunc");
            }
        }
    }

    public static void main(String[] args) {
        MyDeadLock obj = new MyDeadLock();
        new Thread(() -> {
            obj.firstFunc();
        }).start();
        new Thread(() -> {
            obj.secondFunc();
        }).start();
    }
}
