package com.amaze.main;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Kiran on 22/01/2016.
 */
public class Tile extends RectangleShape{
    /*
    @todo:
        - Collision detection
        - Image handler
        - Current location
        - Physical Size e.g. 10x10px NOT HOW MANY BLOCKS IT TAKES
     */

    Image icon; //Icon shown on the block
    private int currentX;
    private int currentY;
    private Vector2f position;
    private Vector2f size;

    /**
     *
     * @param filePath Path to image
     * @param originX Start X pos
     * @param originY Start Y pos
     * @throws IOException
     */
    public Tile(String filePath, int originX, int originY) throws IOException{
        //Path imagePath = Paths.get(URI.create(filePath));
        //icon.loadFromFile(imagePath);
        this.currentX = originX;
        this.currentY = originY;
        position = new Vector2f(originX,originY);
        size = new Vector2f(20,20);

        this.setSize(size);

        this.setPosition(position);


        this.setFillColor(Color.YELLOW);
        this.setOutlineColor(Color.RED);

    }

}
