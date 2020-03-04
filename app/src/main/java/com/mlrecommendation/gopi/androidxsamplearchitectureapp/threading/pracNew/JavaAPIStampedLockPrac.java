package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.pracNew;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.concurrent.locks.StampedLock;

@RequiresApi(api = Build.VERSION_CODES.N)
public class JavaAPIStampedLockPrac {

    StampedLock stampedLock = new StampedLock();
    int x, y;

    public void moveToPosition(int newX, int newY) {
        final long writeStamp = stampedLock.writeLock();
        try {
            x = newX;
            y = newY;
        } finally {
            stampedLock.unlockWrite(writeStamp);
        }
    }


    public static void main(String[] args) {

    }
}
