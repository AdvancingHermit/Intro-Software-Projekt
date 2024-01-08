package com.snake.game;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import com.snake.game.SnakeProjekt;
import com.snake.game.util.Data;
import com.badlogic.gdx.graphics.glutils.HdpiMode;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        DisplayMode desktopMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        config.setFullscreenMode(desktopMode);

        //config.setWindowedMode(600, 600);

        config.setHdpiMode(HdpiMode.Pixels);
        config.setForegroundFPS(30);
        config.setTitle("IntroSnakeProjekt");
        new Lwjgl3Application(new SnakeProjekt(), config);
    }
}