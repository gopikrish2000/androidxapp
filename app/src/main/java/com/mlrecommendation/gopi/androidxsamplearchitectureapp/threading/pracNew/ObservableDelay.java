package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.print;

public class ObservableDelay {
    static boolean condition = true;


    public static void main(String[] args) {
        print("initial");
        final Observable<Integer> observable = Observable.just(1).delay(1, TimeUnit.SECONDS);
        Observable.concat(observable, Observable.interval(250, TimeUnit.MILLISECONDS).filter((Int) -> condition))
                .subscribe((item) -> {
                    condition = false;
                    print(item+"");
        }, (error)->{
            print(error.toString());
        });
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
