package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.designThreadLooper;

public class GopiHandler {

    /*
    *
    * What will happen if i put multiple ThreadLocalObjects of same type like String ( it keeps objectId -> value map for thread so it works)
    * How Handler is sending message to MessageQueue with target = this. otherwise it should crash.
    * What will happen if i put handler after looper creation.
    * How Looper detets this message should go to which handlers.onHandleEvent() ?
    * If a Looper has multiple handlers then message sent which handler it will go to or  all ???
    *
    * */

    private GopiLooper looper;
    private GopiMessageQueue messageQueue;
    private GopiHandlerCallback handlerCallback;

    public GopiHandler() {
        final GopiLooper myLooper = GopiLooper.getMyLooper();
        if (myLooper == null || myLooper.getMessageQueue() == null) {
            throw new RuntimeException("Looper.prepare not called");
        }
        this.looper = myLooper;
        messageQueue = looper.getMessageQueue();
    }

    public GopiHandler(GopiLooper looper, GopiHandlerCallback callback) {
        if (looper == null || looper.getMessageQueue() == null) {
            throw new RuntimeException("Looper.prepare not called");
        }
        this.looper = looper;
        messageQueue = looper.getMessageQueue();
        handlerCallback = callback;
    }

    public void post(Runnable runnable) {
        sendMessage(getMessageFromRunnable(runnable));
    }
    public void postDelayed(Runnable runnable, long delay) {
        final GopiMessage gopiMessage = getMessageFromRunnable(runnable);
        gopiMessage.executionTime = System.currentTimeMillis() + delay;
        sendMessage(gopiMessage);
    }


    private GopiMessage getMessageFromRunnable(Runnable runnable) {
        GopiMessage message = new GopiMessage();
        message.setRunnable(runnable);
        return message;
    }

    public void sendMessage(GopiMessage message) {
        message.setHandler(this);
        messageQueue.putMessage(message);
    }

    public void dispatchMessage(GopiMessage message) {
        if (message.getRunnable() != null) {
            message.getRunnable().run();
        } else {
            if (handlerCallback != null) {
                handlerCallback.handleMessage(message);
                return;
            }
            handleMessage(message);
        }
    }

    public void handleMessage(GopiMessage message) {  // Useful for subclasses which can override this method.
        //  do nothing
    }
}

class GopiHandlerCallback{

    public void handleMessage(GopiMessage message) {
        //  do nothing
    }
}
