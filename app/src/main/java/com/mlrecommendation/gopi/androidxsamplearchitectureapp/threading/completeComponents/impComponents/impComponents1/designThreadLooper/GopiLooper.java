package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.designThreadLooper;

import android.os.Looper;
import android.os.Message;

public class GopiLooper {

    private static ThreadLocal<GopiLooper> threadLocal = new ThreadLocal<>();
    private GopiMessageQueue messageQueue;

    private GopiLooper() { }

    public static void prepare() {
        if (threadLocal.get() != null) {
            throw new RuntimeException("Already prepare called for this thread");
        }
        GopiLooper looper = new GopiLooper();
        looper.messageQueue  = new GopiMessageQueue();
        threadLocal.set(looper);
    }

    public static void loop() {
        if (getMyLooper() == null) {
            throw new RuntimeException("Prepare NOT CALLED for this thread");
        }
        GopiMessageQueue gopiMessageQueue = getMyLooper().messageQueue;

        while (true) {
            GopiMessage next = gopiMessageQueue.getNext();
            if (next == null) {
                return;
            }
            next.getHandler().dispatchMessage(next);
        }
    }

    public static void quit(){
        final GopiLooper gopiLooper = threadLocal.get();
        gopiLooper.getMessageQueue().clear();
        gopiLooper.messageQueue = null;
        threadLocal.remove();
    }

    public static GopiLooper getMyLooper() {
        return threadLocal.get();
    }

    public GopiMessageQueue getMessageQueue() {
        return getMyLooper().messageQueue;
    }
}
