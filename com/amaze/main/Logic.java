package com.amaze.main;

import java.io.IOException;

/**
 * Created by Kiran on 23/01/2016.
 */
public class Logic implements Runnable{
    Window maze;
    Tile t1 = new Tile("",10,20,500,500);

    public Logic(Window wnd) throws IOException{
        maze = wnd;
    }

    @Override
    public void run() {
        maze.addItem(t1);
    }
}
