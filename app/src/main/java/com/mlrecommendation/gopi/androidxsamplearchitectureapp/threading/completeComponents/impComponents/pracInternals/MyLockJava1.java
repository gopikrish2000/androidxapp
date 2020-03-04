package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

// By notifyall(), all threads will awake from wait()state but as inside while condition
public class MyLockJava1 {
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
}

class MyLockTestJava1 {
    MyLockJava1 mylock = new MyLockJava1();

    // Test Reentrant deadlock section start
    private void testReentrantDeadLock() throws InterruptedException {
        new Thread(() -> {
            try {
//                outer();
                mylock.lock();
                System.out.println("executing critical "  + Thread.currentThread().getName());
                mylock.unlock();
            } catch (Exception e) {
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
        new Thread(() -> {
            try {
                mylock.lock();
//                Thread.sleep(1000);
                System.out.println("executing critical "  + Thread.currentThread().getName());
//                mylock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                mylock.lock();
//                Thread.sleep(1000);
                System.out.println("executing critical "  + Thread.currentThread().getName());
//                mylock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                mylock.lock();
//                Thread.sleep(1000);
                System.out.println("executing critical "  + Thread.currentThread().getName());
//                mylock.unlock();
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
//        mylock.lock();  // if lock is not reentrant then this will be Reentrant deadlock
        System.out.println("executing INNER "  + Thread.currentThread().getName());
        mylock.unlock();
    }

    // Test Reentrant deadlock section End






    public static void main(String[] args) throws InterruptedException {
        MyLockTestJava1 obj = new MyLockTestJava1();
        obj.testReentrantDeadLock();
    }
}
