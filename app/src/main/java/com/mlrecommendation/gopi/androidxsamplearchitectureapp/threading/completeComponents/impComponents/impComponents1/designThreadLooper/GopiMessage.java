package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.designThreadLooper;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class GopiMessage {

    int type;
    Object content;
    GopiHandler handler; // target
    Runnable runnable;
    long executionTime; // when
    static Queue<GopiMessage> messageProviderQueue = new LinkedBlockingQueue<>();

    public GopiMessage() {
    }

    public static GopiMessage obtain(){
        final GopiMessage item = messageProviderQueue.poll();
        if(item != null){
            return item;
        }
        return new GopiMessage();
    }

    public void recycle(){
        type = 0;
        content = null;
        handler = null;
        runnable = null;
        executionTime = -1;
        messageProviderQueue.add(this);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public GopiHandler getHandler() {
        return handler;
    }

    public void setHandler(GopiHandler handler) {
        this.handler = handler;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public String toString() {
        return "GopiMessage{" +
                "type=" + type +
                ", content=" + content +
                ", handler=" + handler +
                ", runnable=" + runnable +
                '}';
    }
}
