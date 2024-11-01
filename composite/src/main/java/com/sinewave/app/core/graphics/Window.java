package com.sinewave.app.core.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window {
    private JFrame window;
    private BufferedImage image;
    private ImageIcon imageIcon;
    private Keys keys;
    private int[] pixels;
    private ArrayList<Layer> layers = new ArrayList<>();
    Window(int width, int height, String title, boolean resizable) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        keys = new Keys();
        imageIcon = new ImageIcon(image);
        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(resizable);
        window.add(new JLabel(imageIcon));
        window.addKeyListener(keys);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public void draw(boolean refresh, Draw draw) {
        Graphics2D g2d = image.createGraphics();
        draw.call(g2d);
        g2d.dispose();
        if (refresh) {
            refresh();
        }
    }
    public void clear() {
        image = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_ARGB);
        imageIcon.setImage(image);
    }
    public void drawLayers() {
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        for (Layer layer : layers) {
            if (!layer.isVisible()) {
                continue;
            }
            layer.draw(g2d);
        }
        g2d.dispose();
    }
    private int frames = 0, FPS = 0;
    private long lastTime = System.nanoTime();
    public void refresh() {
        if (showFPS) {
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("FPS: " + FPS, 20, 40);
            g2d.dispose();
        }
        if (System.nanoTime() - lastTime > 1000000000) {
            FPS = frames;
            frames = 0;
            lastTime = System.nanoTime();
        }
        // window.invalidate();
        // window.revalidate();
        window.repaint();
        frames++;
    }
    public void addLayer(int x, int y, Layer layer, boolean rescale) {
        layer.setPos(x, y);
        if (rescale) {
            float scale;
            if (Math.abs(image.getWidth() - layer.getWidth()) > Math.abs(image.getHeight() - layer.getHeight())) {
                scale = (float) image.getWidth() / layer.getWidth();
            } else {
                scale = (float) image.getHeight() / layer.getHeight();
            }
            layer.rescale(scale);
        }
        layers.add(layer);
    }
    public void addLayerAndRefresh(int xpos, int ypos, Layer layer, boolean rescale) {
        if (rescale) {
            layer.setPos(xpos, ypos);
            float scale;
            if (Math.abs(image.getWidth() - layer.getWidth()) > Math.abs(image.getHeight() - layer.getHeight())) {
                scale = (float) image.getWidth() / layer.getWidth();
            } else {
                scale = (float) image.getHeight() / layer.getHeight();
            }
            // layer.scale(scale);
            // int startx = (xpos < 0) ? 0 : xpos;
            // int starty = (ypos < 0) ? 0 : ypos;
            // int width = layer.getWidth() - Math.abs(xpos);
            // int height = layer.getHeight() - Math.abs(ypos);
            // image.setRGB(startx, starty, width, height, layer.getRGB((xpos < 0) ? -xpos : 0, (ypos < 0) ? -ypos : 0, width, height), 0, width);
            Graphics2D g2 = image.createGraphics();
            // g2.drawImage(layer.getImage(), xpos, ypos, null);
            layer.drawScaled(g2, scale);
            g2.dispose();
            refresh();
        } else {
            layer.setPos(xpos, ypos);
            Graphics2D g2 = image.createGraphics();
            // g2.drawImage(layer.getImage(), xpos, ypos, null);
            layer.draw(g2);
            g2.dispose();
            refresh();
        }
    }
    public boolean keyPressed(int key) {
        return keys.keyPressed(key);
    }
    public boolean keyPressed(char key) {
        return keys.keyPressed(key);
    }
    public boolean shouldClose() {
        if (window.isDisplayable()) {
            return false;
        } else {
            return true;
        }
    }
    private boolean showFPS = false;
    public void showFPS() {
        showFPS = true;
    }
    public void hideFPS() {
        showFPS = false;
    }
    public void close() {
        window.dispose();
    }
    public int getWidth() {
        return image.getWidth();
    }
    public int getHeight() {
        return image.getHeight();
    }
    BufferedImage getImage() {
        return image;
    }
    @Deprecated
    public void updatePixels(UpdatePixels update) {
        pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        update.call(pixels);
        image.setRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
    }
    @Deprecated
    public void updateImage(UpdateImage update) {
        update.call(image);
    }
    @FunctionalInterface
    public interface UpdatePixels {
        public void call(int[] pixels);
    }
    @FunctionalInterface
    public interface UpdateImage {
        public void call(BufferedImage image);
    }
    @FunctionalInterface
    public interface Draw {
        public void call(Graphics2D g2d);
    }

    private class Keys implements KeyListener {
        private HashMap<String, Boolean> keysByChar = new HashMap<>();
        private HashMap<Integer, Boolean> keysByCode = new HashMap<>();

        @Override
        public void keyPressed(KeyEvent e) {
            keysByChar.put((e.getKeyChar() + "").toLowerCase(), true);
            keysByCode.put(e.getKeyCode(), true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keysByChar.put((e.getKeyChar() + "").toLowerCase(), false);
            keysByCode.put(e.getKeyCode(), false);
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        public boolean keyPressed(char key) {
            if (!keysByChar.containsKey((key + "").toLowerCase())) {
                return false;
            }
            return keysByChar.get((key + "").toLowerCase());
        }
        public boolean keyPressed(int key) {
            if (!keysByCode.containsKey(key)) {
                return false;
            }
            return keysByCode.get(key);
        }
    }
}
