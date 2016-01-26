package com.amaze.Library;
import com.amaze.entities.Tile;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.io.File;
import java.io.InputStreamReader;

/*
	Reads a text file with a csv type format into a BlockType 2D array (currently 5 x 5)
*/
public class LevelReader
{
    private Tile.BlockType[][] level = new Tile.BlockType[5][5];//Change this for larger maps

    public LevelReader() throws Exception
    {
        InputStream in = new FileInputStream(new File("res/Levels/Levels.txt"));
        Reader reader = new InputStreamReader(in);

        StringBuilder stringBuilder = new StringBuilder();
        int i; int x = 0; int y = 0;

        while ((i = reader.read()) != -1)// Reads all of the text file
        {
            if(Character.toString(((char) i)).equals(","))
            {
                level[x][y] = stringToBlockType(stringBuilder.toString());
                stringBuilder.setLength(0); // empty stringBulider

                if(++y == level[0].length)
                {
                    y = 0;
                    x++;
                    // goes to next row in the 2D Array
                }
            }
            else if(!(Character.toString(((char) i)).equals("\n")))
                stringBuilder.append((char) i);
        }
        System.out.println(Arrays.deepToString(level));// <- Uncomment to see 2D array
    }

    //	Converts string to BlockType form. If the string is invalid, type PATH will be returned
    public Tile.BlockType stringToBlockType(String blockType)
    {
        if (blockType.equals("START")) return Tile.BlockType.START;
        if (blockType.equals("FINISH")) return Tile.BlockType.FINISH;
        if (blockType.equals("DOOR")) return Tile.BlockType.DOOR;
        if (blockType.equals("WALL")) return Tile.BlockType.WALL;
        if (blockType.equals("VOID")) return Tile.BlockType.VOID;
        if (blockType.equals("CHARGE")) return Tile.BlockType.CHARGE;
        if (blockType.equals("FLOOR")) return Tile.BlockType.FLOOR;
        return Tile.BlockType.PATH;
    }

    public Tile.BlockType[][] getLevel(){return level;}
}
