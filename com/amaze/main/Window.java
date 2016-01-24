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

    //private ArrayList<Menu> drawList; //Array list holding Tiles.
    private Menu mainMenu;

    public Window(int screenWidth, int screenHeight, int actualX, int actualY) throws IOException {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Creating a new window
        this.create(new VideoMode(screenWidth, screenHeight), "aMaze", WindowStyle.DEFAULT);
        this.setFramerateLimit(30);

        GUILogic l = new GUILogic(this);
        Thread t = new Thread(l);
        t.start();

        while (this.isOpen( )) {
            // Clear the screen
            this.clear(Color.WHITE);

            drawMenu(mainMenu);

            this.display();

            // the user pressed the close button
            for (Event event : this.pollEvents( )) {
                if (event.type == Event.Type.CLOSED) {
                    this.close( );
                }
            }
        }
    }

    public void addItem(Menu menu) {mainMenu = menu;}
    public int getScreenHeight() {return screenHeight;}
    public int getScreenWidth() {return  screenWidth;}
    public void drawMenu(Menu menu) {this.draw(menu.getButtons());}
}
