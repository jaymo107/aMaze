package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class InstructionScene extends Scene {

    private RectangleShape textBackground;
    private Texture backgroundImage = new Texture();

	public InstructionScene(String sceneTitle, Window window) throws IOException {
        super(sceneTitle,window);

		//Create background
        new Background(window.getScreenWidth(), window.getScreenHeight());

        //Setting size of background shader (white part)
        Vector2f size = new Vector2f(window.getScreenWidth(), window.getScreenHeight());
        textBackground = new RectangleShape(size);
        textBackground.setPosition(0,0);

        //Loading of Background Image for Text box
        backgroundImage.loadFromFile(Paths.get("res/instructions/instructions.png"));
        backgroundImage.setSmooth(true);
        textBackground.setTexture(backgroundImage);
    }

    public void drawGraphics(RenderWindow window) {
        window.draw(textBackground);
    }

}
