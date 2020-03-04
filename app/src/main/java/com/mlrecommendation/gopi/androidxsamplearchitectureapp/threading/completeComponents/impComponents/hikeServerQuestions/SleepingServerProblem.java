package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.hikeServerQuestions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
* Write 3 api calls as following

a. /sleep?timeout=20&connId=1 -> returns response after 20 Seconds which has connectionId = 1;
b. /getAllRequestsInfo -> returns all requests (in table form ) how much time remaing to give response ( suppose after 5 seconds first request should give response as 15 (20-5))
c. /killRequest?connId=2  -> should kill the request instantenously & return response.
* */
public class SleepingServerProblem {

    ExecutorService executor = Executors.newCachedThreadPool();
    ScheduledExecutorService responseExecutor = Executors.newSingleThreadScheduledExecutor();
    ConcurrentHashMap<String, Long> currentRequestMap = new ConcurrentHashMap<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    private volatile static SleepingServerProblem instance = null;
    private SleepingServerProblem() { }

    public static SleepingServerProblem getInstance(){
        if (instance == null) {
            synchronized (SleepingServerProblem.class) {
                if (instance == null) {
                    instance = new SleepingServerProblem();
                }
            }
        }
        return instance;
    }

    public void responseReturner() {

        responseExecutor.scheduleWithFixedDelay(()-> {

            long curTime = System.currentTimeMillis();
            for (Map.Entry<String, Long> entry : currentRequestMap.entrySet()) {
                Long value = entry.getValue();
                if (value == 0) {
                    currentRequestMap.remove(entry.getKey());
                    continue;
                }
                if(value < curTime){
                    currentRequestMap.remove(entry.getKey());
                    // send this data to client with appropriate socket ;
                    continue;
                }

            }
        }, 1000L, 1000L, TimeUnit.MILLISECONDS);
    }

    public void sleepResponse(String connectionId, long timeout) {
        long time = System.currentTimeMillis() + timeout * 1000;
        executor.submit(() -> {
            currentRequestMap.put(connectionId, time);
//            writeLock.lock();
//            writeLock.unlock();
        });
    }

    public void printMap() {
        executor.submit(() -> {
            readLock.lock();
            long currentTime = System.currentTimeMillis();
            JSONArray jsonArray = new JSONArray();
            for (Map.Entry<String, Long> entry : currentRequestMap.entrySet()) {
                if(entry.getValue() == 0 ) continue;
                long timeDiff = entry.getValue() - currentTime;
                if(timeDiff < 0 ) continue;
                try {
                    jsonArray.put(new JSONObject().put(entry.getKey(), timeDiff));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(currentRequestMap);
            readLock.unlock();
        });
    }

    public String killRequest(String connectionId) {
        executor.submit(() -> {
            currentRequestMap.put(connectionId, 0L);
//            writeLock.lock();
//            writeLock.unlock();
        });
        try {
            return new JSONObject().put("status", "killed").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            return new JSONObject().put("error", "unknown").toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
