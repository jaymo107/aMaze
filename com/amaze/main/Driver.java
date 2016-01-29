package com.amaze.main;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
import org.jsfml.system.*;
import java.io.IOException;


/**
 * This class is responsible for creating an instance of a Maze.
 */


class Driver{
    private static int BLOCK_SIZE; //Number of blocks to display on X/Y axis
    private static int WINDOW_SIZE; //Resolution(number of pixels) on X/Y axis

    public static void main(String[] args) throws Exception{
        WINDOW_SIZE = 800;

        // Load Level and work out block size dynamically
        LevelReader level = new LevelReader();
        BLOCK_SIZE = WINDOW_SIZE / level.getSizeOfMaze();

        // Create new window and set FPS limit to 60
        Window window = new Window(WINDOW_SIZE,WINDOW_SIZE);
        window.setFramerateLimit(60);

        //Create Main Menu
        MenuScene menu = new MenuScene("Main Menu",window);

        GameScene game = new GameScene("Game",window,WINDOW_SIZE,level.getSizeOfMaze(), BLOCK_SIZE,
                level.getLevel());
        window.addScene(menu);
        window.addScene(game);

        //Start Displaying
        window.displayThis();

    }
}