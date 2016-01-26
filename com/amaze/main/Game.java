package com.amaze.main;

import com.amaze.display.Window;

import java.io.IOException;

/**
 * Created by Kiran on 25/01/2016.
 * Represents a game
 */
public class Game extends Window{
    int blockSize; //Size of each block. W and H

    public Game(int resolutionX, int resolutionY, int blocksX, int blocksY, int blockSize) throws IOException{
        super(resolutionX,resolutionY,blocksX,blocksY);
        this.blockSize = blockSize;



    }

    /**(
     * Translates X
     * @param blockX
     * @return
     */

    public int translateX(int blockX){

        return 1;
    }

    /**
     * Translates Y
     * @param blockY
     * @return
     */

    public int translateY(int blockY){

        return 1;
    }
}
