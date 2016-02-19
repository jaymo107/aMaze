package com.amaze.main;

import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

/**
 * This is an abstract class which defines every scene in the aMaze project.
 */
public abstract class Scene implements Displayable {

    private Window window;              	//Object reference to the window class.
    private String sceneTitle;              //Title which is displayed on the window frame.
    private boolean running = false;        //State

	private Music music;
	private MusicButton musicButton;

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
    public void executeEvent(Event event) {
		switch (event.type) {
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
				break;
			case MOUSE_BUTTON_PRESSED:
				switch (event.asMouseButtonEvent().button) {
					case LEFT:
						try {
							enterPressed();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
				}
				break;
			case MOUSE_WHEEL_MOVED:
				if (event.asMouseWheelEvent().delta < 0) {
					arrowKeyDown();
				} else {
					arrowKeyUp();
				}
				break;
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

	public static void pause(int time) {
		try {
			Thread.sleep(time);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void arrowKeyUp() {}

	public void arrowKeyDown() {}

	public void enterPressed() throws Exception {}

	public void musicPlaying(boolean state) {
		//if (music == null|| musicButton == null) return;

		if (!state) {
			music.pause();
			musicButton.setSelected(true);
		} else {
			musicButton.setSelected(false);
			music.play();
		}
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public Button getMusicButton() {
		return musicButton;
	}

	public void setMusicButton(MusicButton musicButton) {
		this.musicButton = musicButton;
	}

}
