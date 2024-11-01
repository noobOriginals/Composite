package com.sinewave.app.launcher;

import com.sinewave.app.core.graphics.Display;
import com.sinewave.app.core.graphics.Layer;
import com.sinewave.app.core.graphics.Window;

public class App2 {
    private Window window;
    public void init() {
        window = Display.createWindow(800, 600, "Window");
        window.showFPS();
        window.addLayer(0, 0, new Layer("C:/Users/ssapho/").rescale(0.5f), false);
    }
    public void loop() {

    }
    public void cleanup() {

    }
}
