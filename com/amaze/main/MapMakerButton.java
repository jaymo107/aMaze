package com.amaze.main;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds appropriate information about map maker button.
 */
public class MapMakerButton extends Button {

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

        getDefaultIcon().loadFromFile(Paths.get("res/menuGraphics/mapmake.png"));
        getSelectedIcon().loadFromFile(Paths.get("res/menuGraphics/mapmakesel.png"));
        getDefaultIcon().setSmooth(true);
        getSelectedIcon().setSmooth(true);

        this.setTexture(getDefaultIcon());
    }

	/**
     * This function displays the Map Maker when called
     */
    public void performAction() {
        System.out.println("Level Maker Button Pressed");

        try {
            GridSelectionScene gridSelectionScene = new GridSelectionScene("Grid Selection", getWindow());
			getWindow().addScene(gridSelectionScene);
			getWindow().setScene(getWindow().getArrayList().indexOf(gridSelectionScene));
            getMenu().setRunning(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
