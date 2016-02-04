package com.amaze.main;

import com.amaze.MapMaker.MapMakerScene;
import com.amaze.levelmaker.LevelMaker;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds appropriate information about map maker button.
 */
public class MapMakerButton extends Button {

    Window window;

    /**
     * Construct a button with following parameters:
     *
     * @param xCord  - x-coordinate of the button
     * @param yCord  - y-coordinate of the button
     * @param width  - width of the button
     * @param height - height of the button
     * @param window - reference to the main window
     */
    public MapMakerButton(float xCord, float yCord, float width, float height, Window window, MenuScene menu) throws IOException {
        super(xCord, yCord, width, height, window, menu);

        this.window = window;

        getDefaultIcon().loadFromFile(Paths.get("res/menuGraphics/mapmake.png"));
        getSelectedIcon().loadFromFile(Paths.get("res/menuGraphics/mapmakesel.png"));

        this.setTexture(getDefaultIcon());
    }

	/**
     * This function displays the Map Maker when called
     */
    public void performAction() {
        System.out.println("Level Maker Button Pressed");
        //new LevelMaker(30,30);
        try {

            MapMakerScene mapMaker = new MapMakerScene("Level Menu", window, 30, 30);
            window.addScenes(mapMaker);
            window.setScene(window.getArrayList().indexOf(mapMaker));
            getMenu().setRunning(false);
        } catch (IOException e) {

            e.printStackTrace();
        }
//        getWindow().setScene(window.getCurrentScene()+1);
//        getMenu().setRunning(false);
    }

}
