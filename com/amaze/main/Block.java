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
public abstract class Block extends RectangleShape{
    /*
    @todo:
        - Collision detection
        - Image handler
        - Current location
        - Physical Size e.g. 10x10px NOT HOW MANY BLOCKS IT TAKES
     */

    Image icon; //Icon shown on the block

    public Block(String filePath) throws IOException{
        Path imagePath = Paths.get(URI.create(filePath));
        icon.loadFromFile(imagePath);
    }

    private void loadImage(){

    }

}
