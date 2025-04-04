package app.launcher;

import app.core.graphics.DeltaTimedLoop;
import app.core.graphics.Display;
import app.core.graphics.Layer;
import app.core.graphics.Window;

public class App {
    private Window window;
    private Layer layer;
    private DeltaTimedLoop loop;
    public void init() {
        Display.createResizableWindows(true);
        window = Display.createWindow(800, 600, "App");
        layer = new Layer("C:/Users/noob/Downloads/pto.jpeg");
        window.addLayer(0, 0, layer, true);
        loop = DeltaTimedLoop.createLoop();
        loop.setExitCondition(this::exitCondition);
        loop.setLoopIteration(this::loopIteration);
        loop.setExitOperation(this::exitOperation);
        loop.setTargetFps(240);
        loop.capFrames(false);
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
    public void cleanup() {
        window.close();
        System.exit(0);
    }
}
