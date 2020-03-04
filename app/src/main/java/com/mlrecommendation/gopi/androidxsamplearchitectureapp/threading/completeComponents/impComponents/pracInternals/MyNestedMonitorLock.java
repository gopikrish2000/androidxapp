package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MyNestedMonitorLock {
  protected final Object monitorObject = new Object();
  protected boolean isLocked = false;

  public void lock() {
    synchronized(this){
      while(isLocked){
        synchronized(this.monitorObject){
          print("wait on monitorObject");
          waitObj(this.monitorObject);
          print("wait Done on monitorObject");
        }
      }
      isLocked = true;
      print("locked successfully");
    }
  }

  public void unlock(){
    print("Trying unlock");
    synchronized(this){
      this.isLocked = false;
      print("Unlock got this lock");
      synchronized(this.monitorObject){
        print("Unlock got monitor lock");
        this.monitorObject.notify();
      }
    }
  }

  public static void main(String[] args) {
    MyNestedMonitorLock obj = new MyNestedMonitorLock();
    new Thread(() -> {
      obj.lock();
    }).start();
    new Thread(() -> {
      obj.lock();
    }).start();
    sleep(2000);
    new Thread(() -> {
      obj.unlock();
    }).start();
  }
}