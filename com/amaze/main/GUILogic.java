package com.amaze.main;

import java.io.IOException;

/**
 * This class will handle all the logic associated with the Main Menu
 */
public class GUILogic implements Runnable{

    private Window window;
    private Menu mainMenu;

    public GUILogic(Window window) throws IOException {

        this.window = window;
        mainMenu = new Menu(window);
    }

    @Override
    public void run() {

        window.addItem(mainMenu);
    }
}

