package com.amaze.levelmaker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LevelMaker extends JFrame implements MouseListener, KeyListener {

    Tile.BlockType[] allValues = Tile.BlockType.values();

    Tile[][] tiles;

    private int boardHeight;
    private int boardWidth;

    //private boolean retina = false;
    //private boolean exiting = false;

    ImageIcon[] blockImageIcons;

    public LevelMaker(int width, int height) {
        boardHeight = height;
        boardWidth = width;

        tiles = new Tile[width][height];
        setTitle("Map Maker");
        JPanel panel = new JPanel();
        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //this.retina = isRetina();

        panel.setLayout(new GridLayout(height, width));

        // Has to be done to get correct window and image size
        tiles[0][0] = new Tile();
        tiles[0][0].addMouseListener(this);

        setSize(((int) tiles[0][0].getPreferredSize().getHeight()) * width, ((int) tiles[0][0].getPreferredSize().getHeight()) * height + 20);

        blockImageIcons = new ImageIcon[7];

        for (int i = 0; i < blockImageIcons.length; i++) {
            blockImageIcons[i] = makeImageIcon("res/images/" + allValues[i].toString().toLowerCase() + ".png");
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = new Tile(blockImageIcons, x, y);
                tiles[x][y].addMouseListener(this);
                tiles[x][y].addKeyListener(this);
                panel.add(tiles[x][y]);
            }
        }
        setVisible(true);
    }

    /*
    private boolean isRetina() {
        boolean isRetina = false;
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        try {
            Field field = graphicsDevice.getClass().getDeclaredField("scale");

            if (field != null) {
                field.setAccessible(true);
                Object scale = field.get(graphicsDevice);
                if (scale instanceof Integer && ((Integer) scale).intValue() == 2) {
                    isRetina = true;
                }
            }
        } catch (Exception e) {
            //bad practice - but we just want to suppress the exception
        }

        return isRetina;
    }
    */

    public void mouseClicked(MouseEvent e) {
        Tile tempTile = (Tile)e.getSource();
        int nextImageIndex = java.util.Arrays.asList(allValues).indexOf(tempTile.getBlockType())+1;
        if(nextImageIndex > allValues.length-1) nextImageIndex = 0;

        tempTile.changeBlockType(allValues[nextImageIndex]);
    }

    public ImageIcon makeImageIcon(String imagePath) {
        Image img = new ImageIcon(imagePath).getImage();
        Image newImg = img.getScaledInstance( (int)tiles[0][0].getPreferredSize().getHeight(), (int)tiles[0][0].getPreferredSize().getHeight(), java.awt.Image.SCALE_SMOOTH );

        return new ImageIcon(newImg);
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter("Levels.txt", true));

                for (int y = 0; y < boardHeight; y++) {
                    for (int x = 0; x < boardWidth; x++) {
                        writer.print(tiles[x][y].getBlockType().toString() + ",");
                    }
                    writer.println("");
                }
                writer.close();
                JOptionPane.showMessageDialog(this,"Export Successful");
            }
            catch (IOException f) {
                JOptionPane.showMessageDialog(this,"Export Failed");
            }
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

}
