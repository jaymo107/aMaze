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

/**
 * This is a class responsible for holding a window for aMaze
 */
public class Maze {
    private int screenWidth;
    private int screenHeight;

    public Maze(int screenWidth, int screenHeight) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Creating a new window
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(screenWidth, screenHeight), "aMaze", WindowStyle.DEFAULT);
        window.setFramerateLimit(30);

        while (window.isOpen( )) {
            // Clear the screen
            window.clear(Color.BLUE);
            window.display();

            // the user pressed the close button
            for (Event event : window.pollEvents( )) {
                if (event.type == Event.Type.CLOSED) {
                    window.close( );
                }
            }
        }
    }

    public int getScreenHeight() {return screenHeight;}
    public int getScreenWidth() {return  screenWidth;}
}
