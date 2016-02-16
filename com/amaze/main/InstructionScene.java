package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by MatthewFrost on 16/02/16.
 */
public class InstructionScene extends RectangleShape
{
    public InstructionScene(float width, float height) throws IOException
    {
        Vector2f size = new Vector2f(width, height);
        Texture icon = new Texture();
        icon.loadFromFile(Paths.get("res/menuGraphics/instructions.png"));
        this.setTexture(icon);
        icon.setSmooth(true);
        this.setSize(size);
    }
}
