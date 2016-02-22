package com.amaze.main;

/**
 * This class is responsible for creating an instance of aMaze.
 */

class Driver{

    static int BLOCK_SIZE; //Number of blocks to display on X/Y axis
    static int WINDOW_SIZE; //Resolution(number of pixels) on X/Y axis

	public static void main(String[] args) throws Exception{
		WINDOW_SIZE = 600;

        LevelReader level = new LevelReader();
        level.loadMap(1);

		int BLOCK_SIZE = WINDOW_SIZE / level.getSizeOfMaze();

        //Check if we're using a nice resolution
		//Then we cant display properly and therefore won't load the window!
        if (WINDOW_SIZE % BLOCK_SIZE > 0) System.out.println("Wrong size");

        // Create new window and set FPS limit to 60
        Window window = new Window(WINDOW_SIZE, WINDOW_SIZE + 60);
        window.setFramerateLimit(120);

        //Create Menu Scene
        MenuScene menu = new MenuScene("Main Menu",window);
        window.addScene(menu);

        //Start Displaying
        window.displayThis();
    }

}