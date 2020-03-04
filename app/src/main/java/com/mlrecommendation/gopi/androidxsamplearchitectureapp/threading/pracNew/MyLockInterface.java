package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

public interface MyLockInterface {

    public void lock();
    void unlock();
   default void doSomething(){}
}
