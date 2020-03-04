package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.print;
import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep;

public class WhenProcessStops {
    public static void main(String[] args) {
        firstMethod();
        print("Main thread End");
    }

    private static void firstMethod() {

        new Thread(() -> {  // non daemon thread. Process wont exit
            print("running thread ");
            sleep(15000);
            print("running thread end");
        }).start();

        Observable.just(1).delay(15, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).subscribe((abc) -> { // Daemon thread process will exit.
            print(abc+ "");
        });
    }
}

/*
*
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future = executorService.submit(() -> {
            sleep(10000);

        });

*/