package com.amaze.levelmaker;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Dimension; 


public class Tile extends JButton
{
	public static enum BlockType {
        WALL, FLOOR, DOOR, START, FINISH,
        VOID, CHARGE
    }

    private ImageIcon[] blockImageIcons = new ImageIcon[7];
    private BlockType type = BlockType.WALL;
    private int xLocation;
    private int yLocation;

	public Tile(ImageIcon[] blockImageIcons, int xLocation, int yLocation)
	{
		super();
		this.blockImageIcons = blockImageIcons;
		this.setIcon(blockImageIcons[0]);
		type = BlockType.WALL;
		this.xLocation = xLocation;
		this.yLocation = yLocation;
	}
	public Tile()
	{
		super();
		this.type = BlockType.CHARGE;
	}

	public void changeBlockType(BlockType block)
	{
		if(block == BlockType.WALL) this.setIcon(blockImageIcons[0]);
		if(block == BlockType.FLOOR) this.setIcon(blockImageIcons[1]);
		if(block == BlockType.DOOR) this.setIcon(blockImageIcons[2]);
		if(block == BlockType.VOID) this.setIcon(blockImageIcons[3]);
		if(block == BlockType.CHARGE) this.setIcon(blockImageIcons[4]);
		if(block == BlockType.START) this.setIcon(blockImageIcons[5]);
		if(block == BlockType.FINISH) this.setIcon(blockImageIcons[6]);

		this.type = block;
	}

	// uses a lot of processing - make caches of images at the beginning to make consistant scaling
	public ImageIcon makeImageIcon(String imagePath)
	{
		ImageIcon icon = new ImageIcon(imagePath);
		Image img = icon.getImage() ;  
	    Image newimg = img.getScaledInstance( (int)this.getPreferredSize().getHeight(), (int)this.getPreferredSize().getHeight(), java.awt.Image.SCALE_SMOOTH ) ; 
	    icon = new ImageIcon( newimg );
	    return icon;
	}

	public BlockType getBlockType(){return type;}
	public int getXLocation(){return xLocation;}
	public int getYLocation(){return yLocation;}

}