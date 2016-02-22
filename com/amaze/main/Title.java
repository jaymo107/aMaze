package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds information about Title.
 */
public class Title extends RectangleShape {

	/**
     * Construct a button with following parameters:
     *
     * @param width - width of the title
     * @param height - height of the title
     */
    public Title(float xCord, float yCord, float width, float height) throws IOException {
		Vector2f size = new Vector2f(width, height);
		Vector2f position = new Vector2f(xCord, yCord);

        this.setSize(size);
        this.setPosition(position);

		Texture icon = new Texture();
        icon.loadFromFile(Paths.get("res/menuGraphics/logo.png"));
        icon.setSmooth(true);
        this.setTexture(icon);
    }

}
