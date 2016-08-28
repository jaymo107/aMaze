package com.amaze.main;

import org.jsfml.audio.Music;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

public class InstructionScene extends Scene {

    private RectangleShape textBackground;

	private Texture[] backgroundImage = new Texture[4];

    private int currentInstructionScene = 0;
    private Music music;
    private Background mainBackground;

	public InstructionScene(String sceneTitle, Window window, Music music, String background) throws IOException {
        super(sceneTitle,window);

        this.music = music;

		//Create background
        mainBackground = new Background(window.getScreenWidth(), window.getScreenHeight(),background);

        //Setting size of background shader (white part)
        Vector2f size = new Vector2f(window.getScreenWidth(), window.getScreenHeight());
        textBackground = new RectangleShape(size);
        textBackground.setPosition(0,0);

		for (int i = 0; i < backgroundImage.length; i++) {
			backgroundImage[i] = new Texture();
			backgroundImage[i].loadFromFile(Paths.get("res/instructions/instructionsp" + (i+1) +  ".png"));
			backgroundImage[i].setSmooth(true);
		}
		textBackground.setTexture(backgroundImage[0]);
    }

    public void drawGraphics(RenderWindow window) {
        window.draw(mainBackground);
        window.draw(textBackground);
    }

    public void executeEvent(Event event) {
        switch (event.type) {
            case CLOSED:
				music.stop();
				systemExit();
				break;
            case KEY_PRESSED:
                switch (event.asKeyEvent().key) {
                    case LEFT: arrowKeyLeft(); break;
                    case RIGHT: arrowKeyRight(); break;
                    case ESCAPE: exitScene(this); break;
                }
                break;
            case JOYSTICK_BUTTON_PRESSED:
                System.out.println(event.asJoystickButtonEvent().button);
                switch (event.asJoystickButtonEvent().button) {
                    case 0: arrowKeyLeft();break;
                    case 2: arrowKeyRight();break;
                    case 12: exitScene(this); break;
                }
                break;
        }
    }

    public void arrowKeyRight() {
        changeInstruction(1);
    }

	public void arrowKeyLeft() {
        changeInstruction(-1);
    }

	/**
	 * Changes the Instruction scene based-on the parameter that represents the number of page to increment or decrement
	 * @param i the integer represents the difference between the current page and the previous/next page.
	 */
    public void changeInstruction(int i) {
        currentInstructionScene += i;

		if (currentInstructionScene <= 0 || currentInstructionScene >= 4) {
			exitScene(this);
			return;
		}

		textBackground.setTexture(backgroundImage[currentInstructionScene]);
    }

}
