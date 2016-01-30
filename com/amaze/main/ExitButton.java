package com.amaze.main;

import org.jsfml.graphics.*;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds appropriate information about play button.
 */
public class ExitButton extends Button {

    private Window window;
    private Texture defaultIcon;
    private Texture selectedIcon;

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

        this.window = window;

        defaultIcon = new Texture();
        defaultIcon.loadFromFile(Paths.get("res/menuGraphics/exit.png"));
        this.setTexture(defaultIcon);

        selectedIcon = new Texture();
        selectedIcon.loadFromFile(Paths.get("res/menuGraphics/exitsel.png"));
    }

    public Texture getDefaultIcon() {return defaultIcon;}

    public Texture getSelectedIcon() {return selectedIcon;}

    public void setIcon(Texture t) {setTexture(t);}

    /**
     * This function closes main window when it is invoked.
     */
    public void performAction() {
        System.out.println("Exit Button Pressed");
        window.close();
        System.exit(0);
    }

}
