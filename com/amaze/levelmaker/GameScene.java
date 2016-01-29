package com.amaze.levelmaker;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class GameScene extends JFrame implements MouseListener, KeyListener
{

    Tile.BlockType[] allValues = Tile.BlockType.values();

	Tile[][] tiles;

	private JPanel panel = new JPanel();
	private int boardHeight;
	private int boardWidth;


	public GameScene(int width, int height)
	{
		boardHeight = height;
		boardWidth = width;

		tiles = new Tile[width][height];
		setTitle("Map Maker");
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.setLayout(new GridLayout(height,width));

		// Has to be done to get correct window and image size
		tiles[0][0] = new Tile();
		tiles[0][0].addMouseListener(this);

		setSize(((int)tiles[0][0].getPreferredSize().getHeight())*width, ((int)tiles[0][0].getPreferredSize().getHeight())*height+20);	

		ImageIcon[] blockImageIcons = new ImageIcon[7];
		blockImageIcons[0] = makeImageIcon("images/wall.png");
		blockImageIcons[1] = makeImageIcon("images/floor.png");
		blockImageIcons[2] = makeImageIcon("images/door.png");
		blockImageIcons[3] = makeImageIcon("images/void.png");
		blockImageIcons[4] = makeImageIcon("images/charge.png");
		blockImageIcons[5] = makeImageIcon("images/start.png");
		blockImageIcons[6] = makeImageIcon("images/finish.png");
		
		for (int y = 0; y<height; y++)
		{
			for (int x = 0; x<width; x++)
			{
				tiles[x][y] = new Tile(blockImageIcons, x, y);
				tiles[x][y].addMouseListener(this);
				tiles[x][y].addKeyListener(this);
				panel.add(tiles[x][y]);
			}
		}
		setVisible(true);
	}

	public void mouseClicked(MouseEvent e)
	{
		Tile tempTile = (Tile)e.getSource();
		int nextImageIndex = java.util.Arrays.asList(allValues).indexOf(tempTile.getBlockType())+1;
		if(nextImageIndex > allValues.length-1) nextImageIndex = 0;

		tempTile.changeBlockType(allValues[nextImageIndex]);
	}

	public ImageIcon makeImageIcon(String imagePath)
	{
		ImageIcon icon = new ImageIcon(imagePath);
		Image img = icon.getImage() ;  
	    Image newimg = img.getScaledInstance( (int)tiles[0][0].getPreferredSize().getHeight(), (int)tiles[0][0].getPreferredSize().getHeight(), java.awt.Image.SCALE_SMOOTH ) ; 
	    icon = new ImageIcon( newimg );
	    return icon;
	}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			try 
			{
				PrintWriter writer = new PrintWriter(new FileWriter("Levels.txt", true));

				for (int y = 0; y<boardHeight; y++)
				{
					for (int x = 0; x<boardWidth; x++)
					{
						writer.print(tiles[x][y].getBlockType().toString() + ",");
					}
					writer.println("");
				}
				writer.close();
				JOptionPane.showMessageDialog(this,"Export Successful");
			}
			catch (IOException f) 
			{
   			 	JOptionPane.showMessageDialog(this,"Export Failed");
			}
		}
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}


	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
}