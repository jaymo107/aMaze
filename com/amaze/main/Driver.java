package com.amaze.main;
/**
 * This class is responsible for creating an instance of aMaze.
 */

class Driver{

    static int BLOCK_SIZE; //Number of blocks to display on X/Y axis
    static int WINDOW_SIZE; //Resolution(number of pixels) on X/Y axis

    public static void main(String[] args) throws Exception{

        WINDOW_SIZE = 800;

        // Create new window and set FPS limit to 60
        Window window = new Window(WINDOW_SIZE, WINDOW_SIZE);
        window.setFramerateLimit(60);

        //Create Menu Scene
        MenuScene menu = new MenuScene("Main Menu",window);
        window.addScenes(menu);

        /**
         * GameScene and LevelMenuScene calls are now happening in the appropriate places.
         * By doing this we don't need to allocate additional memory to store information about the game and level menu
         * right when the application is launched. It makes code much cleaner and is a good practice in general.
         *
         * All functionality remains intact
         */

        //Start Displaying
        window.displayThis();
    }
}
