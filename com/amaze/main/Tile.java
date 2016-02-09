package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;

/**
 * Represents a Tile and displays depending on type
 */

public class Tile extends RectangleShape{
    private int currentX;
    private int currentY;
    private Vector2f position;
    private Vector2f tileSize;
    private BlockType blockType;

    public enum BlockType {
        WALL, FLOOR, DOOR, START, FINISH, VOID, CHARGE
    }

    /**
     *
     * @param filePath Path to image
     * @param originX Start X pos
     * @param originY Start Y pos
     * @throws IOException
     */

    public Tile(String filePath, int originX, int originY, int sizeX, int sizeY, BlockType type, Texture[] imageCache){
        this.currentX = originX;
        this.currentY = originY;
        this.position = new Vector2f(originX,originY);
        this.tileSize = new Vector2f(sizeX,sizeY);
        Texture tileTexture = new Texture();
        tileTexture.setSmooth(true);

        //Set image according to type
        switch (type){
            case WALL:
                blockType = BlockType.WALL;
                imageCache[0].setSmooth(true);
                tileTexture = imageCache[0];
                break;
            case FLOOR:
                blockType = BlockType.FLOOR;
                imageCache[1].setSmooth(true);
                tileTexture = imageCache[1];
                break;
            case DOOR:
                blockType = BlockType.DOOR;
                imageCache[2].setSmooth(true);
                tileTexture = imageCache[2];
                break;
            case START:
                blockType = BlockType.START;
                imageCache[3].setSmooth(true);
                tileTexture = imageCache[3];
                break;
            case FINISH:
                blockType = BlockType.FINISH;
                imageCache[4].setSmooth(true);
                tileTexture = imageCache[4];
                break;
            case VOID:
                blockType = BlockType.VOID;
                imageCache[5].setSmooth(true);
                tileTexture = imageCache[5];
                break;
            case CHARGE:
                blockType = BlockType.CHARGE;
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

    public BlockType getTileType() {
        return blockType;
    }

}
