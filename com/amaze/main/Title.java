package com.amaze.main;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds information about Title.
 */
public class Title extends RectangleShape {

    private float xCord;
    private float yCord;

    private Color color;

    private Window window;
    private MenuScene menu;

    private Vector2f position;
    private Vector2f size;

    private Texture icon;


    /**
     * Construct a button with following parameters:
     *
     * @param xCord - x-coordinate of the button
     * @param yCord - y-coordinate of the button
     * @param width - width of the button
     * @param height - height of the button
     * @param window - reference to the main window
     */
    public Title(float xCord, float yCord, float width, float height, Window window, MenuScene menu) throws IOException {


        this.window = window;

        this.xCord = xCord;
        this.yCord = yCord;

        //this.window = window;
        this.menu = menu;

        position = new Vector2f(xCord,yCord);
        size = new Vector2f(width,height);

        this.setSize(size);

        icon = new Texture();
        icon.loadFromFile(Paths.get("res/menuGraphics/logo.png"));
        this.setTexture(icon);
    }
}
