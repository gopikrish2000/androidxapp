package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.presentation;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MyBasicLock1 {
  private final Object lockObj = new Object();
  private boolean isNeedToWait = false;

  public void lock() {
    synchronized(lockObj){
      if (isNeedToWait) {
        try {
          lockObj.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      isNeedToWait = true;
    }
  }

  private void unlock(){
    synchronized(lockObj){
      isNeedToWait = false;
      lockObj.notifyAll();
    }
  }

  public static void main(String[] args) {
    MyBasicLock1 obj = new MyBasicLock1();
    startThread(() -> {
      obj.lock();
    });
    startThread(() -> {
      sleep(1000);
      obj.lock();
    });
    startThread(() -> {
      sleep(1000);
      obj.lock();
    });
    startThread(() -> {
      sleep(1000);
      obj.unlock();
    });
  }
}
