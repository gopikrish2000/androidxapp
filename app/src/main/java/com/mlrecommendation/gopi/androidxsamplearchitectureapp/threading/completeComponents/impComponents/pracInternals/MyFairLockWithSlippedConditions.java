package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.pracInternals;

import java.util.LinkedList;

import static com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.*;

public class MyFairLockWithSlippedConditions {
    private final Object lock = new Object();
    private final Object lockOther = new Object();
    private LinkedList<IndividualLockNode> individualLockNodes = new LinkedList<>();
    private boolean isLocked = false;


    public void acquireFairLock() {
        IndividualLockNode individualLockNode;
        synchronized (lock) {
            individualLockNode = new IndividualLockNode();
            individualLockNodes.add(individualLockNode);
        }
        while (isLocked) {
            individualLockNode.acquire();
        }

        synchronized (lock) {
            individualLockNodes.remove(individualLockNode);
            isLocked = true;
        }
    }


    public void releaseFairLock() {
        synchronized (lock) {
            isLocked = false;
            if ((individualLockNodes.getFirst() != null)) {
                individualLockNodes.removeFirst().release();
            } else {
                lock.notifyAll();
            }
        }
    }

    private static class IndividualLockNode {
        final Object individualLock = new Object();
        volatile boolean isLocked = false;

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
        MyFairLockWithSlippedConditions obj = new MyFairLockWithSlippedConditions();
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
    }
}
