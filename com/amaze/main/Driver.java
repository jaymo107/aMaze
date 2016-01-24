package com.amaze.main;
<<<<<<< HEAD
import org.jsfml.graphics.Drawable;
=======
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
>>>>>>> master
import org.jsfml.system.*;

import java.io.IOException;

/**
 * This class is responsible for creating an instace of a Maze.
 */


class Driver{

    public static void main(String[] args) throws IOException{

<<<<<<< HEAD
        Window window = new Window(800,600,25,25);
=======
        Window maze = new Window(800,600,25,25);

        //Create a kind of tile and add to maze.
        Tile t1 = new Tile("",10,80,40,40, Tile.BlockType.WALL);
        maze.addItem(t1);

        //Start Displaying
        maze.displayThis();
>>>>>>> master
    }
}