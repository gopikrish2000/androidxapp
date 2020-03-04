package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MySlippedConditions {

    boolean state = false;

    private void criticalSection() {
        synchronized (this){
            print("entered synchronization block ");
            while (state) {
                print("waiting for state");
                waitObj(this);
            }
            print("Will execute the code.");
        }
        sleep(2000);
        synchronized (this){
            state = true;
            print("state set to true");
            notifyAll();
        }
    }

    public static void main(String[] args) {
        MySlippedConditions obj = new MySlippedConditions();
        startThread(() -> {obj.criticalSection();});
        startThread(() -> {obj.criticalSection();});
        startThread(() -> {obj.criticalSection();});
    }
}
