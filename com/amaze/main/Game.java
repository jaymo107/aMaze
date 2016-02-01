package com.amaze.main;

import java.io.IOException;
import com.amaze.main.Tile;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;


/**
 * Created by Kiran on 25/01/2016.
 * Represents a game
 */
public class Game extends Scene {

    int blockSize; //Size of each block. W and H
    int blockX;
    int blockY;
    Tile tileMap[][];

    public Game(int resolutionX, int resolutionY, int blocksX, int blocksY, int blockSize, Tile.BlockType[][] level, Window wnd) throws IOException{
        //super(resolutionX,resolutionY,blocksX,blocksY);
        super("hi",wnd);
        this.blockSize = blockSize;

        blockX = level.length;
        blockY = blocksX;

        tileMap = new Tile[blocksX][blocksY];

        for(int j = 0; j < blocksY; j++){
            for(int i = 0; i < blocksX; i++){
                //tileMap[i][j] = new Tile("",translateX(i),translateY(j),this.blockSize,this.blockSize,level[i][j]);
                //this.addScene(tileMap[i][j]);
                //wnd.draw(tileMap[i][j]);
            }
        }
    }

    /**(
     * Translates X
     * @param blockX
     * @return
     */

    public int translateX(int blockX){
        return blockSize * blockX;
    }

    /**
     * Translates Y to raw
     * @param blockY
     * @return
     */

    public int translateY(int blockY){
        return blockSize * blockY;
    }

    /**
     * Draws items associated with MenuScene in the main Window.
     */
    public void drawMenuItems() {
        for(int j = 0; j < blockX; j++){
            for(int i = 0; i < blockY; i++){
                getWindow().draw(tileMap[i][j]);
                //this.addScene(tileMap[i][j]);
                //wnd.draw(tileMap[i][j]);
            }
        }
    }

    public void display(RenderWindow window){
        setRunning(true);
        getWindow().setTitle(getSceneTitle());
        while(this.isRunning()) try {

            window.clear(Color.WHITE);
            drawMenuItems();

            for (Event event : window.pollEvents()) {

                //Different behaviour depending on
                this.executeEvent(event);
            }
            window.display();

        }catch (Exception e) {
            this.setRunning(false);
        }
    }

    public void executeEvent(Event event) {

    }

}
