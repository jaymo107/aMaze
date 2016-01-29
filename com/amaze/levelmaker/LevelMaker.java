package com.amaze.levelmaker;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
import java.awt.geom.AffineTransform;import java.lang.reflect.Field;

public class LevelMaker extends JFrame implements MouseListener, KeyListener {

    Tile.BlockType[] allValues = Tile.BlockType.values();

    Tile[][] tiles;

    private JPanel panel = new JPanel();
    private int boardHeight;
    private int boardWidth;

    private boolean retina = false;
    private boolean exiting = false;

    ImageIcon[] blockImageIcons;


    public LevelMaker(int width, int height) {
        boardHeight = height;
        boardWidth = width;

        tiles = new Tile[width][height];
        setTitle("Map Maker");
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.retina = isRetina();

        panel.setLayout(new GridLayout(height, width));

        // Has to be done to get correct window and image size
        tiles[0][0] = new Tile();
        tiles[0][0].addMouseListener(this);

        setSize(((int) tiles[0][0].getPreferredSize().getHeight()) * width, ((int) tiles[0][0].getPreferredSize().getHeight()) * height + 20);

        blockImageIcons = new ImageIcon[7];
        blockImageIcons[0] = makeImageIcon("images/wall.png");
        blockImageIcons[1] = makeImageIcon("images/floor.png");
        blockImageIcons[2] = makeImageIcon("images/door.png");
        blockImageIcons[3] = makeImageIcon("images/void.png");
        blockImageIcons[4] = makeImageIcon("images/charge.png");
        blockImageIcons[5] = makeImageIcon("images/start.png");
        blockImageIcons[6] = makeImageIcon("images/finish.png");

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
            //bad practice - but we just want to surpress the exception
        }

        return isRetina;
    }

    /**
     * A method called by the operating system to draw onto the screen - <p><B>YOU DO NOT (AND SHOULD NOT) NEED TO CALL THIS METHOD.</b></p>
     */
    public void paint (Graphics gr)
    {
        Graphics2D window = (Graphics2D) gr;

        //if the user has a retina display, everything requires doubling
        //and then scaled down back down to a ratio of 1:1
        //http://lists.apple.com/archives/java-dev/2012/Jun/msg00069.html
        int scale = (this.retina)?2:1;

        AffineTransform transformer = new AffineTransform();
        transformer.scale((float)1/scale, (float)1/scale);

        window.setTransform(transformer);

        BufferedImage i = new BufferedImage(this.getWidth() * scale, this.getHeight() * scale, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = i.createGraphics();

        window.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        synchronized (this)
        {
            if (!this.exiting)
            {
//                g.clearRect(0, 0, width * scale, height * scale);
//                for(SolarObject t : things)
//                {
//                    g.setColor(t.col);
//                    g.fillOval(t.x * scale, t.y * scale, t.diameter * scale, t.diameter * scale);
//
//                    try{ Thread.sleep(0); } catch (Exception e) {} }
           }
//
//            window.drawImage(i, 0, 0, this);
        }
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
