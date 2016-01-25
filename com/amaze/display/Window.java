package com.amaze.display;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.w3c.dom.css.Rect;

import com.amaze.entities.Tile;

import javax.swing.border.TitledBorder;

/**
 * This is a class responsible for holding a window for aMaze
 */

public class Window extends RenderWindow{
    private int screenWidth;
    private int screenHeight;
    private Sound sound; //Sound to be played
    private int gridX;
    private int gridY;

    private ArrayList<Tile> drawList; //Array list holding Tiles.

    /**
     *
     * @param resolutionX Width in pixels(physical size of window)
     * @param resolutionY Height in pixels(physical size of window)
     * @param blocksX Size of X grid overlayed onto window
     * @param blocksY Size of y grid overlayed onto window
     *
     * Example:
     *                maze(1920,1080,25,25)
     *                This would create a window of 1920x1080 pixels but with 25 by 25
     *                grid to place objects into a maze TEST
     */

    public Window(int resolutionX, int resolutionY, int blocksX, int blocksY) throws IOException {
        //Instantiate
        this.screenWidth = resolutionX;
        this.screenHeight = resolutionY;
        drawList = new ArrayList<>();

        // Creating a new window
        this.create(new VideoMode(screenWidth, screenHeight), "aMaze", WindowStyle.DEFAULT);
        this.setFramerateLimit(60);

        //Load Sounds
        SoundBuffer soundBuffer = new SoundBuffer();
        soundBuffer.loadFromFile(Paths.get("res/beep.wav"));

        //Create a sound and set its buffer
        sound = new Sound();
        sound.setBuffer(soundBuffer);
    }

    /**
     * Used to start displaying the window. Once this is called nothing can be changed.
     */

    public void displayThis(){
        while (this.isOpen( )) {
            // Clear the screen
            this.clear(Color.BLUE);
            drawItems(this.drawList);

            this.display();

            // the user pressed the close button
            for (Event event : this.pollEvents( )) {
                //Different behaviour depending on
                switch(event.type){
                    case CLOSED:
                        this.close();
                        break;
                    case KEY_PRESSED:
                        if(event.asKeyEvent().key == Keyboard.Key.UP){
                            drawList.get(0).deltaY(-10F);
                        }else if(event.asKeyEvent().key == Keyboard.Key.DOWN){
                            drawList.get(0).deltaY(10F);
                        }else if(event.asKeyEvent().key == Keyboard.Key.LEFT){
                            drawList.get(0).deltaX(-10F);
                        }else if(event.asKeyEvent().key == Keyboard.Key.RIGHT){
                            drawList.get(0).deltaX(10F);
                        }else{
                            sound.play();
                        }
                        break;
                }
            }
        }
    }

    /**
     * Add an item to be display on screen.
     * @param tile Tile that you want to add
     */
    public void addItem(Tile tile,int gridPosX, int gridPosY ){

        drawList.add(tile);
    }

    public int getScreenHeight() {return screenHeight;}

    public int getScreenWidth() {return  screenWidth;}

    /**
     * Draw all items in list
     * @param list List of tiles you want to draw
     */

    private void drawItems(ArrayList<Tile> list){
        for(Tile tile : list){
            this.draw(tile);
        }
    }

    public RenderWindow getWindow(){
        return this;
    }

    /**
     * Translates a grid positon into raw resolution for X axis
     * @return Integer on X value
     */

    private int translateX(){
        return 1;
    }

    /**
     * Translate a grid position into raw resolution for Y axis
     * @return
     */
    private int translateY(){
        return 1;
    }

}
