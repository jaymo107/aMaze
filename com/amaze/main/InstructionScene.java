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

    private RectangleShape textBackground1;
    private Texture backgroundImage1 = new Texture();
    private Texture backgroundImage2 = new Texture();
    private Texture backgroundImage3 = new Texture();
    private int currentInstructionScene = 1;
    private Music music;
    private Background mainBackground;

	public InstructionScene(String sceneTitle, Window window, Music music, String background) throws IOException {
        super(sceneTitle,window);

        this.music = music;

		//Create background
        mainBackground = new Background(window.getScreenWidth(), window.getScreenHeight(),background);

        //Setting size of background shader (white part)
        Vector2f size = new Vector2f(window.getScreenWidth(), window.getScreenHeight());
        textBackground1 = new RectangleShape(size);
        textBackground1.setPosition(0,0);


        //Loading of Background Image for Text box
        backgroundImage1.loadFromFile(Paths.get("res/instructions/instructionsp1.png"));
        backgroundImage1.setSmooth(true);
        textBackground1.setTexture(backgroundImage1);

        backgroundImage2.loadFromFile(Paths.get("res/instructions/instructionsp2.png"));
        backgroundImage2.setSmooth(true);

        backgroundImage3.loadFromFile(Paths.get("res/instructions/instructionsp3.png"));
        backgroundImage3.setSmooth(true);
    }

    public void drawGraphics(RenderWindow window) {
        window.draw(mainBackground);
        window.draw(textBackground1);
    }

    public void executeEvent(Event event) {

        switch (event.type) {
            case CLOSED:
                music.stop();
                systemExit();
                break;
            case KEY_PRESSED:
                switch (event.asKeyEvent().key) {
                    case LEFT:
                        arrowKeyLeft();
                        break;
                    case RIGHT:
                        arrowKeyRight();
                        break;
                    case ESCAPE:
                        exitScene(this);
                        break;
                }
                break;
        }
    }

    public void arrowKeyRight() {

        changeBackground(+1);
    }
    public void arrowKeyLeft() {
        changeBackground(-1);
    }
    public void changeBackground(int i) {

        currentInstructionScene = currentInstructionScene + i;

        textBackground1.setTexture(backgroundImage2);

        if(currentInstructionScene <= 0) {

            exitScene(this);
        }

        if(currentInstructionScene == 1) {

            textBackground1.setTexture(backgroundImage1);
        }
        if(currentInstructionScene == 2) {

            textBackground1.setTexture(backgroundImage2);
        }
        if(currentInstructionScene == 3) {

            textBackground1.setTexture(backgroundImage3);
        }
        if(currentInstructionScene >= 4) {

            exitScene(this);
        }
    }

}
