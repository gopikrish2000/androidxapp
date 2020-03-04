package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class BasicLockPrac implements MyLockInterface {

    private Object lock = new Object();
    boolean isLocked = false;

    public void lock() {
        synchronized (lock) {
            while (isLocked) {
                waitObj(lock);
            }
            isLocked = true;
        }
    }

    public void unlock(){
        synchronized (lock){
            if(!isLocked){
                throw new RuntimeException("lock not called before exception");
            }
            isLocked = false;
            lock.notifyAll();
        }
    }
}


