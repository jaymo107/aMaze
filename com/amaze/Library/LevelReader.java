package com.amaze.Library;
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
    public enum BlockType
    {
        START, FINISH, PATH, WALL,
        DOOR
    }
    private BlockType[][] level = new BlockType[5][5];//Change this for larger maps

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
    public BlockType stringToBlockType(String blockType)
    {
        if (blockType.equals("START")) return BlockType.START;
        if (blockType.equals("FINISH")) return BlockType.FINISH;
        if (blockType.equals("DOOR")) return BlockType.DOOR;
        if (blockType.equals("WALL")) return BlockType.WALL;
        return BlockType.PATH;
    }

    public BlockType[][] getLevel(){return level;}
}
