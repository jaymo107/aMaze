package com.amaze.main;

import org.jsfml.graphics.Texture;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *Reads a text file with a csv type format into a BlockType 2D array (currently 5 x 5)
 */
public class LevelReader {

    private Tile.BlockType[][] level;//Change this for larger maps
    private int sizeOfMaze;


	/**
	 * Converts string to BlockType form. If the string is invalid, type PATH will be returned
	 */
    public Tile.BlockType stringToBlockType(String blockType) {

        if (blockType.equals("START")) return Tile.BlockType.START;
        if (blockType.equals("FINISH")) return Tile.BlockType.FINISH;
        if (blockType.equals("DOOR")) return Tile.BlockType.DOOR;
        if (blockType.equals("WALL")) return Tile.BlockType.WALL;
        if (blockType.equals("VOID")) return Tile.BlockType.VOID;
        if (blockType.equals("CHARGE")) return Tile.BlockType.CHARGE;
        if (blockType.equals("FLOOR")) return Tile.BlockType.FLOOR;

        return Tile.BlockType.WALL;
    }

    public Tile.BlockType[][] getLevel() {
        return level;
    }

    public int getSizeOfMaze() {
        return sizeOfMaze;
    }

    public void loadMap(String levelNumber) throws IOException {

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

        while ((i = reader.read()) != -1) // Reads all of the text file
        {
            if (Character.toString(((char) i)).equals(",")) {
                level[y][x] = stringToBlockType(stringBuilder.toString());
                stringBuilder.setLength(0); // empty StringBuilder


                if (++y == level[0].length)//If length of first line == max
                {
                    y = 0;
                    x++;
                    // goes to next row in the 2D Array
                }
            } else if (!(Character.toString(((char) i)).equals("\n"))) {
                stringBuilder.append((char) i);
            }
        }
        //System.out.println(Arrays.deepToString(level));// <- Uncomment to see 2D array
    }

    public void loadNewTileMap(Window window, int blocks, int blockSize, Tile.BlockType[][] level) throws Exception {
        GameScene.setBlockSize(blockSize);

		Tile[][] tileMap = new Tile[blocks][blocks];

        /* Cache textures before we start using them in order to increase performance */
        Texture tileTexture[] = new Texture[7];
        for (int i = 0; i < tileTexture.length; i++) {
            tileTexture[i] = new Texture();
			tileTexture[i].loadFromFile(Paths.get("res/images/" + Tile.BlockType.values()[i].toString().toLowerCase() + ".png"));
        }

        /* Create new instances of tiles */
        for (int j = 0; j < blocks; j++) {
            for (int i = 0; i < blocks; i++) {
                tileMap[i][j] = new Tile("", blockSize * i, blockSize * j, GameScene.getBlockSize(), GameScene.getBlockSize(), level[i][j], tileTexture);
            }
        }
    }

}

