package com.amaze.main;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.w3c.dom.css.Rect;

import javax.swing.border.TitledBorder;

/**
 * This is a class responsible for holding a window for aMaze
 */

public class Window extends RenderWindow{
    private int screenWidth;
    private int screenHeight;

    /**
     *
     * @param screenWidth Width in pixels(physical size of window)
     * @param screenHeight Height in pixels(physical size of window)
     * @param actualX Size of X grid overlayed onto window
     * @param actualY Size of y grid overlayed onto window
     *
     * Example:
     *                maze(1920,1080,25,25)
     *                This would create a window of 1920x1080 pixels but with 25 by 25
     *                grid to place objects into a maze
     */


    Vector2f vector = new Vector2f(20,20);
    RectangleShape shape = new RectangleShape(vector);

    private ArrayList<Tile> drawList; //Array list holding Tiles.

    public Window(int screenWidth, int screenHeight, int actualX, int actualY) throws IOException{
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        drawList = new ArrayList<Tile>();

        Tile t1 = new Tile("",10,10);
        Tile t2 = new Tile("",20,10);
        Tile t3 = new Tile("",50,10);
        drawList.add(t1);
        drawList.add(t2);
        drawList.add(t3);

        // Creating a new window
        this.create(new VideoMode(screenWidth, screenHeight), "aMaze", WindowStyle.DEFAULT);
        this.setFramerateLimit(30);

        while (this.isOpen( )) {
            // Clear the screen
            this.clear(Color.BLUE);

            drawItems(this.drawList);

            this.display();

            // the user pressed the close button
            for (Event event : this.pollEvents( )) {
                if (event.type == Event.Type.CLOSED) {
                    this.close( );
                }
            }
        }
    }

    public void addItem(Tile tile){
        drawList.add(tile);
    }

    public int getScreenHeight() {return screenHeight;}
    public int getScreenWidth() {return  screenWidth;}
    private void drawItems(ArrayList<Tile> list){
        for(int i = 0; i < list.size() ; i++){
            this.draw(list.get(i));
        }
    }
}
