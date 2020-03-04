package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class JavaAPIReentrantReadWriteLock {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void doSomething() {
//        lock.writeLock().
        StampedLock lock = new StampedLock();
//        lock.try


    }

    public void readToWriteReentrant(){ // Reentrant Deadlock for read to write lock reentrant
        lock.readLock().lock();
        print("readlock acquired");
        lock.writeLock().lock();
        print("Reentrant writelock acquired");
        sleep(2000);
        lock.writeLock().unlock();
        lock.readLock().unlock();
    }


    public static void main(String[] args) {
        JavaAPIReentrantReadWriteLock obj = new JavaAPIReentrantReadWriteLock();

        startThread(() -> {
            obj.readToWriteReentrant();
            //while (true) {
                /*obj.lock.lock();
                obj.lockOther.lock();

                print("start");
                sleep(2000);
                print("End");
                obj.lock.unlock();
                sleep(5000);
                obj.lockOther1.lock();
                obj.lockOther.unlock();
                obj.lockOther1.unlock();*/
            //}
        });

        /*startThread(() -> {
            while (true) {
                synchronized (obj.lock){
                    synchronized (obj.lockOther){
                        print("start");
                        sleep(2000);
                        print("End");
                    }
                }

                obj.lock.unlock();
                obj.lockOther1.lock();
                obj.lockOther.unlock();
                obj.lockOther1.unlock();
//                obj.unlock();
//                sleep(2000);
            }
        });*/

        /*startThread(() -> {
            while (true) {
                obj.lock();
                print("start");
                obj.doSomething();
                sleep(2000);
                print("End");
                obj.unlock();
                sleep(2000);
            }
        });

        startThread(() -> {
            while (true) {
                obj.lock();
                print("start");
                obj.doNotifySomething();
                sleep(1000);
                print("End");
                obj.unlock();
                sleep(1000);
            }
        });

        startThread(() -> {
            while (true) {
                obj.lock();
                obj.doSomething();
                print("start");
                sleep(500);
                print("End");
                obj.unlock();
                sleep(500);
            }
        });*/
    }
}
