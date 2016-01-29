package com.amaze.main;

import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds appropriate information about map maker button.
 */
public class MapMakerButton extends Button {

    private Window window;
    private Texture defaultIcon;
    private Texture selectedIcon;

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

        defaultIcon = new Texture();
        defaultIcon.loadFromFile(Paths.get("res/menuGraphics/mapmake.png"));
        this.setTexture(defaultIcon);

        selectedIcon = new Texture();
        selectedIcon.loadFromFile(Paths.get("res/menuGraphics/mapmakesel.png"));
    }
    public Texture getDefaultIcon() {return defaultIcon;}
    public Texture getSelectedIcon() {return selectedIcon;}
    public void setIcon(Texture t) {

        setTexture(t);
    }
}
