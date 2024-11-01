package com.sinewave.app.core.graphics;

import java.util.HashMap;

public class Display {
    private static HashMap<String, Window> windows = new HashMap<>();
    private static boolean resizableWindows = false;

    public static Window createWindow(int width, int height, String title) {
        Window w = new Window(width, height, title, resizableWindows);
        windows.put(title, w);
        return w;
    }
    public static void createResizableWindows(boolean resizable) {
        resizableWindows = resizable;
    }
    public static Window getWindow(String title) {
        return windows.get(title);
    }
}
