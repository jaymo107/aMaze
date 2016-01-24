package com.amaze.main;
import org.jsfml.graphics.*;

/**
 * This class will handle the menu and buttons associated with it.
 */
public class Menu {

    private final int NUMBER_OF_ITEMS = 4; // Number of items available to select in the menu
    private Button button[] = new Button[NUMBER_OF_ITEMS];

    public Menu(Window window) {


        button[0] = new PlayButton(window.getScreenWidth() / 2.5F, (window.getScreenHeight() / NUMBER_OF_ITEMS), 200, 40, Color.RED);
        button[1] = new PlayButton(window.getScreenWidth() / 2.5F, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 1.6F, 200, 40, Color.RED);
        button[2] = new PlayButton(window.getScreenWidth() / 2.5F, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 2.2F, 200, 40, Color.RED);
        button[3] = new PlayButton(window.getScreenWidth() / 2.5F, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 2.8F, 200, 40, Color.RED);
    }

    public Button[] getButtons() {

        return button;
    }
}
