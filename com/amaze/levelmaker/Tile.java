package com.amaze.levelmaker;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Dimension; 


public class Tile extends JButton
{

	public enum BlockType {
        WALL, FLOOR, DOOR, VOID, CHARGE, START, FINISH
    }

    private ImageIcon[] blockImageIcons = new ImageIcon[BlockType.values().length];
    private BlockType type = BlockType.WALL;

    private int xLocation;
    private int yLocation;

	public Tile(ImageIcon[] blockImageIcons, int xLocation, int yLocation)
	{
		super();
		this.blockImageIcons = blockImageIcons;
		this.setIcon(blockImageIcons[0]);
		this.xLocation = xLocation;
		this.yLocation = yLocation;
	}

	public Tile()
	{
		super();
	}

	public void changeBlockType(BlockType block)
	{
		int index = 0;

		if (block == BlockType.WALL)   index = 0;
		if (block == BlockType.FLOOR)  index = 1;
		if (block == BlockType.DOOR)   index = 2;
		if (block == BlockType.VOID)   index = 3;
		if (block == BlockType.CHARGE) index = 4;
		if (block == BlockType.START)  index = 5;
		if (block == BlockType.FINISH) index = 6;

		setIcon(blockImageIcons[index]);
		this.type = block;
	}

	public BlockType getBlockType(){ return type; }

	public int getXLocation(){ return xLocation; }

	public int getYLocation(){ return yLocation; }

}