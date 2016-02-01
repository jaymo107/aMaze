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
    private int level = 7; //Ranges from 0 to 7
    private Texture texture;

    /**
     *
     * @param resX Size of window X
     * @param resY Size of window Y
     */
    public Battery(int resX, int resY) throws IOException{
        this.setPosition(resX - 50,0 + 22);
        this.setSize(new Vector2f(50,30));
        this.texture = new Texture();
        texture.loadFromFile(Paths.get("res/images/battery/battery2.png"));
        this.setTexture(texture);

    }

    public void changeChargeLevel(int level) throws Exception{
        //Texture texture = new Texture(); //tileTexture[0].loadFromFile(Paths.get("res/images/wall.png"));

        switch(level){
            case 0:
                texture.loadFromFile(Paths.get("res/images/battery/battery1.png"));
                break;
            case 1:
                texture.loadFromFile(Paths.get("res/images/battery/battery1.png"));
                break;
            case 2:
                texture.loadFromFile(Paths.get("res/images/battery/battery2.png"));
                break;
            case 3:
                texture.loadFromFile(Paths.get("res/images/battery/battery3.png"));
                break;
            case 4:
                texture.loadFromFile(Paths.get("res/images/battery/battery4.png"));
                break;
            case 5:
                texture.loadFromFile(Paths.get("res/images/battery/battery5.png"));
                break;
            case 6:
                texture.loadFromFile(Paths.get("res/images/battery/battery6.png"));
                break;
            case 7:
                texture.loadFromFile(Paths.get("res/images/battery/battery7.png"));
                break;
            default:
                texture.loadFromFile(Paths.get("res/images/battery/battery7.png"));
                break;
        }
        this.setTexture(texture);
    }
}
