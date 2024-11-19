package com.sinewave.app.launcher;

import java.util.ArrayList;

import com.sinewave.app.core.graphics.Display;
import com.sinewave.app.core.graphics.FPSCap;
import com.sinewave.app.core.graphics.Layer;
import com.sinewave.app.core.graphics.Window;
import com.sinewave.app.core.io.File;

public class App2 {
    private Window window;
    private ArrayList<Layer> layers = new ArrayList<>();
    private FPSCap capper;
    public void init() {
        Display.createResizableWindows(true);
        window = Display.createWindow(800, 600, "Window");
        window.showFPS();
        loadLayers("Layers.txt");
        window.drawLayers();
        window.refresh();
        capper = new FPSCap(240);
    }
    public void loop() {
        while (!window.shouldClose()) {
            window.clear();
            window.drawLayers();
            window.refresh();
            if (window.keyPressed('e')) {
                layers.get(0).rescale(0.1f);
            }
            capper.run();
        }
    }
    public void cleanup() {

    }

    private void loadLayers(String filePath) {
        File file = new File(filePath);
        String line = file.readLine();
        while (line != null) {
            layers.add(new Layer(line));
            window.addLayer(0, 0, layers.get(layers.size() - 1), false);
            line = file.readLine();
        }
    }
}
