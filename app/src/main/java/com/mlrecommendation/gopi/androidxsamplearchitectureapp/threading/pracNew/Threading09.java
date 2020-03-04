package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.concurrent.locks.StampedLock;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.print;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Threading09 {
  static class Foo {
    public  int x, y;
    public  boolean f;

    public void bar() {
      x = 5;
      y = 10;
      f = true;
    }
  }

  StampedLock stampedLock = new StampedLock();
  int x,y;

  public boolean moveIfAt(int oldX, int oldY, int newX, int newY) {
    long stamp = stampedLock.readLock();
    try {
      while (x == oldX && y == oldY) {
        long writeStamp = stampedLock.tryConvertToWriteLock(stamp);
        if (writeStamp != 0) {
          stamp = writeStamp;
          x = newX;
          y = newY;
          return true;
        } else {
          stampedLock.unlockRead(stamp);
          stamp = stampedLock.writeLock();
        }
      }
      return false;
    } finally {
      stampedLock.unlock(stamp); // could be read or write lock
    }
  }

  public static void main(String... args) throws InterruptedException {
    Foo f = new Foo();

    Thread t1 = new Thread(f::bar);
    Thread t2 = new Thread(() -> {
      while (!f.f) { print("as"); }
      assert (f.x == 5);
      print("t2 end");
    });

    t2.start();
    Thread.sleep(5000);

    t1.start();

    t1.join();
    t2.join();
  }
}