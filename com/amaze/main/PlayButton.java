package com.amaze.main;

import org.jsfml.graphics.*;
import org.jsfml.window.ContextActivationException;

import java.io.IOException;

/**
 * This class holds appropriate information about play button.
 */
public class PlayButton extends Button {

    private Window window;
    private MenuScene menu;

    /**
     * Construct a button with following parameters:
     *
     * @param xCord - x-coordinate of the button
     * @param yCord - y-coordinate of the button
     * @param width - width of the button
     * @param height - height of the button
     * @param color - color of the button
     * @param window - reference to the main window
     */
    public PlayButton(float xCord, float yCord, float width, float height, Color color, Window window, MenuScene menu) {

        super(xCord, yCord, width, height, color, window, menu);

        this.window = window;
        this.menu = menu;
    }

    /**
     * This function changes Scenes when called.
     */
    public void startNewGame() {

        window.setScene(1);
        menu.setRunning(false);
    }
}
