package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import java.util.LinkedList;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MyFairLockComplete {
    private final Object lock = new Object();
    private volatile LinkedList<IndividualLockNode> individualLockNodes = new LinkedList<>();
    private volatile boolean isLocked = false;


    public void acquireFairLock() {
        IndividualLockNode individualLockNode = null;
        boolean isItemAdded = false;
        while (true) {
            synchronized (lock) {  // avoid slipping conditions n
                if (!isLocked) {
                    isLocked = true;
                    break;
                } else {
                    if (!isItemAdded) {
                        individualLockNode = new IndividualLockNode();
                        individualLockNodes.add(individualLockNode);
                        isItemAdded = true;
                    }
                }
            }
            individualLockNode.acquire();
        }
    }

    public void releaseFairLock() {
        synchronized (lock) {
            isLocked = false;
            if ((individualLockNodes.peekFirst() != null)) {
                individualLockNodes.removeFirst().release();
            } else {
                lock.notifyAll();
            }
        }
    }

    private static class IndividualLockNode {
        final Object individualLock = new Object();
        volatile boolean isLocked = true;

        void acquire() {
            synchronized (individualLock) {
                while (isLocked) {
                    waitObj(individualLock);
                }
                isLocked = true;
            }
        }

        void release() {
            synchronized (individualLock) {
                isLocked = false;
                individualLock.notify();
            }
        }
    }

    public static void main(String[] args) {
        MyFairLockComplete obj = new MyFairLockComplete();
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
                sleep(2000);
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
                sleep(3000);
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
                sleep(100);
            }
        });
        /*startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });
        startThread(() -> {
            while (true) {
                obj.acquireFairLock();
                print("Start");
                sleep(1000);
                print("End");
                obj.releaseFairLock();
            }
        });*/
    }
}
