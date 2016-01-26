package com.amaze.display;
import com.amaze.entities.Tile;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This is a class responsible for holding a window for aMaze
 */

public class Window extends RenderWindow{

	private int screenWidth;
    private int screenHeight;
    Sound sound; //Sound to be played

    private ArrayList<Tile> drawList; //Array list holding Tiles.

    /**
     *
     * @param resolutionX Width in pixels(physical size of window)
     * @param resolutionY Height in pixels(physical size of window)
     * @param blocksX Size of X grid overlayed onto window
     * @param blocksY Size of y grid overlayed onto window
     *
     * Example:
     *                maze(1920,1080,25,25)
     *                This would create a window of 1920x1080 pixels but with 25 by 25
     *                grid to place objects into a maze
     */

    public Window(int resolutionX, int resolutionY, int blocksX, int blocksY) throws IOException {
        //Instantiate
        this.screenWidth = resolutionX;
        this.screenHeight = resolutionY;
        drawList = new ArrayList<Tile>();

        // Creating a new window
        this.create(new VideoMode(screenWidth, screenHeight), "aMaze", WindowStyle.DEFAULT);
        this.setFramerateLimit(60);

        //Load Sounds
        SoundBuffer soundBuffer = new SoundBuffer();
        soundBuffer.loadFromFile(Paths.get("res/beep.wav"));

        //Create a sound and set its buffer
        sound = new Sound();
        sound.setBuffer(soundBuffer);
    }

    /**
     * Used to start displaying the window. Once this is called nothing can be changed.
     */

    public void display(){
        while (this.isOpen()) {
            // Clear the screen
            this.clear(Color.BLUE);
            drawItems(this.drawList);

            super.display();

            // the user pressed the close button
            for (Event event : this.pollEvents()) {
                //Different behaviour depending on
				if (event.type == Event.Type.CLOSED) close();
				if (event.type == Event.Type.KEY_PRESSED) {
					Keyboard.Key k = event.asKeyEvent().key;
					if (k == Keyboard.Key.UP) drawList.get(0).deltaY(-10F);
					else if (k == Keyboard.Key.DOWN) drawList.get(0).deltaY(10F);
					else if (k == Keyboard.Key.LEFT) drawList.get(0).deltaX(-10F);
					else if (k == Keyboard.Key.RIGHT) drawList.get(0).deltaX(10F);
					else sound.play();
				}
            }
        }
    }

    /**
     * Add an item to be display on screen.
     * @param tile Tile that you want to add
     */
    public void addItem(Tile tile){
        drawList.add(tile);
    }

    public int getScreenHeight() {return screenHeight;}

    public int getScreenWidth() {return  screenWidth;}

    /**
     * Draw all items in list
     * @param list List of tiles you want to draw
     */
    private void drawItems(ArrayList<Tile> list){
		list.forEach(this::draw);
    }

    public RenderWindow getWindow(){
        return this;
    }

}
