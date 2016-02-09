package com.amaze.main;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

public class LevelMenuScene extends Scene {

    Text userLevel;
    Background background;
    RectangleShape textBackground;
    Texture backgroundImage = new Texture();
    Window wnd;
    Tile[][] tileMap;                           //Used for displaying map in background
    private int blocks;

    static final int MAX_LEVEL = 20;
    static final int MIN_LEVEL = 1;

    int userLevelNumber = 1;

    public LevelMenuScene(String sceneTitle, Window window) throws IOException {
        super(sceneTitle,window);
        wnd = window;
        blocks = 0;

        //Create background
        background = new Background(window.getScreenWidth(), window.getScreenHeight());

        //Create Font
        Font maze = new Font();
        try {
            maze.loadFromFile(Paths.get("res/fonts/Maze.ttf"));
        } catch (IOException e){
            System.out.println("Could not load the font!");
        }

        //Vector2f position = new Vector2f(xCord, yCord);
        Vector2f size = new Vector2f(window.getScreenWidth()/1.2F, window.getScreenHeight()/5);
        textBackground = new RectangleShape(size);
        textBackground.setPosition(window.getScreenWidth()/12F, window.getScreenHeight()/2.5F);


        backgroundImage.loadFromFile(Paths.get("res/menuGraphics/Wall.png"));
        textBackground.setTexture(backgroundImage);

        //Create text
        userLevel = new Text("Level 1", maze, 170);
        userLevel.setColor(Color.BLACK);
        userLevel.setStyle(Text.BOLD);
        userLevel.setOrigin((window.getScreenWidth()/9.5F) * -1, (window.getScreenHeight()/3F) * -1);

        System.out.println(window.getScreenHeight());
        System.out.println(window.getScreenWidth());
        changeBackground(userLevelNumber);

    }

    /**
     * This function is triggered when user presses arrow key up.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyUp() {
        if(userLevelNumber < MAX_LEVEL) {
            userLevelNumber++;
        } else {
            userLevelNumber = MAX_LEVEL;
        }
        userLevel.setString("Level " + userLevelNumber);
        changeBackground(userLevelNumber);
    }

    /**
     * This function is triggered when user presses arrow key down.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyDown() {
        if(userLevelNumber == MIN_LEVEL) {
            userLevelNumber = MIN_LEVEL;
        } else {
            userLevelNumber--;
        }
        userLevel.setString("Level " + userLevelNumber);
        changeBackground(userLevelNumber);
    }

    /**
     * This function is triggered when user presses enter button.
     * This function will check which button is currently selected.
     * Based on the button, a specific function will be invoked.
     */
    public void enterPressed() throws Exception {

        LevelReader level = new LevelReader();

        level.loadMap(getUserLevelNumber());

        Driver.BLOCK_SIZE = Driver.WINDOW_SIZE / level.getSizeOfMaze();

        GameScene game = new GameScene("Game", getWindow(), level.getSizeOfMaze(), Driver.BLOCK_SIZE, level.getLevel());

		getWindow().addScene(game);

		getWindow().getScene(getWindow().getArrayList().indexOf(game)).display();
        this.setRunning(false);
    }

    public void executeEvent(Event event) {
        switch(event.type) {
            case CLOSED: systemExit(); break;
            case KEY_PRESSED:
                switch (event.asKeyEvent().key) {
                    case UP: arrowKeyUp(); break;
                    case DOWN: arrowKeyDown(); break;
                    case ESCAPE: exitScene(this); break;
                    case RETURN:
                        try {
                            enterPressed();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }

        }
    }

	public String getUserLevelNumber()
    {
        return String.valueOf(userLevelNumber);
    }

    public void drawGraphics(RenderWindow window) {
        //window.draw(background);

        for (int j = 0; j < blocks; j++) {
            for (int i = 0; i < blocks; i++) {
                window.draw(tileMap[i][j]);
            }
        }

        window.draw(textBackground);
        window.draw(userLevel);
    }
    public void changeBackground(int levelNumber){
        Tile.BlockType[][] tempTiles;   //Holds reference to newly loaded tile map of the types
        int blockSize;

        /* Cache textures before we start using them in order to increase performance */
        Texture tileTexture[] = new Texture[7];
        for (int i = 0; i < tileTexture.length; i++) {
            tileTexture[i] = new Texture();

            try{
                tileTexture[i].loadFromFile(Paths.get("res/images/" + Tile.BlockType.values()[i].toString().toLowerCase() + ".png"));
            }catch (IOException e){
                System.out.println("Error loading tile image for menu background");
            }

        }

        /* Load Level from File according to number */
        LevelReader backgroundLevelLoader = new LevelReader();

        try {
            backgroundLevelLoader.loadMap(String.valueOf(levelNumber));
        } catch (IOException e) {
            System.out.println("Cannot load level " + levelNumber);
        }

        /* Load stored 2d map */
        tempTiles = backgroundLevelLoader.getLevel();

        /* Workout maze sizings */
        blocks = backgroundLevelLoader.getSizeOfMaze();
        blockSize = wnd.getSize().x / blocks;

        tileMap = new Tile[blocks][blocks];

        for (int j = 0; j < blocks; j++) {
            for (int i = 0; i < blocks; i++) {
                tileMap[i][j] = new Tile("", blockSize * i, blockSize * j, blockSize, blockSize, tempTiles[i][j], tileTexture);
            }
        }


        //wnd.setSize(new Vector2i(10,10));
        wnd.create(new VideoMode((int)tileMap[blocks - 1][blocks - 1].getPosition().x + blockSize,(int)tileMap[blocks - 1][blocks - 1].getPosition().y + blockSize),"sadas");

        /* Convert 2d array of types into tiles */
        System.out.println("hello");



    }
}
