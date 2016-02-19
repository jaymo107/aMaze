package com.amaze.main;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds appropriate information about play button.
 */
public class PlayButton extends Button {

    /**
     * Construct a button with following parameters:
     *
     * @param xCord - x-coordinate of the button
     * @param yCord - y-coordinate of the button
     * @param width - width of the button
     * @param height - height of the button
     * @param window - reference to the main window
     */
    public PlayButton(float xCord, float yCord, float width, float height, Window window, MenuScene menu) throws IOException {
        super(xCord, yCord, width, height, window, menu);

        getDefaultIcon().loadFromFile(Paths.get("res/menuGraphics/play.png"));
        getSelectedIcon().loadFromFile(Paths.get("res/menuGraphics/playsel.png"));
        getDefaultIcon().setSmooth(true);
        getSelectedIcon().setSmooth(true);

        this.setTexture(getDefaultIcon());
    }

    /**
     * This function changes the scene to LevelMenuScene when called.
     */
    public void performAction() {
        System.out.println("Play Button Pressed");
        try {
            LevelMenuScene levelMenu = new LevelMenuScene("Level Menu", getWindow());
			getWindow().addScene(levelMenu);
			getWindow().setScene(getWindow().getArrayList().indexOf(levelMenu));
            getMenu().setRunning(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
