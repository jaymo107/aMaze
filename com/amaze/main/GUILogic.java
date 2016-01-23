package com.amaze.main;

import java.io.IOException;

/**
 * This class will handle all the logic associated with the Main Menu
 */
public class GUILogic implements Runnable{

    private Window mainWindow;
    private Menu mainMenu;

    public GUILogic(Window window) throws IOException {

        this.mainWindow = window;
        mainMenu = new Menu(window);
    }

    @Override
    public void run() {

        mainWindow.addItem(mainMenu);
    }
}

