package com.amaze.entities;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;

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
    private Vector2f size;

    public enum BlockType {
        WALL, FLOOR, DOOR, START, FINISH
    }

    /**
     *
     * @param filePath Path to image
     * @param originX Start X pos
     * @param originY Start Y pos
     * @throws IOException
     */

    public Tile(String filePath, int originX, int originY, int sizeX, int sizeY, BlockType type) throws IOException{
        this.currentX = originX;
        this.currentY = originY;
        position = new Vector2f(originX,originY);
        size = new Vector2f(sizeX,sizeY);
        Texture tileTexture = new Texture();
        tileTexture.setSmooth(true);

        //Set image according to type
        switch (type){
            case WALL:
                tileTexture.loadFromFile(Paths.get("res/wall.png"));
                break;
            case FLOOR:
                tileTexture.loadFromFile(Paths.get("res/face.png"));
                break;
            case DOOR:
                break;
            case START:
                break;
            case FINISH:
                break;
            default:
                System.out.print("Block must have type defined");
                break;
        }

        Vector2i size = tileTexture.getSize();
        this.setTexture(tileTexture);
        this.setSize(new Vector2f(size.x,size.y));
        this.setPosition(position);

        this.setFillColor(Color.YELLOW);
        this.setOutlineColor(Color.RED);
    }

    public void deltaX(float x){
        this.move(x,0);
    }

    public void deltaY(float y){
        this.move(0,y);
    }

}
