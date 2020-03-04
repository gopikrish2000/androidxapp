package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.designThreadLooper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Handler;

public class GopiMessageQueue {

    private LinkedBlockingQueue<GopiMessage> linkedBlockingQueue;
    public GopiMessageQueue() {
        linkedBlockingQueue = new LinkedBlockingQueue<>();
    }

    public GopiMessage getNext()  {
        try {
            return linkedBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putMessage(GopiMessage message) {
        linkedBlockingQueue.add(message);  // Also re-arrange the messages based on message.executionTime & If after re-arrange the current item is first then interrupty & notify the blocking queue to again take the correct element.
    }

    public void clear(){
        linkedBlockingQueue.clear();
    }
}


