package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.threadName;

// First check if synchronized(lock) is done in 1 method & 2nd different method same synchornized(lock) is  used  then those threads work or not  then Y http://tutorials.jenkov.com/java-concurrency/nested-monitor-lockout.html
class MyCustomLock {
    final Object lockObj = new Object();
    boolean isLocked = false;
    String threadName = null;

    public void lock() throws InterruptedException {
        synchronized (lockObj) {
            while (isLocked && !Thread.currentThread().getName().equals(threadName)){
                System.out.println("Thread waiting Started is " + Thread.currentThread().getName());
                lockObj.wait();  // && !Thread.currentThread().getName().equals(threadName) made this lock reentrant.
                System.out.println("Thread waiting DONE is " + Thread.currentThread().getName());
            }
            isLocked = true;
            threadName = Thread.currentThread().getName();
//            lockSectionCode();
//            lockObj.notifyAll();
            System.out.println("Lock thread "+ threadName);
            Thread.sleep(10000);
        }
    }

    public void unlock() throws InterruptedException {
        synchronized (lockObj) {
            if (!threadName.equals(Thread.currentThread().getName())) {
                throw new RuntimeException("illegal monitor ");
            }
            System.out.println(("UNLocked thread "+ threadName));
            System.out.println("***");
            isLocked = false;
            lockObj.notifyAll();
        }
    }

    /*private void lockSectionCode() throws InterruptedException {
        synchronized (lockObj) {
            System.out.println("Lock thread "+ threadName);
            Thread.sleep(10000);
        }
    }*/

    public void firstSynchronizedMethod() throws InterruptedException {
        synchronized (lockObj) {
            System.out.println("MyCustomLock.firstSynchronizedMethod Start" + threadName());
            Thread.sleep(10000);
            System.out.println("MyCustomLock.firstSynchronizedMethod End" + threadName());
        }

    }

    public synchronized void secondSynchronizedMethod() throws InterruptedException {
        synchronized (lockObj) {
            System.out.println("MyCustomLock.secondSynchronizedMethod Start " + threadName());
            Thread.sleep(10000);
            System.out.println("MyCustomLock.secondSynchronizedMethod End " + threadName());
        }
    }
}

class OneSynchronizedBlockWorksInMonitor {
    MyCustomLock mylock = new MyCustomLock();

    // Test Reentrant deadlock section start
    private void testNestedMonitorLock() throws InterruptedException {
        new Thread(() -> {
            try {
//                outer();
                /*mylock.lock();
                System.out.println("executing critical "  + Thread.currentThread().getName());
                mylock.unlock();*/
                mylock.firstSynchronizedMethod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                /*mylock.lock();
//                Thread.sleep(1000);
                System.out.println("executing critical "  + Thread.currentThread().getName());
                mylock.unlock();*/
                mylock.firstSynchronizedMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                /*mylock.lock();
//                Thread.sleep(1000);
                System.out.println("executing critical "  + Thread.currentThread().getName());
//                mylock.unlock();*/
                mylock.secondSynchronizedMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                /*mylock.lock();
//                Thread.sleep(1000);
                System.out.println("executing critical "  + Thread.currentThread().getName());
//                mylock.unlock();*/
                mylock.secondSynchronizedMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                /*mylock.lock();
//                Thread.sleep(1000);
                System.out.println("executing critical "  + Thread.currentThread().getName());
//                mylock.unlock();*/
                mylock.secondSynchronizedMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void outer() throws InterruptedException {
        mylock.lock();
        System.out.println("executing OUTER "  + Thread.currentThread().getName());
        inner();
        mylock.unlock();
    }

    private void inner() throws InterruptedException {
        mylock.lock();  // if lock is not reentrant then this will be Reentrant deadlock
        System.out.println("executing INNER "  + Thread.currentThread().getName());
        //mylock.unlock();
    }

    // Test Reentrant deadlock section End



    public static void main(String[] args) throws InterruptedException {
        OneSynchronizedBlockWorksInMonitor obj = new OneSynchronizedBlockWorksInMonitor();
        obj.testNestedMonitorLock();
    }
}
