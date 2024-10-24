package com.sinewave.app.testing;

import com.sinewave.app.core.DeltaTimedLoop;
import com.sinewave.app.core.Display;
import com.sinewave.app.core.Layer;
import com.sinewave.app.core.Window;

public class App {
    private Window window;
    private Layer layer;
    private DeltaTimedLoop loop;
    public void init() {
        Display.createResizableWindows(true);
        window = Display.createWindow(800, 600, "App");
        layer = new Layer("/home/noob/Downloads/Wallpaper.jpg");
        window.addLayer(0, 0, layer, true);
        loop = DeltaTimedLoop.createLoop();
        loop.setExitCondition(this::exitCondition);
        loop.setLoopIteration(this::loopIteration);
        loop.setExitOperation(this::exitOperation);
        loop.setTargetFps(240);
        loop.capFrames(true);
        window.showFPS();
    }
    public boolean exitCondition() {
        return (window.keyPressed('x') || window.shouldClose());
    }
    public void loopIteration(float deltaTimeMultiplier) {
        window.clear();
        window.drawLayers();
        window.refresh();
        if (window.keyPressed('w')) {
            layer.move(0, (int)(-3 * deltaTimeMultiplier));
        }
        if (window.keyPressed('s')) {
            layer.move(0, (int)(3 * deltaTimeMultiplier));
        }
        if (window.keyPressed('a')) {
            layer.move((int)(-3 * deltaTimeMultiplier), 0);
        }
        if (window.keyPressed('d')) {
            layer.move((int)(3 * deltaTimeMultiplier), 0);
        }
    }
    public void exitOperation() {
        window.close();
        System.exit(0);
    }
    public void loop() {
        loop.start();
    }
}
