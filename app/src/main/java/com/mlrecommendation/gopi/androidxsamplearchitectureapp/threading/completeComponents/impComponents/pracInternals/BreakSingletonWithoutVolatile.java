package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep;
import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.threadName;

public class BreakSingletonWithoutVolatile {
    Integer value;
    String name;

    private static BreakSingletonWithoutVolatile instance;
    private static volatile int i = 0;

    private BreakSingletonWithoutVolatile(Integer value, String name) {
        System.out.println("BreakSingletonWithoutVolatile.BreakSingletonWithoutVolatile() "+ threadName());
//        sleep(10000);
        this.value = new Integer(value);
        this.name = name;

    }

    public static BreakSingletonWithoutVolatile getInstance() {
//        BreakSingletonWithoutVolatile helper = instance;
//        sleep(1000);
            if(instance != null) return instance;
            if (instance == null) {
                synchronized (BreakSingletonWithoutVolatile.class) {
                    if (instance == null) {
                    /*if (i == 0) {
                        sleep(2000);
                        i++;
                    }*/
                        instance = new BreakSingletonWithoutVolatile(20, "gopi1");
                        System.out.println(" name is "+ instance.name );
                    }
                }
            }
        return instance;
    }

    void printVars() {
        System.out.println("value is "+ value + " name is "+ name + threadName());

    }

    static class SingletonBreaker{

        public static void main(String[] args)  {
            System.out.println("Program Starttime " + ThreadUtils.threadName());
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
/*
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();

            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();

            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();

            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();
            sleep(1000);
            new Thread(() -> {
                BreakSingletonWithoutVolatile.getInstance().printVars();
            }).start();*/
        }
    }
}
