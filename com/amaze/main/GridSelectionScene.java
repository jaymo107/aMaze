package com.amaze.main;

import com.amaze.MapMaker.MapMakerScene;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

public class GridSelectionScene extends Scene {

    Text userLevel;
    Background background;
    RectangleShape textBackground;
    Texture backgroundImage = new Texture();
    Window wnd;
    Tile[][] tileMap;                           //Used for displaying map in background
    private int blocks;

    static final int MAX_WIDTH = 30;
    static final int MAX_HEIGHT = 30;
    static final int MIN_WIDTH = 5;
    static final int MIN_HEIGHT = 5;

    private int mapWidth = 5;
    private int mapHeight = 5;

    public GridSelectionScene(String sceneTitle, Window window) throws IOException {

        super(sceneTitle, window);
        wnd = window;
        blocks = 0;

        //Create background
        background = new Background(window.getScreenWidth(), window.getScreenHeight());

        //Create Font
        Font maze = new Font();
        try {
            maze.loadFromFile(Paths.get("res/fonts/Maze.ttf"));
        } catch (IOException e) {
            System.out.println("Could not load the font!");
        }

        float textBackgroundHeight = window.getScreenHeight() / 5;
        float textBackgroundWidth  = window.getScreenWidth() / 1.25F;
        float textBackgroundXCord  = window.getScreenWidth() / 2 - (textBackgroundWidth / 2);
        float textBackgroundYCord  = window.getScreenHeight() / 2  - (textBackgroundHeight / 1.5F);

        float textXCord = window.getScreenWidth() / -7.5F;
        float textYCord = window.getScreenHeight() / -3.4F;

        int fontSize = window.getScreenWidth() / 4;

        //Vector2f position = new Vector2f(xCord, yCord);
        Vector2f size = new Vector2f(textBackgroundWidth, textBackgroundHeight);
        textBackground = new RectangleShape(size);
        textBackground.setPosition(textBackgroundXCord, textBackgroundYCord);


        backgroundImage.loadFromFile(Paths.get("res/menuGraphics/Wall.png"));
        textBackground.setTexture(backgroundImage);

        //Create text
        userLevel = new Text("5 X 5", maze, fontSize);
        userLevel.setColor(Color.BLACK);
        userLevel.setStyle(Text.BOLD);
        userLevel.setOrigin(textXCord, textYCord);

        System.out.println(window.getScreenHeight());
        System.out.println(window.getScreenWidth());
    }

    /**
     * This function is triggered when user presses arrow key up.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyUp() {
        if (mapWidth < MAX_WIDTH && mapHeight < MAX_HEIGHT) {
            mapHeight++;
            mapWidth++;
        } else {
            mapHeight = MAX_HEIGHT;
            mapWidth = MAX_WIDTH;
        }
        userLevel.setString(mapWidth + " X " + mapHeight);
    }

    /**
     * This function is triggered when user presses arrow key down.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyDown() {
        if (mapWidth == MIN_WIDTH && mapHeight == MIN_HEIGHT) {
            mapHeight = MIN_HEIGHT;
            mapWidth = MIN_WIDTH;
        } else {
            mapHeight--;
            mapWidth--;
        }
        userLevel.setString(mapWidth + " X " + mapHeight);
    }

    /**
     * This function is triggered when user presses enter button.
     * This function will check which button is currently selected.
     * Based on the button, a specific function will be invoked.
     */
    public void enterPressed() throws Exception {

        try {
            MapMakerScene mapMaker = new MapMakerScene("Map Maker", getWindow(), mapWidth); // mapwidth is number of blocks X
            wnd.addScene(mapMaker);
            wnd.setScene(wnd.getArrayList().indexOf(mapMaker));
            this.setRunning(false);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void executeEvent(Event event) {
        switch (event.type) {
            case CLOSED:
                systemExit();
                break;
            case KEY_PRESSED:
                switch (event.asKeyEvent().key) {
                    case UP:
                        arrowKeyUp();
                        break;
                    case DOWN:
                        arrowKeyDown();
                        break;
                    case ESCAPE:
                        exitScene(this);
                        break;
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
    public void drawGraphics(RenderWindow window) {
        window.draw(background);

        for (int j = 0; j < blocks; j++) {
            for (int i = 0; i < blocks; i++) {
                window.draw(tileMap[i][j]);
            }
        }

        window.draw(textBackground);
        window.draw(userLevel);
    }
}
