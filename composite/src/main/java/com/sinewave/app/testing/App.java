package com.sinewave.app.testing;

import com.sinewave.app.core.Display;
import com.sinewave.app.core.Layer;
import com.sinewave.app.core.Timer;
import com.sinewave.app.core.Window;

public class App {
    private Window window;
    private Timer timer1;
    public void init() {
        timer1 = new Timer();
        Display.createResizableWindows(true);
        window = Display.createWindow(800, 600, "App");
        Layer l = new Layer("/home/noob/Downloads/Wallpaper.jpg");
        window.addLayer(0, 0, l, false);
    }
    public void loop() {
        timer1.reset();
        while (timer1.getTimeInUnit(Timer.UNIT_SECONDS) < 10) {
            window.drawLayers();
            window.refresh();
        }
        System.exit(0);
    }
}
