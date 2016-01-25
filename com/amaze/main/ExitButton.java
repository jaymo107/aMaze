package com.amaze.main;

import org.jsfml.graphics.*;

/**
 * This class holds appropriate information about play button.
 */
public class ExitButton extends Button {

    private Window window;

    /**
     * Construct a button with following parameters:
     *
     * @param xCord - x-coordinate of the button
     * @param yCord - y-coordinate of the button
     * @param width - width of the button
     * @param height - height of the button
     * @param color - color of the button
     */
    public ExitButton(float xCord, float yCord, float width, float height, Color color, Window window) {

        super(xCord, yCord, width, height, color, window);

        this.window = window;
    }
    public void closeWindow() {window.close();}
}
