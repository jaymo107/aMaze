package com.amaze.main;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

/**
 * This is an abstract class which is responsible for managing all button in the game.
 */
public abstract class Button extends RectangleShape {

    private float xCoord;
    private float yCoord;
//    private float width;
//    private float height;

    private Color color;

    private Window window;

    private Vector2f position;
    private Vector2f size;


    /**
     * This is temprorary constructor for testing if a button is properly displayed in the window frame.
     */
    public Button(float xCoord, float yCoord, float width, float height, Window window, Color color) {

        this.xCoord = xCoord;
        this.yCoord = yCoord;
//        this.width = width;
//        this.height = height;
        this.window = window;
        this.color = color;

        position = new Vector2f(xCoord,yCoord);
        size = new Vector2f(width,height);

        this.setSize(size);
        this.setPosition(position);

        this.setFillColor(color);

    }

    public double getXCoord() {
        return xCoord;
    }
    public double getYCoord() {
        return yCoord;
    }
}
