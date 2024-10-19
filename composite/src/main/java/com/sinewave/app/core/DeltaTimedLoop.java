package com.sinewave.app.core;

public class DeltaTimedLoop implements Runnable {
    private boolean threaded = false;
    private int targetFps;
    private ExitCondition condition;
    private LoopIteration loop;
    private ExitOperation operation;

    private DeltaTimedLoop() {}
    public static DeltaTimedLoop createLoop() {
        return new DeltaTimedLoop();
    }
    public static DeltaTimedLoop createThreadedLoop() {
        DeltaTimedLoop threadedLoop = new DeltaTimedLoop();
        threadedLoop.threaded = true;
        return threadedLoop;
    }

    public void setExitCondition(ExitCondition exitCondition) {
        condition = exitCondition;
    }
    public void setLoopIteration(LoopIteration loopIteration) {
        loop = loopIteration;
    }
    public void setExitOperation(ExitOperation exitOperation) {
        operation = exitOperation;
    }
    public void setTargetFps(int targetFps) {
        this.targetFps = targetFps;
    }

    public void start() {
        if (threaded) {
            startThrad();
            return;
        }
        long lastTime, currentTime;
        float deltaTime;
        lastTime = System.nanoTime();
        while (!condition.test()) {
            currentTime = System.nanoTime();
            deltaTime = currentTime - lastTime;
            lastTime = currentTime;
            loop.run(deltaTime / (1000000000.0f / targetFps));
        }
        operation.run();
    }

    private Thread loopThread;
    private void startThrad() {
        loopThread = new Thread(this, "loopThread");
        loopThread.start();
    }
    public void run() {
        long lastTime, currentTime;
        float deltaTime;
        lastTime = System.nanoTime();
        while (!condition.test()) {
            currentTime = System.nanoTime();
            deltaTime = currentTime - lastTime;
            lastTime = currentTime;
            loop.run(deltaTime / (1000000000.0f / targetFps));
        }
        operation.run();
    }

    @FunctionalInterface
    public interface ExitCondition {
        public boolean test();
    }
    @FunctionalInterface
    public interface LoopIteration {
        public void run(float deltaTimeMultiplier);
    }
    @FunctionalInterface
    public interface ExitOperation {
        public void run();
    }
}
