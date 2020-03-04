package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

@RequiresApi(api = Build.VERSION_CODES.N)
public class JavaAPIStampedLockDemo {
    Map<String, String> map = new HashMap<>();
    private StampedLock stampedLock = new StampedLock();

    public void put(String key, String value) {
        long stamp = stampedLock.writeLock();
        try {
            map.put(key, value);
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    public String get(String key) throws InterruptedException {
        long stamp = stampedLock.readLock();
        try {
            return map.get(key);
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }


    public String readWithOptimisticLock(String key) {
        long stamp = stampedLock.tryOptimisticRead();
        String value = map.get(key);

        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();
            try {
                return map.get(key);
            } finally {
                stampedLock.unlock(stamp);
            }
        }
        return value;
    }

    public void tryReentrantRead() { // works fine
        final long readStamp = stampedLock.readLock();
        final long nestedStamp = stampedLock.readLock();
        print("inside nested Read stampedLock");
        sleep(2000);
        stampedLock.unlock(nestedStamp);
        stampedLock.unlock(readStamp);
    }

    public void tryReentrantWrite() {  //Reentrant deadlock.
        final long readStamp = stampedLock.writeLock();
        final long nestedStamp = stampedLock.writeLock();
        print("inside nested write stampedLock");
        sleep(2000);
        stampedLock.unlock(nestedStamp);
        stampedLock.unlock(readStamp);
    }

    Runnable r1 = ()->{
        long stamp = stampedLock.tryOptimisticRead();
        try{
            System.out.println("In optimistic stampedLock " + stampedLock.validate(stamp));  // can return true
            sleep(2);
            System.out.println("In optimistic stampedLock " + stampedLock.validate(stamp)); // can return false ( as write lock can break this at any point )
        } finally{
            stampedLock.unlock(stamp);
        }
    };

    // Runnable as lambda - Write lock
    Runnable r2 = ()->{
        System.out.println("about to get write lock");
        sleep(1);
        long stamp = stampedLock.writeLock();
        try{
            System.out.println("After getting write lock ");
        }finally{
            stampedLock.unlock(stamp);
            System.out.println("Relinquished write lock ");
        }
    };

    public void useAsLockInterface(){
        final Lock lock = this.stampedLock.asReadLock();
        final Condition condition = lock.newCondition();
    }

    public static void main(String[] args) {
        JavaAPIStampedLockDemo obj = new JavaAPIStampedLockDemo();

        startThread(() -> {
//            obj.tryReentrantRead();
//            obj.tryReentrantWrite();
            obj.useAsLockInterface();
        });
    }
}
