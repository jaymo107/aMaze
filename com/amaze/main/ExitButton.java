package com.amaze.main;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds appropriate information about play button.
 */
public class ExitButton extends Button {

    /**
     * Construct a button with following parameters:
     *
     * @param xCord - x-coordinate of the button
     * @param yCord - y-coordinate of the button
     * @param width - width of the button
     * @param height - height of the button
     * @param window - reference to the main window
     */
    public ExitButton(float xCord, float yCord, float width, float height, Window window, MenuScene menu) throws IOException {
        super(xCord, yCord, width, height, window, menu);

        getDefaultIcon().loadFromFile(Paths.get("res/menuGraphics/exit.png"));
        getSelectedIcon().loadFromFile(Paths.get("res/menuGraphics/exitsel.png"));
        getDefaultIcon().setSmooth(true);
        getSelectedIcon().setSmooth(true);

        this.setTexture(getDefaultIcon());
    }

    /**
     * This function closes main window when it is invoked.
     */
    public void performAction() {
        System.out.println("Exit Button Pressed");
        getWindow().close();
        System.exit(0);
    }

}
