package com.amaze.main;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

public class LevelMenuScene extends Scene {

    Text userLevel;
    int userLevelNumber = 0;

    public LevelMenuScene(String sceneTitle, Window window) {

        super(sceneTitle,window);

        //Create Font
        Font arial = new Font();
        try {
            arial.loadFromFile(Paths.get("res/fonts/Arial.ttf"));
        } catch (IOException e){

            System.out.println("Could not load the font!");
        }

        //Create text
        userLevel = new Text("Level: 1", arial, 48);
        userLevel.setColor(Color.BLACK);
        userLevel.setStyle(Text.BOLD);
        userLevel.setOrigin((window.getScreenWidth()/2)*-1, (window.getScreenHeight()/2)*-1);

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
        this.userLevelNumber++;
        userLevel.setString("Level: " + userLevelNumber);
    }

    /**
     * This function is triggered when user presses arrow key down.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyDown() {
        this.userLevelNumber--;
        userLevel.setString("Level: " + userLevelNumber);
    }

    /**
     * This function is triggered when user presses enter button.
     * This function will check which button is currently selected.
     * Based on the button, a specific function will be invoked.
     */
    public void enterPressed() {
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
                    case RETURN: enterPressed(); break;
                }
                break;
        }
    }
    public int getUserLevelNumber()
    {
        return userLevelNumber;
    }


    public void draw(RenderWindow window) {

        window.draw(userLevel);
    }
}
