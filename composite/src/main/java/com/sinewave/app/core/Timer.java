package com.sinewave.app.core;

public class Timer implements Runnable {
    // Units
    public static int UNIT_NANOS = 1, UNIT_MILLIS = 1000000, UNIT_SECONDS = UNIT_MILLIS * 1000, UNIT_MINUTES = UNIT_SECONDS * 60, UNIT_HOURS = UNIT_MINUTES * 60;

    private long startTime;
    public Timer() {
        startTime = System.nanoTime();
    }

    public void reset() {
        startTime = System.nanoTime();
    }
    public long getTime() {
        return System.nanoTime() - startTime;
    }
    public float getTimeInUnit(int unit) {
        if (unit < UNIT_MILLIS) {
            try {
                throw new Exception("Unit is too small!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0.0f;
        }
        return getTime() / unit;
    }
    public void wait(int time, int unit) {
        long startTime = System.nanoTime();
        while ((System.nanoTime() - startTime) < time * unit) {}
    }
    public void waitUntil(ConditionCheck conditionCheck) {
        while (!conditionCheck.call()) {}
    }

    public void waitThenRunSeparately(int time, int unit, Action action) {
        threadedWaitTime = time;
        threadedTimeUnit = unit;
        threadedAction = action;
        threadTask = "waitAndRun";
        startThread();
    }
    public void waitUntilThenRunSeparately(ConditionCheck conditionCheck, Action action) {
        threadedConditionCheck = conditionCheck;
        threadedAction = action;
        threadTask = "waitUntilAndRun";
        startThread();
    }
    
    // Threading
    private String threadTask;
    private ConditionCheck threadedConditionCheck;
    private Action threadedAction;
    private int threadedWaitTime;
    private int threadedTimeUnit;
    private Thread timerThread; 
    private void startThread() {
        timerThread = new Thread(this, "timerThread");
        timerThread.start();
    }
    public void run() {
        switch (threadTask) {
            case "waitAndRun":
                long startTime = System.nanoTime();
                while ((System.nanoTime() - startTime) < threadedWaitTime * threadedTimeUnit) {}    
                threadedAction.call();
                return;
            case "waitUntilAndRun":
                while (!threadedConditionCheck.call()) {}
                threadedAction.call();
                return;
            
            default:
                return;
        }
    }

    @FunctionalInterface
    public interface Action {
        public void call();
    }

    public interface ConditionCheck {
        public boolean call();
    }
}
