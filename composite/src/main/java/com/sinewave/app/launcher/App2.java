package com.sinewave.app.launcher;

import java.util.ArrayList;

import com.sinewave.app.core.graphics.Display;
import com.sinewave.app.core.graphics.FPSCap;
import com.sinewave.app.core.graphics.Layer;
import com.sinewave.app.core.graphics.Timer;
import com.sinewave.app.core.graphics.Window;
import com.sinewave.app.core.io.File;

public class App2 {
    private Window window;
    private Timer timer;
    private ArrayList<Layer> layers = new ArrayList<>();
    private FPSCap capper;
    public void init() {
        Display.createResizableWindows(false);
        window = Display.createWindow(800, 600, "Window");
        loadLayers("Layers.txt");
        window.drawLayers();
        window.refresh();
        capper = new FPSCap(240);
        timer = new Timer();
    }
    public void loop() {
        File file = new File("Commands.txt");
        String line = file.readLine();
        while (line != null) {
            processCommand(line);
            line = file.readLine();
        }
        timer.wait(10, Timer.UNIT_SECONDS);
    }
    public void cleanup() {
        window.close();
        System.exit(0);
    }

    private void loadLayers(String filePath) {
        File file = new File(filePath);
        String line = file.readLine();
        while (line != null) {
            layers.add(new Layer(line));
            window.addLayer(0, 0, layers.get(layers.size() - 1), true);
            line = file.readLine();
        }
    }

    private void processCommand(String command) {
        switch (command) {
            case "moveUp()":
                movePlayer(0, -1, 0);
                return;
            case "moveDown()":
                movePlayer(0, 1, 0);
                return;
            case "moveLeft()":
                movePlayer(-1, 0, 0);
                return;
            case "moveRight()":
                movePlayer(1, 0, 0);
                return;
        }
    }

    int totalSteps = 100, animationSpeed = 2, animationSteps = 1;
    private void movePlayer(int x, int y, int speed) {
        int player = layers.size() - 1;
        for (int i = 0; i < totalSteps / animationSteps; i++) {
            layers.get(player).move(x * animationSteps, y * animationSteps);
            window.clear();
            window.drawLayers();
            window.refresh();
            timer.wait((int)((20 * animationSteps) / (float)animationSpeed), Timer.UNIT_MILLIS);
            capper.run();
        }
    }
}
