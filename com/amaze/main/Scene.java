package com.amaze.main;

import org.jsfml.graphics.Color;
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
     */
    public void display() {
		setRunning(true);
		getWindow().setTitle(getSceneTitle());

		while (isRunning()) try {
			getWindow().clear(Color.BLACK);
			drawGraphics(getWindow());
			for (Event event : getWindow().pollEvents()) {
				executeEvent(event);
			}
			getWindow().display();
		} catch (Exception e) {
			setRunning(false);
		}
	}

    /**
     * When invoked, this function is responsible for executing appropriate
     * set of steps depending on the event.
     * @param event - input based event (e.g arrow key up)
     */
    public abstract void executeEvent(Event event);

	public abstract void drawGraphics(RenderWindow window);

    public String getSceneTitle() {return sceneTitle;}

    public boolean isRunning() {return running;}

    public void setRunning(boolean bool) {this.running = bool;}

    public Window getWindow() { return window; }

    public void systemExit() {
        window.close();
        System.exit(0);
    }

    public void exitScene(Scene currentScene) {
        window.setScene(0);
        window.getScene(0).display();
        currentScene.setRunning(false);
    }

}
