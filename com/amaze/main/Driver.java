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

    public static void main(String[] args) throws IOException{

        Window maze = new Window(800,600,25,25);

        MenuScene menu = new MenuScene("Main Menu",maze);
        maze.addScene(menu);

        //Start Displaying
        maze.displayThis();

    }
}