package com.mlrecommendation.gopi.androidxsamplearchitectureapp.javaConcepts.others;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Gopi Krishna on 2020-02-18.
 */
public class VariableScope {

    public static void main(String[] args) {
        variableScopingTestt();
        ThreadUtils.sleep(5000);
    }

    private static void variableScopingTestt() {
        int input = 25;
        Observable.timer(5, TimeUnit.SECONDS).subscribe( (val) -> {
            System.out.println("input = " + input);
        });
    }
}
