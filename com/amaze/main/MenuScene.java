package com.amaze.main;
import com.amaze.levelmaker.*;
import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

import java.io.IOException;

/**
 * This class will handle the menu and buttons associated with it.
 */
public class MenuScene extends Scene {

    private final int NUMBER_OF_ITEMS = 3;                  // Number of items available to select in the menu
    private Button button[] = new Button[NUMBER_OF_ITEMS];  // Array which holds buttons(items).
    private int currentButton = 0;                          // Track currently selected item in the menu.
    private Window window;

    /**
     * Constructs buttons to be displayed on the main window.
     *
     * @param window - object reference to the main window.
     */

    public MenuScene(String sceneTitle, Window window) throws IOException {

        super(sceneTitle);

        this.window = window;

        button[0] = new PlayButton(window.getScreenWidth() / 3.75F, (window.getScreenHeight() / NUMBER_OF_ITEMS), 400, 125,  window, this);
        button[1] = new MapMakerButton(window.getScreenWidth() / 3.75F, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 1.6F, 400, 125,  window, this);
        button[2] = new ExitButton(window.getScreenWidth() / 3.75F, (window.getScreenHeight() / NUMBER_OF_ITEMS) * 2.2F, 400,125,  window, this);
    }

    /**
     * This function is triggered when user presses arrow key up.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyUp() {

        if(currentButton == 0) {

            button[currentButton].setSelected(true);
            button[currentButton].setIcon(button[currentButton].getSelectedIcon());
            currentButton = 0;
        } else if (currentButton <= NUMBER_OF_ITEMS) {
            button[currentButton].setSelected(false);
            button[currentButton].setIcon(button[currentButton].getDefaultIcon());
            button[currentButton - 1].setSelected(true);
            button[currentButton - 1].setIcon(button[currentButton - 1].getSelectedIcon());
            currentButton--;
        }
    }

    /**
     * This function is triggered when user presses arrow key down.
     * Every time this function is called, next item on the menu will be selected.
     * Corresponding boolean variable, as well as the color of the item will change.
     */
    public void arrowKeyDown() {

        if(currentButton == NUMBER_OF_ITEMS - 1) {

            button[currentButton].setSelected(true);
            button[currentButton].setIcon(button[currentButton + 1].getSelectedIcon());
            currentButton = NUMBER_OF_ITEMS - 1;
        } else {

            button[currentButton].setSelected(false);
            button[currentButton].setIcon(button[currentButton].getDefaultIcon());
            button[currentButton + 1].setSelected(true);
            button[currentButton + 1].setIcon(button[currentButton + 1].getSelectedIcon());
            currentButton++;
        }
    }

    /**
     * This function is triggered when user presses enter button.
     * This function will check which button is currently selected.
     * Based on the button, a specific function will be invoked.
     */
    public void enterPressed() {

        if (button[0].isSelected()) {

            System.out.println("Play Button Pressed");
            ((PlayButton)button[0]).startNewGame();
        }
        if (button[1].isSelected()) {

            System.out.println("Level Maker Button Pressed");
            new LevelMaker(30,30);
        }
        if (button[2].isSelected()) {

            System.out.println("Exit Button Pressed");
            ((ExitButton)button[3]).closeWindow();
        }
    }

    public Button[] getButtons() { return button; }

    /**
     * This function handles event based on their type.
     *
     * @param event - event to be handled.
     * test.
     */
    public void executeEvent(Event event) {
        switch(event.type) {
            case CLOSED:
                window.close();
                System.exit(0);
                break;
            case KEY_PRESSED:
                if(event.asKeyEvent().key == Keyboard.Key.UP){

                    this.arrowKeyUp();
                }else if(event.asKeyEvent().key == Keyboard.Key.DOWN){

                    this.arrowKeyDown();
                }else if(event.asKeyEvent().key == Keyboard.Key.RETURN){

                    this.enterPressed();
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
        while(this.isRunning()) try {

			window.clear(Color.WHITE);
			drawMenuItems(window);

			for (Event event : window.pollEvents()) {

                //Different behaviour depending on
                this.executeEvent(event);
            }
            window.display();

        }catch (Exception e) {
            this.setRunning(false);
        }
    }

    /**
     * Draws items associated with MenuScene in the main Window.
     * @param window - reference to the window.
     */
    private void drawMenuItems(RenderWindow window) {
        for(Button b: getButtons()) {
            window.draw(b);
        }
    }
}
