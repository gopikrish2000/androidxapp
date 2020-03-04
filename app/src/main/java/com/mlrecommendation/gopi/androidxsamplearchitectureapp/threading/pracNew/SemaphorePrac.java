package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import java.util.HashMap;

public class SemaphorePrac implements MyLockInterface {

    int permits = 2;
    HashMap<Long,Integer> currentThreadMap = new HashMap<>();
    Object lock = new Object();

    @Override
    public void lock() {
        final long curThreadId = Thread.currentThread().getId();
        synchronized (lock) {
            while (currentThreadMap.size() >= permits && !currentThreadMap.containsKey(curThreadId)) {
                ThreadUtils.waitObj(lock);
            }
            currentThreadMap.put(curThreadId, ((currentThreadMap.get(curThreadId) == null)?0: currentThreadMap.get(curThreadId)) + 1);
        }
    }

    @Override
    public void unlock() {
        final long curThreadId = Thread.currentThread().getId();
        synchronized (lock) {
            if(!currentThreadMap.containsKey(curThreadId)){
                throw new RuntimeException("Please call lock before unlocking");
            }
            final Integer count = currentThreadMap.get(curThreadId);
            if(count == 1){
                currentThreadMap.remove(curThreadId);
            } else {
                currentThreadMap.put(curThreadId, count - 1);
            }
            lock.notifyAll();
        }
    }
}
