package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import java.util.concurrent.atomic.AtomicInteger;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MyReadWriteLockNonReentrant {  // not working perfectly.

    final Object readLock = new Object();
     volatile int readersCount = 0;
     volatile int writersCount = 0;
    volatile AtomicInteger writeRequests = new AtomicInteger(0);

    public void acquireReadLock() {
        synchronized (readLock) {
           while (writersCount > 0 ||  writeRequests.get() > 0){
               waitObj(readLock);
           }
            readersCount++;
        }
    }

    public void releaseReadLock() {
        synchronized (readLock) {
            readersCount--;
            readLock.notifyAll();
        }
    }

    public void acquireWriteLock() {   // add reentrace for write , as only one thread enters this.
        writeRequests.getAndIncrement();
        synchronized (readLock) {
            while (writersCount > 0 || readersCount > 0) {
//                print("Waiting to acquire Write Lock");
                waitObj(readLock);
            }
            writeRequests.decrementAndGet();
            writersCount++;
        }
    }

    public void releaseWriteLock() {
        synchronized (readLock) {
            writersCount--;
            readLock.notifyAll();
        }
    }

    public static void main(String[] args) {
        MyReadWriteLockNonReentrant obj = new MyReadWriteLockNonReentrant();

        startThread(()->{
            while (true) {
                obj.acquireReadLock();
                print("Performing read Start");
                sleep(2000);
                print("Performing read End");
                obj.releaseReadLock();
            }
        });
        startThread(()->{
            while (true) {
                obj.acquireReadLock();
                print("Performing read Start");
                sleep(2000);
                print("Performing read End");
                obj.releaseReadLock();
            }
        });
        startThread(()->{
            while (true) {
                obj.acquireReadLock();
                print("Performing read Start");
                sleep(2000);
                print("Performing read End");
                obj.releaseReadLock();
            }
        });

        startThread(()->{
            while (true) {
                obj.acquireWriteLock();
                print("Performing WRITE Start");
                sleep(4000);
                print("Performing WRITE End");
                obj.releaseWriteLock();
            }
        });

        startThread(()->{
            while (true) {
                obj.acquireWriteLock();
                print("Performing WRITE Start");
                sleep(5000);
                print("Performing WRITE End");
                obj.releaseWriteLock();
            }
        });
    }
}
