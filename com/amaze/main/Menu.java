package com.amaze.main;
import org.jsfml.graphics.*;

/**
 * This class will handle the menu and buttons associated with it.
 */
public class Menu {

    private final int NUMBER_OF_ITEMS = 5; // Number of items available to select in the menu
    private Button button[];
    private Window window;


    public Menu(Window window) {

        this.window = window;
        button[0] = new PlayButton(window.getScreenWidth() / 2, window.getScreenHeight() / 2, window, Color.RED);

    }

    /**
     * This function will handle an event when user presses arrow key up
     */
    public void arrowKeyUp() {

    }

    /**
     * This function will handle an event when user presses arrow key down
     */
    public void arrowKeyDown() {

    }

    /**
     * This function will handle an event when user presses an enter key
     */
    public void enterPressed() {

    }
}
