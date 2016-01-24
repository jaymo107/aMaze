package com.amaze.main;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

/**
 * This is an abstract class which is responsible for managing all button in the game.
 */
public abstract class Button extends RectangleShape {

    private float xCord;
    private float yCord;

    private Color color;

    private Vector2f position;
    private Vector2f size;


    /**
     * This is temprorary constructor for testing if a button is properly displayed in the window frame.
     */
    public Button(float xCord, float yCord, float width, float height, Color color) {

        this.xCord = xCord;
        this.yCord = yCord;

        this.color = color;

        position = new Vector2f(xCord,yCord);
        size = new Vector2f(width,height);

        this.setSize(size);
        this.setPosition(position);

        this.setFillColor(color);
        this.setOutlineColor(color);

    }
}
