package com.amaze.main;

import org.jsfml.graphics.*;

/**
 * This is an abstract class which is responsible for managing all button in the game.
 */
public abstract class Button {

    private double xCoord;
    private double yCoord;
    private Color color;
    private Window window;
    private Image image;

    /**
     * This constructor creates a button given 3 parameters
     *
     * @param xCoord - width of the button.
     * @param yCoord - heigth of the button.
     * @param image  - image which goes inside the button.
     */

    public Button(double xCoord, double yCoord, Window window, Image image) {

        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.window = window;
        this.image = image;
    }

    /**
     * This is temprorary constructor for testing if a button is properly displayed in the window frame.
     */
    public Button(double xCoord, double yCoord, Window window, Color color) {

        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.window = window;
        this.color = color;
    }

    public double getWidth() {
        return xCoord;
    }

    public double getHeight() {
        return yCoord;
    }

    public Color getColor() {
        return color;
    }
}
