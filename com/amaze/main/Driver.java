package com.amaze.main;
import org.jsfml.system.*;

import java.io.IOException;

/**
 * This class is responsible for creating an instace of a Maze.
 */

class Driver{

    public static void main(String[] args) throws IOException{
        Tile t1 = new Tile("",10,20,10,20);
        Window maze = new Window(800,600,25,25);
    }
}