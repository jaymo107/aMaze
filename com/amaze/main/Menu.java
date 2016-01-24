package com.amaze.main;
import org.jsfml.graphics.*;

/**
 * This class will handle the menu and buttons associated with it.
 */
public class Menu {

    private final int NUMBER_OF_ITEMS = 1; // Number of items available to select in the menu
    private Button button;
    //private Window window;


    public Menu(Window window) {

        //this.window = window;
        button = new PlayButton(window.getScreenWidth() / 2, (window.getScreenHeight() / NUMBER_OF_ITEMS), 500, 500, window, Color.RED);
//        this.button[1] = new PlayButton(window.getScreenWidth() / 2, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 2, 100, 20, window, Color.RED);
//        this.button[2] = new PlayButton(window.getScreenWidth() / 2, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 3, 100, 20, window, Color.RED);
//        this.button[3] = new PlayButton(window.getScreenWidth() / 2, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 4, 100, 20, window, Color.RED);

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
    public Button getButtons() {

        return button;
    }
}
