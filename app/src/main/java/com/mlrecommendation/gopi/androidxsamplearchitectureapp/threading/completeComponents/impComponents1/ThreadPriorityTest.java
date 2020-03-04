package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class ThreadPriorityTest {


    public static void main(String[] args) {
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        print("availableProcessors is " + availableProcessors);
        CyclicBarrier barrier  = new CyclicBarrier(2);

        startThread(() -> {
            Thread.currentThread().setPriority(1);
//            sleep(2000);
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = 0;
            while (i++ < 100) {
                print(i + "write");
            }
        });
        startThread(() -> {
            Thread.currentThread().setPriority(9);
//            sleep(1000);
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = 0;
            while (i++ < 100) {
                print(i + "read");
            }
        });
    }
}
