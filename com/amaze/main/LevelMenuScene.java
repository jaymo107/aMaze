package com.amaze.main;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LevelMenuScene extends Scene {

    Text userLevel;
    Background background;
    RectangleShape textBackground;
    Texture backgroundImage = new Texture();
    Window wnd;
    Tile[][] tileMap;                           //Used for displaying map in background
    private int blocks;
    private int currentLevel;

    static final int MAX_LEVEL = 20;
    static final int MIN_LEVEL = 1;

    List<String> results = new ArrayList<String>();

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

        //Setting size of background shader (white part)
        Vector2f size = new Vector2f(window.getScreenWidth(), window.getScreenHeight());
        textBackground = new RectangleShape(size);
        textBackground.setPosition(0,0);

        //Loading of Background Image for Text box
        backgroundImage.loadFromFile(Paths.get("res/menuGraphics/Wall.png"));
        textBackground.setTexture(backgroundImage);

        //Create text
        int fontSize = window.getScreenWidth() / 4;
        userLevel = new Text("Level 1", maze, fontSize);
        userLevel.setColor(Color.BLACK);
        userLevel.setStyle(Text.BOLD);
        userLevel.setOrigin((-(window.getScreenWidth()/2) + (fontSize * 1.85F)), -(window.getScreenHeight()/2) + fontSize);

        changeBackground(userLevelNumber);


        //Following set of codes adds all the files to the located in Levels folder to results arrayList
        File[] files = new File("res/Levels").listFiles();

        for(File file: files) {

            if(file.isFile()) {

                results.add(file.getName());
            }
        }
        System.out.println(results);
    }

    /**
     * This function is triggered when user presses arrow key up.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyUp() {
        if(userLevelNumber < results.size()) {
            userLevelNumber++;
        } else {
            userLevelNumber = results.size();
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

        GameScene game = new GameScene("Game", getWindow(), level.getSizeOfMaze(), Driver.BLOCK_SIZE, level.getLevel(), userLevelNumber);

		getWindow().addScene(game);

		getWindow().getScene(getWindow().getArrayList().indexOf(game)).display();
        this.setRunning(false);
    }

    public void executeEvent(Event event) {
        switch(event.type) {
            case CLOSED:
                systemExit();
                break;
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

	public String getUserLevelNumber() { return String.valueOf(userLevelNumber); }

    public void drawGraphics(RenderWindow window) {

        for (int j = 0; j < blocks; j++) {
            for (int i = 0; i < blocks; i++) {
                window.draw(tileMap[i][j]);
            }
        }
        window.draw(textBackground);
        window.draw(userLevel);
    }
    public void changeBackground(int levelNumber) {
        Tile.BlockType[][] tempTiles;   //Holds reference to newly loaded tile map of the types
        int blockSize;

        /* Cache textures before we start using them in order to increase performance */
        Texture tileTexture[] = new Texture[7];
        for (int i = 0; i < tileTexture.length; i++) {
            tileTexture[i] = new Texture();

            try {
                tileTexture[i].loadFromFile(Paths.get("res/images/" + Tile.BlockType.values()[i].toString().toLowerCase() + ".png"));
            } catch (IOException e) {
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

        wnd.create(new VideoMode((int) tileMap[blocks - 1][blocks - 1].getPosition().x + blockSize, (int) tileMap[blocks - 1][blocks - 1].getPosition().y + blockSize), "Level Menu");
    }
    public int getCurrentLevel() { return userLevelNumber; }
}
