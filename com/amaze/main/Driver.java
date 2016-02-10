package com.amaze.main;

/**
 * This class is responsible for creating an instance of aMaze.
 */

class Driver{

    static int BLOCK_SIZE; //Number of blocks to display on X/Y axis
    static int WINDOW_SIZE; //Resolution(number of pixels) on X/Y axis
    private static boolean correctSize; //Used to check if we're using the correct resolution.

    public static void main(String[] args) throws Exception{
        WINDOW_SIZE = 700;
        correctSize = true;

        // Load Level and work out block size dynamically
        LevelReader level = new LevelReader();
        level.loadMap("1");
        BLOCK_SIZE = WINDOW_SIZE / level.getSizeOfMaze();

        //Check if we're using a nice resolution
        if(WINDOW_SIZE % BLOCK_SIZE > 0){
            //Then we cant display properly and therefore won't load the window!
            correctSize = false;
            System.out.println("WRONG SIZE");
        }

        // Create new window and set FPS limit to 60
        Window window = new Window(WINDOW_SIZE, WINDOW_SIZE + 60);
        window.setFramerateLimit(120);

        //Create Menu Scene
        MenuScene menu = new MenuScene("Main Menu",window);

        //LevelMenuScene levelMenu = new LevelMenuScene("Level Menu", window);
        //GameScene game = new GameScene("Game", window, level.getSizeOfMaze(), Driver.BLOCK_SIZE, level.getLevel());
        //MapMakerScene mapMaker = new MapMakerScene("Level Menu", window, 30, 30);

        window.addScene(menu);


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