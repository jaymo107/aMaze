package com.amaze.main;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

/**
 * This is an abstract class which defines every scene in the aMaze project.
 */
public abstract class Scene implements Displayable {

    private Window window;              //Object reference to the window class.

    private String sceneTitle;              //Title which is displayed on the window frame.
    private boolean running = false;        //State

    public Scene(String sceneTitle, Window window) {
        this.sceneTitle = sceneTitle;
        this.window = window;
    }

    /**
     * When invoked, this function displays current scene on the window.
     * @param window - primary window on which scenes are displayed
     */
    public abstract void display(RenderWindow window);

    /**
     * When invoked, this function is responsible for executing appropriate
     * set of steps depending on the event.
     * @param event - input based event (e.g arrow key up)
     */
    public abstract void executeEvent(Event event);

    public String getSceneTitle() {return sceneTitle;}

    public boolean isRunning() {return running;}

    public void setRunning(boolean bool) {this.running = bool;}

    public Window getWindow() { return window; }

}
