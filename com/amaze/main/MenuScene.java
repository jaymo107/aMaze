package com.amaze.main;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class will handle the menu and buttons associated with it.
 */
public class MenuScene extends Scene {

    private final int NUMBER_OF_ITEMS = 3;                  // Number of items available to select in the menu
    private Button buttons[] = new Button[NUMBER_OF_ITEMS];  // Array which holds buttons(items).
    private int currentButton = 0;                          // Track currently selected item in the menu.
    private Title title;
    private Background background;
    private Music music;

    private boolean playing = false;

    /**
     * Constructs buttons to be displayed on the main window.
     *
     * @param sceneTitle - title of the Scene
     * @param window - object reference to the main window.
     */

    public MenuScene(String sceneTitle, Window window) throws IOException {
        super(sceneTitle, window);

        background = new Background(window.getScreenWidth(), window.getScreenHeight());
        title = new Title(800, 300);

        buttons[0] = new PlayButton(window.getScreenWidth() / 3.75F, (window.getScreenHeight() / NUMBER_OF_ITEMS), 400, 125,  window, this);
        buttons[1] = new MapMakerButton(window.getScreenWidth() / 3.75F, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 1.6F, 400, 125,  window, this);
        buttons[2] = new ExitButton(window.getScreenWidth() / 3.75F, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 2.2F, 400,125,  window, this);

        music = new Music();
        try {
            music.openFromFile(Paths.get("res/music/gs3.wav"));
        } catch (IOException e) {
            System.out.println("There was a problem loading the background music.");
        }

        buttons[0].setSelected(true);
    }

    /**
     * This function is triggered when user presses arrow key up.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyUp() {
        if(currentButton == 0) {
            buttons[currentButton].setSelected(true);
        } else {
            buttons[currentButton].setSelected(false);
            buttons[--currentButton].setSelected(true);
        }
    }

    /**
     * This function is triggered when user presses arrow key down.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyDown() {
        if(currentButton == NUMBER_OF_ITEMS - 1) {
            buttons[currentButton].setSelected(true);
        } else {
            buttons[currentButton].setSelected(false);
            buttons[++currentButton].setSelected(true);
        }
    }

    /**
     * This function is triggered when user presses enter button.
     * This function will check which button is currently selected.
     * Based on the button, a specific function will be invoked.
     */
    public void enterPressed() {
        for (Button b: buttons) {
            if (b.isSelected()) {
                b.performAction();

                if (b instanceof PlayButton) {
                    playing = true;
                    music.stop();
                }
            }
        }
    }

    public Button[] getButtons() { return buttons; }

    /**
     * This function handles event based on their type.
     *
     * @param event - event to be handled.
     * test.
     */
    public void executeEvent(Event event) {
        switch(event.type) {
            case CLOSED:
                getWindow().close();
                System.exit(0);
                break;
            case KEY_PRESSED:
                switch (event.asKeyEvent().key) {
                    case UP: arrowKeyUp(); break;
                    case DOWN: arrowKeyDown(); break;
                    case RETURN: enterPressed(); break;
                }
                break;
        }
    }

    /**
     * Displays all the items associated with the Menu on the primary window.
     * @param window - primary window on which scenes are displayed
     */
    public void display(RenderWindow window) {
        setRunning(true);
        window.setTitle(getSceneTitle());
//        if (!playing) {
//            music.play();
//            music.setLoop(true);
//        }

        while(this.isRunning()) try {
			window.clear(Color.WHITE);
			drawMenuItems(window);

			for (Event event : window.pollEvents()) {
                //Different behaviour depending on
                executeEvent(event);
            }
            window.display();

        }catch (Exception e) {
            setRunning(false);
        }
    }

    /**
     * Draws items associated with MenuScene in the main Window.
     * @param window - reference to the window.
     */
    private void drawMenuItems(RenderWindow window) {

        window.draw(background);

        for(Button b: getButtons()) {
            window.draw(b);
        }
        window.draw(title);
    }

}
