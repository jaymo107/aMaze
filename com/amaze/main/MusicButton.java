package com.amaze.main;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds information associated with Music switch button which is located in the Main Menu.
 */
public class MusicButton extends Button {

    /* Music button for MenuScene. */
    public MusicButton(float xCord, float yCord, float width, float height, Window window, MenuScene menu) throws IOException {
        super(xCord, yCord, width, height, window, menu);

		getDefaultIcon().loadFromFile(Paths.get("res/menuGraphics/musicOn.png"));
        getSelectedIcon().loadFromFile(Paths.get("res/menuGraphics/musicOnsel.png"));
        this.setTexture(getDefaultIcon());
    }

    /* Music button for GameScene HUD */
	public MusicButton(float xCord, float yCord, float width, float height, Window window) throws IOException {
        super(xCord, yCord, width, height, window);

		getDefaultIcon().loadFromFile(Paths.get("res/menuGraphics/musicOn.png"));
        getSelectedIcon().loadFromFile(Paths.get("res/menuGraphics/musicOnsel.png"));
        getDefaultIcon().setSmooth(true);
        getSelectedIcon().setSmooth(true);
        this.setTexture(getDefaultIcon());
    }

	public void performAction() {}

}
