package com.sinewave.app.core.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Layer {
    private BufferedImage image, originalImage;
    private int x, y;
    private boolean visible, rescaled;
    public Layer(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < 1000; y++) {
                for (int x = 0; x < 1000; x++) {
                    image.setRGB(x, y, new Color((x / 2000f) + (y / 2000f), (x / 2000f) + (y / 2000f), (x / 2000f) + (y / 2000f)).getRGB());
                }
            }
            e.printStackTrace();
        }
        originalImage = image;
        setPos(0, 0);
        show();
        originalScale();
    }
    public int getWidth() {
        return image.getWidth();
    }
    public int getHeight() {
        return image.getHeight();
    }
    public void hide() {
        visible = false;
    }
    public void show() {
        visible = true;
    }
    public boolean isVisible() {
        return visible;
    }
    public Layer rescale(float scale) {
        scale(scale);
        rescaled = true;
        return this;
    }
    public void originalScale() {
        image = originalImage;
        rescaled = false;
    }
    public boolean isRescaled() {
        return rescaled;
    }
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }
    public int[] getPos(int[] pos) {
        pos[0] = x;
        pos[1] = y;
        return pos;
    }
    public int[] getRGB(int startx, int starty, int width, int height) {
        return image.getRGB(startx, starty, width, height, null, 0, width);
    }
    @Deprecated
    public void scale(float scale) {
        BufferedImage resized = new BufferedImage((int)(image.getWidth() * scale), (int)(image.getHeight() * scale), image.getType());
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale), 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        image = resized;
    }
    public void drawScaled(Graphics2D g2d, float scale) {
        if (!visible) {
            return;
        }
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, x, y, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale), null);
    }
    public BufferedImage getImage() {
        return image;
    }
    public void draw(Graphics2D g2d) {
        if (!visible) {
            return;
        }
        g2d.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
    }
    public void drawInside(Draw draw) {
        draw.call(image);
    }

    @FunctionalInterface
    public interface Draw {
        public void call(BufferedImage image);
    }
}
