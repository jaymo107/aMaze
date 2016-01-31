package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class is responsible for manging background image for Menu Scene
 */
public class Background extends RectangleShape {

    private Vector2f size;
    private Texture icon;


    /**
     * Construct a button with following parameters:
     *
     * @param width - width of the background
     * @param height - height of the background
     */
    public Background(float width, float height) throws IOException {

        size = new Vector2f(width,height);

        this.setSize(size);

        icon = new Texture();
        icon.loadFromFile(Paths.get("res/menuGraphics/maze.png"));
        this.setTexture(icon);
    }
}

