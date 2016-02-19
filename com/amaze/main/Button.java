package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;

import java.io.IOException;

/**
 * This is an abstract class which is responsible for managing all button in the menu.
 */
public abstract class Button extends RectangleShape {

    private Window window;
    private MenuScene menu;

    private boolean selected;

    private Texture defaultIcon = new Texture();
    private Texture selectedIcon = new Texture();

    /**
     * Construct a button with following parameters:
     *
     * @param xCord - x-coordinate of the button
     * @param yCord - y-coordinate of the button
     * @param width - width of the button
     * @param height - height of the button
     */
    public Button(float xCord, float yCord, float width, float height, Window window, MenuScene menu) throws IOException {
        this.window = window;
        this.menu = menu;

        Vector2f position = new Vector2f(xCord, yCord);
        Vector2f size = new Vector2f(width, height);

        this.setSize(size);
        this.setPosition(position);

        selected = false;
    }

	public Button(float xCord, float yCord, float width, float height, Window window) throws IOException {
        this.window = window;

        Vector2f position = new Vector2f(xCord, yCord);
        Vector2f size = new Vector2f(width, height);

        this.setSize(size);
        this.setPosition(position);

        selected = false;
    }

    /**
     * Confirms if current button is selected.
     * @return - true/false based on the result.
     */
    public boolean isSelected() {return selected;}

    /**
     * Change state of the button
     * @param selected - Whether it is selected/not selected.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        defaultIcon.setSmooth(true);
        selectedIcon.setSmooth(true);
        setIcon(selected ? selectedIcon : defaultIcon);
    }

	/**
     * Obtains the current window that this button is being displayed on.
     * @return the Window instance that represents the current Window.
     */
    public Window getWindow() { return window; }

	/**
	 * Obtains the current menu that the button is being placed
     * @return the MenuScene instance that represents the current menu
     */
    public MenuScene getMenu() { return menu; }

    public Texture getDefaultIcon() {return defaultIcon; }

    public Texture getSelectedIcon() { return selectedIcon; }

    public void setIcon(Texture t) {
        t.setSmooth(true);
        setTexture(t);
	}

    public abstract void performAction();

	public boolean isMouseOn() {
		Vector2i mousePos = Mouse.getPosition(getWindow());
		Vector2f buttonPos = getPosition();

		return (mousePos.x > buttonPos.x &&
				mousePos.y > buttonPos.y + 20 &&
				mousePos.x < buttonPos.x + getSize().x &&
				mousePos.y < buttonPos.y + getSize().y - 20
		);
	}

}
