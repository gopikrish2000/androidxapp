package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class SynchronizationTest {

    int count = 0;

    public void setCounter(int newVal){
        synchronized (this){
            count += newVal;
        }
    }

    public static void main(String[] args) {

        SynchronizationTest obj = new SynchronizationTest();
        CyclicBarrier barrier = new CyclicBarrier(2);

        startThread(() -> {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            print("after await");
            obj.setCounter(11);
        });

        startThread(() -> {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            print("after await2");
            obj.setCounter(22);
        });

        startThread(() -> {
            while (true){
                System.out.println("obj.count = " + obj.count);
                sleep(15);
            }
        });


    }
}
