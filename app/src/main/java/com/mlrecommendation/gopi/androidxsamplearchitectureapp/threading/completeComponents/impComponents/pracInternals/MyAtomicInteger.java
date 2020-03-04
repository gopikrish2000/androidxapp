package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import java.util.concurrent.atomic.AtomicInteger;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MyAtomicInteger {
    volatile int val;
    AtomicInteger atomicInteger;

    public MyAtomicInteger(int val) {
        this.val = val;
        atomicInteger = new AtomicInteger(val);
    }

    public int compareAndSetBlocking(int expect, int update){
        synchronized (this){
            if(val == expect){
                val = update;
            }
        }
        return val;
    }

    public boolean compareAndSetNonBlocking(int expect, int update){
        if(expect != val) return false;

        if(expect == val){
            val = update;
        }
        return true;
    }

    public int getAndIncrementNonBlocking() {
        while (true) {
            int curVal = atomicInteger.get();
            int nextVal = curVal + 1;
            if (atomicInteger.compareAndSet(curVal, nextVal)) {
                return nextVal;
            }
        }
    }

    public static void main(String[] args) {
        MyAtomicInteger obj = new MyAtomicInteger(10);
        startThread(() -> {
            int i = 0;
            while (i++ <= 10) {
                print(obj.getAndIncrementNonBlocking() + "");
                sleep(1000);
            }
        });
        startThread(() -> {
            int i = 0;
            while (i++ <= 10) {
                print(obj.getAndIncrementNonBlocking() + "");
                sleep(1000);
            }
        });
        /*startThread(() -> {
            int i = 0;
            while (i++ <= 10) {
                print(obj.getAndIncrementNonBlocking() + "");
                sleep(1000);
            }
        });
        startThread(() -> {
            int i = 0;
            while (i++ <= 10) {
                print(obj.getAndIncrementNonBlocking() + "");
                sleep(1000);
            }
        });
        startThread(() -> {
            int i = 0;
            while (i++ <= 10) {
                print(obj.getAndIncrementNonBlocking() + "");
                sleep(1000);
            }
        });
        startThread(() -> {
            int i = 0;
            while (i++ <= 10) {
                print(obj.getAndIncrementNonBlocking() + "");
                sleep(1000);
            }
        });*/
    }

}
