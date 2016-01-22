package com.amaze.main;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.RectangleShape;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Kiran on 22/01/2016.
 */
public abstract class Tile extends RectangleShape{
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

    /**
     *
     * @param filePath Path to image
     * @param originX Start X pos
     * @param originY Start Y pos
     * @throws IOException
     */
    public Tile(String filePath, int originX, int originY) throws IOException{
        Path imagePath = Paths.get(URI.create(filePath));
        icon.loadFromFile(imagePath);


    }

    private void loadImage(){

    }

}
