package com.amaze.main;

import com.amaze.main.Display.Displayable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

/**
 * This is an abstract class which defines every scene in the aMaze project.
 */
public abstract class Scene implements Displayable {

    private String sceneTitle;              //Title which is displayed on the window frame.
    private boolean running = false;        //State

    public Scene(String sceneTitle) {

        this.sceneTitle = sceneTitle;
    }

    /**
     * When invoked, this function displays current scene on the window.
     * @param window - primary window on which scenes are displayed
     */
    public void display(RenderWindow window) {}

    /**
     * When invoked, this function is responsible for executing appropriate
     * set of steps depending on the event.
     *
     * @param event - input based event (e.g arrow key up)
     */
    public void executeEvent(Event event) {}


    public String getSceneTitle() {return sceneTitle;}
    public void setRunning(boolean bool) {this.running = bool;}
    public boolean isRunning() {return running;}
}
