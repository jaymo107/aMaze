package com.amaze.main;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.w3c.dom.css.Rect;

import javax.swing.border.TitledBorder;

/**
 * This is a class responsible for holding a window for aMaze
 */

public class Window extends RenderWindow{
    private int screenWidth;
    private int screenHeight;
    Sound sound; //Sound to be played


    private ArrayList<Button> menuButtons;

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

        menuButtons = new ArrayList<>();

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

    public void displayThis(){
        while (this.isOpen( )) {
            // Clear the screen
            this.clear(Color.BLUE);
            drawItems(this.menuButtons);

            this.display();

            // the user pressed the close button
            for (Event event : this.pollEvents( )) {
                //Different behaviour depending on
                switch(event.type){
                    case CLOSED:
                        this.close();
                        break;
                    case KEY_PRESSED:
//                        if(event.asKeyEvent().key == Keyboard.Key.UP){
//                            drawList.get(0).deltaX(10F);
//                        }else if(event.asKeyEvent().key == Keyboard.Key.DOWN){
//                            drawList.get(0).deltaX(-10F);
//                        }else{
//                            sound.play();
//                        }
                        break;
                }
            }
        }
    }

    public void addButton(Button[] button) {

        for (Button aButton : button) {

            menuButtons.add(aButton);
        }
    }

    public int getScreenHeight() {return screenHeight;}

    public int getScreenWidth() {return  screenWidth;}


    private void drawItems(ArrayList<Button> list){
        for(Button button : list){
            this.draw(button);
        }
    }

    public RenderWindow getWindow(){
        return this;
    }

}