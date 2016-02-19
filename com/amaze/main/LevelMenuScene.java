package com.amaze.main;

import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LevelMenuScene extends Scene {

    private Text userLevel;
	private RectangleShape textBackground;
    private Texture backgroundImage = new Texture();
    private Tile[][] tileMap;                           //Used for displaying map in background
    private int blocks;

    private int numberOfVoids;
    private int numberOfCharges;
    private int numberOfWalls;
    private int numberOfDoors;

    private Text voids;
    private Text charges;
    private Text walls;
    private Text doors;
    private Music music;

	public static final int MIN_LEVEL = 1;

    RectangleShape edgeFrame = new RectangleShape();

    ArrayList<String> results = new ArrayList<>();

    int userLevelNumber = 1;

    public LevelMenuScene(String sceneTitle, Window window, Music music) throws IOException {
        super(sceneTitle, window);
        blocks = 0;

        //Reference Music
        this.music = music;

        //Create background
		new Background(window.getScreenWidth(), window.getScreenHeight());

        //Create Font
        Font maze = new Font();
        Font arial = new Font();
        try {
            maze.loadFromFile(Paths.get("res/fonts/Maze.ttf"));
            arial.loadFromFile(Paths.get("res/fonts/Arial.ttf"));
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

        changeBackground(userLevelNumber,Driver.BLOCK_SIZE);

        walls = new Text("Number Of Walls: ", arial, 30);
        walls.setString("Number of Walls: " +numberOfWalls);
        walls.setColor(Color.BLACK);
        walls.setStyle(Text.BOLD);
        walls.setOrigin((-(window.getScreenWidth()/2) + (fontSize * 1.85F)), -(window.getScreenHeight()/2) + fontSize);

        charges = new Text("Number Of Charges: ", arial, 30);
        charges.setString("Number of Charges: " +numberOfCharges);
        charges.setColor(Color.BLACK);
        charges.setStyle(Text.BOLD);
        charges.setOrigin((-(window.getScreenWidth()/2) + (fontSize * 1.85F)), -(window.getScreenHeight()/2) + 1.2F*fontSize);

        voids = new Text("Number Of Voids: ", arial, 30);
        voids.setString("Number of Voids: " +numberOfVoids);
        voids.setColor(Color.BLACK);
        voids.setStyle(Text.BOLD);
        voids.setOrigin((-(window.getScreenWidth()/2) + (fontSize * 1.85F)), -(window.getScreenHeight()/2) + 1.5F*fontSize);

        doors = new Text("Number Of Doors: ", arial, 30);
        doors.setString("Number of Doors: " +numberOfDoors);
        doors.setColor(Color.BLACK);
        doors.setStyle(Text.BOLD);
        doors.setOrigin((-(window.getScreenWidth()/2) + (fontSize * 1.85F)), -(window.getScreenHeight()/2) + 1.8F*fontSize);

        //Following set of codes adds all the files to the located in Levels folder to results arrayList
        File[] files = new File("res/Levels").listFiles();

        for(File file: files != null ? files : new File[0]) {
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
        changeBackground(userLevelNumber,Driver.BLOCK_SIZE);
        walls.setString("Number of Walls: " +numberOfWalls);
        charges.setString("Number of Charges: " +numberOfCharges);
        voids.setString("Number of Voids: " +numberOfVoids);
        doors.setString("Number of Doors: " +numberOfDoors);
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
        changeBackground(userLevelNumber,Driver.BLOCK_SIZE);
        walls.setString("Number of Walls: " +numberOfWalls);
        charges.setString("Number of Charges: " +numberOfCharges);
        voids.setString("Number of Voids: " +numberOfVoids);
        doors.setString("Number of Doors: " +numberOfDoors);
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

        music.stop();
		getWindow().addScene(game);
		getWindow().getScene(getWindow().getArrayList().indexOf(game)).display();
        this.setRunning(false);
    }

	public String getUserLevelNumber() { return String.valueOf(userLevelNumber); }

    public void drawGraphics(RenderWindow window) {
        for (int j = 0; j < blocks; j++) {
            for (int i = 0; i < blocks; i++) {
                window.draw(tileMap[i][j]);
            }
        }
        window.draw(textBackground);
        window.draw(edgeFrame);
        window.draw(userLevel);
        window.draw(walls);
        window.draw(charges);
        window.draw(voids);
        window.draw(doors);
    }

    public void changeBackground(int levelNumber, int blockSize) {
        Tile.BlockType[][] tempTiles;   //Holds reference to newly loaded tile map of the types
        //int blockSize;

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
        //blockSize = getWindow().getSize().x / blocks;

        tileMap = new Tile[blocks][blocks];

        for (int j = 0; j < blocks; j++) {
            for (int i = 0; i < blocks; i++) {
                tileMap[i][j] = new Tile(blockSize * i, blockSize * j, blockSize, blockSize, tempTiles[i][j], tileTexture);
            }
        }

        edgeFrame.setSize(new Vector2f(getWindow().getScreenWidth(), getWindow().getScreenHeight()));
        edgeFrame.setPosition(0,0);
        Texture edgeFrameTexture = new Texture();

        try{
            edgeFrameTexture.loadFromFile(Paths.get("res/images/frame.png"));
        }catch (IOException e){
            System.out.println("Problem loading edge frame");
        }

        edgeFrameTexture.setSmooth(true);
        edgeFrame.setTexture(edgeFrameTexture);

        numberOfWalls = backgroundLevelLoader.getWallAmount();
        numberOfCharges = backgroundLevelLoader.getChargeAmount();
        numberOfDoors = backgroundLevelLoader.getDoorAmount();
        numberOfVoids = backgroundLevelLoader.getVoidAmount();
    }

}
