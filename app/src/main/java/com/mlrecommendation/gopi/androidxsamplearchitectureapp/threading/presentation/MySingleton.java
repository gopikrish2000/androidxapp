package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.presentation;

public class MySingleton {

    private MySingleton(){}
    private static volatile MySingleton instance;

    public static MySingleton getInstance(){
        if (instance == null) {
            synchronized (MySingleton.class) {
                if (instance == null) {
                    instance = new MySingleton();
                }
            }
        }
        return instance;
    }
}
