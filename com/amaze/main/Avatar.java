package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.nio.file.Paths;

/**
 * Created by Kiran on 28/01/2016.
 */
public class Avatar extends RectangleShape{
    private int currentX;
    private int currentY;

    public Avatar(int startX, int startY)throws Exception{
        Texture t = new Texture();
        t.loadFromFile(Paths.get("res/face.png"));
        this.setSize(new Vector2f(20,20));
        this.setPosition(startX,startY);
        this.setTexture(t);
    }
}
