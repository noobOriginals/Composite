package app.launcher;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        app.init();
        app.loop();
        app.cleanup();
    }
}