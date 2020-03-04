package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MyReadWriteLockReentrant {  // with Writer to Reader Reentrance + Reader to Writer Reentrance(when only 1 reader exist)

    final Object readLock = new Object();
    volatile int readersCount = 0;
    volatile int writersCount = 0;
    volatile AtomicInteger writeRequests = new AtomicInteger(0);
    volatile String writerThreadName = "";
    volatile Map<String, Integer> readerAccessMap = new HashMap<>();


    public void acquireReadLock() {
        synchronized (readLock) {
            while (!canAcquiredReadlock()) {
                waitObj(readLock);
            }
            readersCount++;
            String threadName = Thread.currentThread().getName();
            readerAccessMap.put(threadName, readerAccessMap.get(threadName) == null ? 1 : readerAccessMap.get(threadName) + 1);
        }
    }

    private boolean canAcquiredReadlock() {
        String name = Thread.currentThread().getName();
        if (readerAccessMap.get(name) != null || writerThreadName.equals(name)) return true;
        if(writersCount > 0 || writeRequests.get() > 0) return false;
        return true;
    }

    public void releaseReadLock() {
        synchronized (readLock) {
            readersCount--;
            readLock.notifyAll();

            String name = Thread.currentThread().getName();
            Integer val = readerAccessMap.get(name);
            if (val == 1) {
                readerAccessMap.remove(name);
            } else {
                readerAccessMap.put(name, val - 1);
            }
        }
    }

    public void acquireWriteLock() {   // add reentrace for write , as only one thread enters this.
        writeRequests.getAndIncrement();
        synchronized (readLock) {
            while (!canAcquireWriteRequest()) {
                waitObj(readLock);
            }
            writeRequests.decrementAndGet();
            writersCount++;
            writerThreadName = Thread.currentThread().getName();
        }
    }

    private boolean canAcquireWriteRequest() {
        String name = Thread.currentThread().getName();
        if (writerThreadName.equals(name)) return true;
        if(writersCount > 0 || readersCount > 0) return false;
        return true;
    }

    public void releaseWriteLock() {
        synchronized (readLock) {
            writersCount--;
            readLock.notifyAll();
            if (writersCount == 0 ) {
                writerThreadName = "";
            }
        }
    }

    private void otherReadOperation() {
        acquireReadLock();
        print("acquired OTHER_READ_OPERATION readlock ");
        sleep(3000);
        releaseReadLock();
    }

    private void otherWriteOperation() {
        acquireWriteLock();
        print("acquired OTHER_WRITE_OPERATION writelock ");
        sleep(3000);
        releaseWriteLock();
    }

    public static void main(String[] args) {
        MyReadWriteLockReentrant obj = new MyReadWriteLockReentrant();

       /* startThread(() -> {
            while (true) {
                obj.acquireReadLock();
                print("Performing read Start");
                sleep(2000);
                obj.otherReadOperation();
                print("Performing read End");
                obj.releaseReadLock();
            }
        });*/
       /* startThread(() -> {
            while (true) {
                obj.acquireReadLock();
                print("Performing read Start");
                sleep(2000);
                print("Performing read End");
                obj.releaseReadLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireReadLock();
                print("Performing read Start");
                sleep(2000);
                print("Performing read End");
                obj.releaseReadLock();
            }
        });*/

        startThread(() -> {
            while (true) {
                obj.acquireWriteLock();
                print("Performing WRITE Start");
                sleep(4000);
                obj.otherWriteOperation();
                obj.otherReadOperation();
                print("Performing WRITE End");
                System.out.println("");
                obj.releaseWriteLock();
                sleep(2000);
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireWriteLock();
                print("Performing WRITE Start");
                sleep(5000);
                print("Performing WRITE End");
                obj.releaseWriteLock();
                sleep(2000);
            }
        });

    }


}
