package main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.KurtMarioGame;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("KURT MARIO!!");
        cfg.setWindowedMode(480, 320);
        cfg.setWindowIcon(FilePaths.DEFAULT_SPRITE);
        cfg.setForegroundFPS(60);

        new Lwjgl3Application(new KurtMarioGame(), cfg);
    }
}