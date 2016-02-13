package com.amaze.entities;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import com.amaze.main.GameScene;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Represents an avatar. There will only be one avatar in the maze. This will be the player.
 */
public class Avatar extends RectangleShape {

    private int currentX;
    private int currentY;
    private int level;                  // Level that player is on
    private int score;                  // Holds player score
    private Vector2i oldTile;
    private int numberOfChargesPickedUp;
    private int timeSpentInVoid;

    /**
     * Basically does some initial housekeeping.
     * @param startX Start Pixel X location
     * @param startY Start Pixel Y location
     * @throws Exception If fails to load texture
     */

    public Avatar(int startX, int startY, int blockSize) {
        level = 0;
        score = 0;
        numberOfChargesPickedUp = 0;
        timeSpentInVoid = 0;
        Texture t = new Texture();

        try{
            t.loadFromFile(Paths.get("res/face.png"));
        }catch (IOException e){
            System.out.println("Error loading avatar image");
        }

        this.setSize(new Vector2f((float)(blockSize / 1.2),(float)(blockSize / 1.2)));
        this.setPosition(startX,startY);
        this.setTexture(t);
        this.oldTile = new Vector2i(0,0); 
    }

    /**
     * Allows you to change level of a player. Changes the picture on the avatar.
     * @param levelNumber Level that you want to set the player
     */
    public void changeLevel(int levelNumber) {

    }
    
    public Vector2i getTilePosition() {
		return new Vector2i(Math.round(this.getPosition().x / GameScene.getBlockSize()), Math.round(this.getPosition().y / GameScene.getBlockSize()));
    }
    
    /**
     * Event for when the user has moved tiles
     */
    public boolean hasMovedTiles(){
		if (!this.oldTile.equals(this.getTilePosition())) {
			this.oldTile = this.getTilePosition();
			return true;
		}
		return false;
    }


    public int getLevel(){
        return level;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    /* Void Options */
    public int getNumberOfChargesPickedUp() {
        return numberOfChargesPickedUp;
    }

    public void increaseChargesCount() {
        System.out.println("Picked up charge");
        numberOfChargesPickedUp = numberOfChargesPickedUp + 1;
    }

    /* Charge Options */
    public void setTimeSpentInVoid(int timeSpentInVoid) {
        this.timeSpentInVoid = timeSpentInVoid;
    }

    public int getTimeSpentInVoid() {

        return timeSpentInVoid;
    }
}
