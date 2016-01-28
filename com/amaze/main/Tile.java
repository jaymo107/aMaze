package com.amaze.main;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;
import org.jsfml.system.*;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jsfml.*;

/**
 * Created by Kiran
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
    private Vector2f tileSize;

    public enum BlockType {
        WALL, FLOOR, DOOR, START,
        FINISH,VOID,CHARGE,PATH
    }

    /**
     *
     * @param filePath Path to image
     * @param originX Start X pos
     * @param originY Start Y pos
     * @throws IOException
     */

    public Tile(String filePath, int originX, int originY, int sizeX, int sizeY, BlockType type, Texture[] imageCache) throws IOException{
        this.currentX = originX;
        this.currentY = originY;
        position = new Vector2f(originX,originY);
        tileSize = new Vector2f(sizeX,sizeY);
        Texture tileTexture = new Texture();
        tileTexture.setSmooth(true);

        //Set image according to type
        switch (type){
            case WALL:
                imageCache[0].setSmooth(true);
                tileTexture = imageCache[0];
                break;
            case FLOOR:
                imageCache[1].setSmooth(true);
                tileTexture = imageCache[1];
                break;
            case DOOR:
                imageCache[2].setSmooth(true);
                tileTexture = imageCache[2];
                break;
            case START:
                imageCache[3].setSmooth(true);
                tileTexture = imageCache[3];
                break;
            case FINISH:
                imageCache[4].setSmooth(true);
                tileTexture = imageCache[4];
                break;
            case VOID:
                imageCache[5].setSmooth(true);
                tileTexture = imageCache[5];
                break;
            case CHARGE:
                imageCache[6].setSmooth(true);
                tileTexture = imageCache[6];
                break;
            default:
                System.out.print("Block must have type defined");
                break;
        }

        Vector2i size = tileTexture.getSize();
        this.setTexture(tileTexture);
        this.setSize(tileSize);
        this.setPosition(position);

        //this.setFillColor(Color.YELLOW);
        //this.setOutlineColor(Color.RED);
    }

    public void deltaX(float x){
        this.move(x,0);

    }

    public void deltaY(float y){
        this.move(0,y);

    }

}
