package com.sinewave.app.testing;

import com.sinewave.app.core.Display;
import com.sinewave.app.core.Layer;
import com.sinewave.app.core.Window;

public class App {
    private Window window;
    public void init() {
        Display.createResizableWindows(true);
        window = Display.createWindow(800, 600, "App");
        Layer l = new Layer("/home/noob/Downloads/Wallpaper.jpg");
        window.addLayer(0, 0, l, true);
    }
    public void loop() {
        while (!window.keyPressed('x') && !window.shouldClose()) {
            window.drawLayers();
            window.refresh();
        }
        System.exit(0);
    }
}
