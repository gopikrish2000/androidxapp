package com.mlrecommendation.gopi.androidxsamplearchitectureapp;


import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.print;

public class SampleTestt {

    public static void main(String[] args) {
        Observable.just(1).delay(5, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).subscribe(integer -> {
            print(" in main ");
        });
    }
}
