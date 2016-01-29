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
 * Represents a Tile and displays depending on type
 */

public class Tile extends RectangleShape{
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
        this.position = new Vector2f(originX,originY);
        this.tileSize = new Vector2f(sizeX,sizeY);
        Texture tileTexture = new Texture();
        tileTexture.setSmooth(true);

        //Set image according to type
        try{
          switch (type){
              case WALL:
                  tileTexture.loadFromFile(Paths.get("res/images/wall.png"));
                  break;
              case FLOOR:
                  tileTexture.loadFromFile(Paths.get("res/images/floor.png"));
                  break;
              case DOOR:
                  tileTexture.loadFromFile(Paths.get("res/images/door.png"));
                  break;
              case START:
                  tileTexture.loadFromFile(Paths.get("res/images/blue.png"));
                  break;
              case FINISH:
                  tileTexture.loadFromFile(Paths.get("res/images/blue.png"));
                  break;
              case VOID:
                  tileTexture.loadFromFile(Paths.get("res/images/void.png"));
                  break;
              case CHARGE:
                  tileTexture.loadFromFile(Paths.get("res/images/charge.png"));
                  break;
              default:
                  System.out.print("Block must have type defined");
                  break;
          }
        }catch(IOException exception){
          System.out.print("Couldn't load from file.");

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
