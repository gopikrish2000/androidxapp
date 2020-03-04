package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.presentation;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MySlippedConditionsPresentation {

    boolean state = false;

    private void criticalSection() {
        synchronized (this) {
            while (state) {
                waitObj(this);
            }
        }
        synchronized (this) {
            state = true;
        }
    }

    public static void main(String[] args) {
        MySlippedConditionsPresentation obj = new MySlippedConditionsPresentation();
        startThread(() -> {
            obj.criticalSection();
        });
        startThread(() -> {
            obj.criticalSection();
        });
        startThread(() -> {
            obj.criticalSection();
        });
    }
}
