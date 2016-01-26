package com.amaze.main;
import com.amaze.Library.LevelReader;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.View;
import org.jsfml.system.*;

import com.amaze.display.Window;
import com.amaze.entities.Tile;

import java.io.IOException;

/**
 * This class is responsible for creating an instace of a Maze.
 */

class Driver{

    public static final int X_RES = 800;
    public static final int Y_RES = 800;
    public static final int BLOCK_SIZE = 200;

    public static void main(String[] args) throws IOException{
        //Workout how bit grid overlay should be
        int gridWidth = X_RES / BLOCK_SIZE;
        int gridHeight = Y_RES / BLOCK_SIZE;

        Window maze = new Window(X_RES,Y_RES,BLOCK_SIZE,BLOCK_SIZE);

        //Create a kind of tile and add to maze.
//        Tile t1 = new Tile("",0,0,200,200, Tile.BlockType.WALL);
//        maze.addItem(t1);
//        Tile t2 = new Tile("",200,0,200,200, Tile.BlockType.WALL);
//        maze.addItem(t2);
//        Tile t3 = new Tile("",400,0,200,200, Tile.BlockType.WALL);
//        maze.addItem(t3);
//        Tile t4 = new Tile("",600,0,200,200, Tile.BlockType.WALL);
//        maze.addItem(t4);


        try {
            LevelReader levelReader = new LevelReader();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Start Displaying
        maze.displayThis();
    }
}