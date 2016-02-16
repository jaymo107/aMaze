package com.amaze.main;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class InstructionScene extends Scene {

    private RectangleShape textBackground;
    private Texture backgroundImage = new Texture();
    private Window wnd;


    public InstructionScene(String sceneTitle, Window window) throws IOException {
        super(sceneTitle,window);
        wnd = window;

        //Create background
        new Background(window.getScreenWidth(), window.getScreenHeight());

        //Setting size of background shader (white part)
        Vector2f size = new Vector2f(window.getScreenWidth(), window.getScreenHeight());
        textBackground = new RectangleShape(size);
        textBackground.setPosition(0,0);

        //Loading of Background Image for Text box
        backgroundImage.loadFromFile(Paths.get("res/menuGraphics/instructions.png"));
        textBackground.setTexture(backgroundImage);
    }


    public void arrowKeyUp() {}

    public void arrowKeyDown() {}

    public void enterPressed() throws Exception {}

    public void executeEvent(Event event) {
        switch(event.type) {
            case CLOSED:
                systemExit();
                break;
            case KEY_PRESSED:
                switch (event.asKeyEvent().key) {
                    case UP: arrowKeyUp(); break;
                    case DOWN: arrowKeyDown(); break;
                    case ESCAPE: exitScene(this); break;
                    case RETURN:
                        try {
                            enterPressed();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            case JOYSTICK_BUTTON_PRESSED:

                System.out.println(event.asJoystickButtonEvent().button);

                switch (event.asJoystickButtonEvent().button) {

                    case 1: arrowKeyDown();break;
                    case 3: arrowKeyUp();break;
                    case 12: exitScene(this); break;
                    case 13:
                        try {
                            enterPressed();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
                break;
        }
    }

    public void drawGraphics(RenderWindow window) {
        window.draw(textBackground);
    }
}
