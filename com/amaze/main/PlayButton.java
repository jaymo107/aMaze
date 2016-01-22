package com.amaze.main;

import org.jsfml.graphics.*;

/**
 * This class holds appropriate information about play button.
 */
public class PlayButton extends Button {

    /**
     * Main constructor
     */
    public PlayButton(double xCord, double yCord, Window window, Image image) {

        super(xCord, yCord, window, image);
    }

    /**
     * Testing constructor
     */
    public PlayButton(double xCord, double yCord, Window window, Color color) {

        super(xCord, yCord, window, color);
    }
}
