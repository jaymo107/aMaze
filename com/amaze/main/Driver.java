package com.amaze.main;
import com.amaze.display.Window;
import com.amaze.entities.Tile;

import java.io.IOException;

/**
 * This class is responsible for creating an instance of a Maze.
 */

class Driver{

    public static void main(String[] args) throws IOException{

        Window maze = new Window(800,600,25,25);

        //Create a kind of tile and add to maze.
        Tile t1 = new Tile("",10,80,40,40, Tile.BlockType.WALL);
        maze.addItem(t1);

        //Start Displaying
        maze.display();
    }
}