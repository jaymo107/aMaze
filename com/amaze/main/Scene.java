package com.amaze.main;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;


public abstract class Scene implements Displayable {

    private String sceneTitle;
    private boolean running = false;

    public Scene(String sceneTitle) {

        this.sceneTitle = sceneTitle;
    }

    public void display(RenderWindow window) {}
    public void executeEvent(Event event) {}
    public String getSceneTitle() {return sceneTitle;}
    public void setRunning(boolean bool) {this.running = bool;}
    public boolean isRunning() {return running;}
}
