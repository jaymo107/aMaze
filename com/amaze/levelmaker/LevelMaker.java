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

    private Tile.BlockType[] allValues = Tile.BlockType.values();

    private Tile[][] tiles;

    private int boardHeight;
    private int boardWidth;
    private int numberOfStart;
    private int numberOfFinish;

    ImageIcon[] blockImageIcons;

    public LevelMaker(int width, int height) {
        boardHeight = height;
        boardWidth = width;
        numberOfFinish = 0;
        numberOfStart = 0;

        tiles = new Tile[width][height];
        JPanel panel = new JPanel();
        setTitle("Map Maker");
        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        panel.setLayout(new GridLayout(width, height));

        // Has to be done to get correct window and image size
        tiles[0][0] = new Tile();
        tiles[0][0].setPreferredSize(new Dimension(30,30));

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
            //countNumbersOfStartEnd();

            if(numberOfStart > 1){
                JOptionPane.showMessageDialog(this,"You can only have one Start Square!");
            }else if(numberOfFinish > 1){
                JOptionPane.showMessageDialog(this,"You can only have one Finish Square!");
            }else{
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
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}



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

}
