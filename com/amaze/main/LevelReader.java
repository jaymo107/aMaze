package com.amaze.main;

import java.io.*;

/**
 *Reads a text file with a csv type format into a BlockType 2D array (currently 5 x 5)
 */
public class LevelReader {

    private Tile.BlockType[][] level;//Change this for larger maps
    private int sizeOfMaze;

    private int voidAmount = 0;
    private int chargeAmount = 0;
    private int wallAmount = 0;
	private int doorAmount = 0;


	/**
	 * Converts string to BlockType form. If the string is invalid, type PATH will be returned, also counts
     * the amount of each type of tile excluding START and FINISH
	 */
    public Tile.BlockType stringToBlockType(String blockType) {
        if (blockType.equals("START")) return Tile.BlockType.START;
        if (blockType.equals("FINISH")) return Tile.BlockType.FINISH;
        if (blockType.equals("DOOR")) {
            doorAmount++;
            return Tile.BlockType.DOOR;
        }
        if (blockType.equals("WALL")) {
            wallAmount++;
            return Tile.BlockType.WALL;
        }
        if (blockType.equals("VOID")) {
            voidAmount++;
            return Tile.BlockType.VOID;
        }
        if (blockType.equals("CHARGE")) {
            chargeAmount++;
            return Tile.BlockType.CHARGE;
        }
        if (blockType.equals("FLOOR")) {
			return Tile.BlockType.FLOOR;
        }

        return Tile.BlockType.WALL;
    }

    public Tile.BlockType[][] getLevel() {
        return level;
    }

    public int getSizeOfMaze() {
        return sizeOfMaze;
    }

    public void loadMap(int levelNumber) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int i;
        int x = 0;
        int y = 0;
        int currentlyReadChar = 0;
        sizeOfMaze = 0;

        //Open up file
        InputStream in = new FileInputStream(new File("res/Levels/" + levelNumber + ".txt"));
        Reader reader = new InputStreamReader(in);

        //Read map size
        while (currentlyReadChar != 10) {
            currentlyReadChar = reader.read();
            if (currentlyReadChar == 44) sizeOfMaze++;
        }

        //Clear the reader
        reader.close();
        in = new FileInputStream(new File("res/Levels/" + levelNumber + ".txt"));
        reader = new InputStreamReader(in);

        //Initialise Level
        level = new Tile.BlockType[sizeOfMaze][sizeOfMaze];

		// Reads all of the text file
        while ((i = reader.read()) != -1) {
            if (Character.toString(((char) i)).equals(",")) {
                level[y][x] = stringToBlockType(stringBuilder.toString());
                stringBuilder.setLength(0); // empty StringBuilder

				//If length of first line == max
                if (++y == level[0].length) {
                    y = 0;
                    x++;
                    // goes to next row in the 2D Array
                }
            } else if (!(Character.toString(((char) i)).equals("\n"))) {
                stringBuilder.append((char) i);
            }
        }
    }

    public int getDoorAmount() {
        return doorAmount;
    }

    public int getVoidAmount() {
        return voidAmount;
    }

    public int getChargeAmount() {
        return chargeAmount;
    }

    public int getWallAmount() {
        return wallAmount;
    }

}

