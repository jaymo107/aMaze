package com.amaze.main;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

public class LevelMenuScene extends Scene {

    Text userLevel;
    Background background;
    RectangleShape textBackround;
    Texture backgroundImage = new Texture();
    Window window;

    static final int MAX_LEVEL = 20;
    static final int MIN_LEVEL = 1;


    int userLevelNumber = 1;

    public LevelMenuScene(String sceneTitle, Window window) throws IOException {

        super(sceneTitle,window);

        this.window = window;

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
        textBackround = new RectangleShape(size);
        textBackround.setPosition(window.getScreenWidth()/12F, window.getScreenHeight()/2.5F);


        backgroundImage.loadFromFile(Paths.get("res/menuGraphics/Wall.png"));
        textBackround.setTexture(backgroundImage);

        //Create text
        userLevel = new Text("Level 1", maze, 170);
        userLevel.setColor(Color.BLACK);
        userLevel.setStyle(Text.BOLD);
        userLevel.setOrigin((window.getScreenWidth()/9.5F) * -1, (window.getScreenHeight()/3F) * -1);

        System.out.println(window.getScreenHeight());
        System.out.println(window.getScreenWidth());

    }

    @Override
    public void display(RenderWindow window) {
        setRunning(true);
        window.setTitle(getSceneTitle());

        while(this.isRunning()) try {
            window.clear(Color.WHITE);
            draw(window);

            for (Event event : window.pollEvents()) {
                executeEvent(event);
            }
            window.display();

        }catch (Exception e) {
            setRunning(false);
        }
    }
    /**
     * This function is triggered when user presses arrow key up.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyUp() {

        if(userLevelNumber < MAX_LEVEL) {

            this.userLevelNumber++;
        }
        else {

            this.userLevelNumber = MAX_LEVEL;
        }
        userLevel.setString("Level " + userLevelNumber);
    }

    /**
     * This function is triggered when user presses arrow key down.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyDown() {

        if(userLevelNumber == MIN_LEVEL) {

            this.userLevelNumber = MIN_LEVEL;
        }
        else {

            this.userLevelNumber--;
        }
        userLevel.setString("Level " + userLevelNumber);
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

        GameScene game = new GameScene("Game", window, level.getSizeOfMaze(), Driver.BLOCK_SIZE, level.getLevel());

        window.addScenes(game);

        getWindow().setScene(2);
        getWindow().getScene(2).display(getWindow());
    }

    @Override
    public void executeEvent(Event event) {
        switch(event.type) {
            case CLOSED:
                getWindow().close();
                System.exit(0);
                break;
            case KEY_PRESSED:
                switch (event.asKeyEvent().key) {
                    case UP: arrowKeyUp(); break;
                    case DOWN: arrowKeyDown(); break;
                    case RETURN:
                        try {
                            enterPressed();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
                break;
        }
    }
    public String getUserLevelNumber()
    {
        return String.valueOf(userLevelNumber);
    }


    public void draw(RenderWindow window) {

        window.draw(background);
        window.draw(textBackround);
        window.draw(userLevel);
    }
}
