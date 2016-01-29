package com.amaze.main;

import org.jsfml.graphics.*;
import org.jsfml.window.ContextActivationException;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds appropriate information about play button.
 */
public class PlayButton extends Button {

    private Window window;
    private MenuScene menu;
    private Texture defaultIcon;
    private Texture selectedIcon;

    /**
     * Construct a button with following parameters:
     *
     * @param xCord - x-coordinate of the button
     * @param yCord - y-coordinate of the button
     * @param width - width of the button
     * @param height - height of the button
     * @param window - reference to the main window
     */
    public PlayButton(float xCord, float yCord, float width, float height, Window window, MenuScene menu) throws IOException {

        super(xCord, yCord, width, height, window, menu);

        this.window = window;
        this.menu = menu;

        defaultIcon = new Texture();
        defaultIcon.loadFromFile(Paths.get("res/menuGraphics/play.png"));
        this.setTexture(defaultIcon);

        selectedIcon = new Texture();
        selectedIcon.loadFromFile(Paths.get("res/menuGraphics/playsel.png"));
    }

    /**
     * This function changes Scenes when called.
     */
    public void startNewGame() {

        window.setScene(1);
        menu.setRunning(false);

    }
    public Texture getDefaultIcon() {return defaultIcon;}
    public Texture getSelectedIcon() {return selectedIcon;}
    public void setIcon(Texture t) {

        setTexture(t);
    }
}
