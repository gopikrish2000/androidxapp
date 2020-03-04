package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.presentation;

public class MyBasicLock {
  private final Object lock = new Object();

  public void doWait() throws InterruptedException{
    synchronized(lock){
      lock.wait();
    }
  }

  public void doNotify(){
    synchronized(lock){
      lock.notifyAll();
    }
  }
}