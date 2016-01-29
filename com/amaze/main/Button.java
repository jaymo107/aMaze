package com.amaze.main;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

/**
 * This is an abstract class which is responsible for managing all button in the menu.
 */
public abstract class Button extends RectangleShape {

    private float xCord;
    private float yCord;

    private Color color;

    private Window window;
    private MenuScene menu;

    private Vector2f position;
    private Vector2f size;

    private boolean selected;

    /**
     * Construct a button with following parameters:
     *
     * @param xCord - x-coordinate of the button
     * @param yCord - y-coordinate of the button
     * @param width - width of the button
     * @param height - height of the button
     * @param color - color of the button
     */
    public Button(float xCord, float yCord, float width, float height, Color color, Window window, MenuScene menu) {

        this.xCord = xCord;
        this.yCord = yCord;

        this.color = color;

        //this.window = window;
        this.menu = menu;

        position = new Vector2f(xCord,yCord);
        size = new Vector2f(width,height);

        this.setSize(size);
        this.setPosition(position);

        this.setFillColor(color);
        //this.setOutlineColor(Color.YELLOW);

        selected = false;
    }

    /**
     * Confirms if current button is selected.
     * @return - true/false based on the result.
     */
    public boolean isSelected() {return selected;}

    /**
     * Change state of the button
     * @param selected - Whether it is selected/not selected.
     */
    public void setSelected(boolean selected) {this.selected = selected;}

    /**
     * Change color of the button
     * @param color - new color.
     */
    public void setColor(Color color) {this.setFillColor(color);}
}
