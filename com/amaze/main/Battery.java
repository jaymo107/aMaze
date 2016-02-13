package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Represents a battery
 */
public class Battery extends RectangleShape {

    public static final int MAX = 6;

    private int chargeLevel; //Ranges from 0 to 6
    private Texture texture;

    /**
     *
     * @param resX Size of window X
     * @param resY Size of window Y
     * @param defaultLevel Represents the level the battery will start at
     */

    public Battery(int resX, int resY, int defaultLevel) throws IOException{
        this.setPosition(resX - (resX / 2) - 50,resY - 60);
        this.setSize(new Vector2f(100,80));
        this.chargeLevel = defaultLevel;
        this.texture = new Texture();
        changeChargeLevel(chargeLevel);
    }

    /**
     * Changes charge level based on input number
     * Changes the image shown too
     * @param level the level of the battery
     */

    public void changeChargeLevel(int level) {
        try{
			texture.loadFromFile(Paths.get("res/images/battery/" + Integer.toString(level) + ".png"));
            this.setTexture(texture);
        }catch (Exception e){
            System.out.println("There was a problem with loading the battery icon");
        }
    }

    public int getChargeLevel() {
        return chargeLevel;
    }

    /**
     * Increase battery charge level by a specified amount
     * @param level the level the battery will increase
     */

    public void increaseChargeLevel(int level) {
        if (chargeLevel >= MAX) return;

        chargeLevel += level;
        changeChargeLevel(chargeLevel);
    }

    /**
     * Decrease battery charge level by a specified amount
     * @param level the level the battery will decrease
     */

    public void decreaseChargeLevel(int level) {
        if (chargeLevel <= 1) return;

        chargeLevel -= level;
        changeChargeLevel(chargeLevel);
    }
}
