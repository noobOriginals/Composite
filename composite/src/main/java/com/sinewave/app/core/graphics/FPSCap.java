package com.sinewave.app.core.graphics;

public class FPSCap {
    private long lastTime = 0, currentTime = 0, deltaTime = 0, sleepTime = 0, targetLoopTime = 0;

    public FPSCap(int targetFPS) {
        targetLoopTime = (long)(1000000000.0f / targetFPS);
    }

    public void run() {
        currentTime = System.nanoTime();
        deltaTime = currentTime - lastTime;
        sleepTime = (deltaTime < targetLoopTime)? targetLoopTime - deltaTime : 0;
        try {
            Thread.sleep(sleepTime / Timer.UNIT_MILLIS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lastTime = System.nanoTime();
    }
}
