package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

public class MyLockJava {
    final Object lockObj = new Object();
    boolean isLocked = false;
    String threadName = null;

    public void lock() throws InterruptedException {
        synchronized (lockObj) {
            while (isLocked && !Thread.currentThread().getName().equals(threadName)){
                System.out.println("Thread waiting is " + Thread.currentThread().getName());
                lockObj.wait();  // && !Thread.currentThread().getName().equals(threadName) made this lock reentrant.
            }
            isLocked = true;
            threadName = Thread.currentThread().getName();
            lockSectionCode();
//            lockObj.notifyAll();
        }
    }

    public void unlock() throws InterruptedException {
        synchronized (lockObj) {
            if (!threadName.equals(Thread.currentThread().getName())) {
                throw new RuntimeException("illegal monitor ");
            }
            System.out.println(("UNLocked thread "+ threadName));
            isLocked = false;
            lockObj.notifyAll();
        }
    }

    private void lockSectionCode() throws InterruptedException {
        synchronized (lockObj) {
            System.out.println("Lock thread "+ threadName);
            Thread.sleep(10000);
        }
    }
}

class MyLockTestJava {
    MyLockJava mylock = new MyLockJava();

    // Test Reentrant deadlock section start
    private void testReentrantDeadLock() throws InterruptedException {
        new Thread(() -> {
            try {
                outer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                mylock.lock();
//                Thread.sleep(1000);
                System.out.println("executing critical "  + Thread.currentThread().getName());
                mylock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
       /* new Thread(() -> {
            try {
                mylock.lock();
//                Thread.sleep(1000);
                System.out.println("executing critical "  + Thread.currentThread().getName());
                mylock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/
    }

    private void outer() throws InterruptedException {
        mylock.lock();
        System.out.println("executing OUTER "  + Thread.currentThread().getName());
        inner();
        Thread.sleep(3000);
        System.out.println("executing OUTER End"  + Thread.currentThread().getName());

        mylock.unlock();
    }

    private void inner() throws InterruptedException {
        mylock.lock();  // if lock is not reentrant then this will be Reentrant deadlock
        System.out.println("executing INNER "  + Thread.currentThread().getName());
        mylock.unlock();
    }

    // Test Reentrant deadlock section End






    public static void main(String[] args) throws InterruptedException {
        MyLockTestJava obj = new MyLockTestJava();
        obj.testReentrantDeadLock();
    }
}
