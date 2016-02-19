package com.amaze.entities;

import com.amaze.main.GameScene;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents an avatar. There will only be one avatar in the maze. This will be the player.
 */
public class Avatar extends RectangleShape {

	  private int level;                  // Level that player is on
    private int score;                  // Holds player score
    private Vector2i oldTile;
    private int numberOfChargesPickedUp;
    private int timeSpentInVoid;
    private int imageNumber;
    private int maxImageNumber = 2;
    private Texture up;
    private Texture down;
    private Texture left;
    private Texture right;
    private String direction;

    /**
     * Produces and avatar for the game and picks the texture of the avatar randomly
     * from the res/avatars.
     * @param startX Start Pixel X location
     * @param startY Start Pixel Y location
     */

    public Avatar(int startX, int startY, int blockSize) {
        level = 0;
        score = 0;
        numberOfChargesPickedUp = 0;
        timeSpentInVoid = 0;
        up = new Texture();
        down = new Texture();
        left = new Texture();
        right = new Texture();


        direction = "DOWN";

        Random random = new Random();
        imageNumber = random.nextInt(this.maxImageNumber) + 1;

        try{
        //load in the directions
        up.loadFromFile(Paths.get("res/avatars/" + imageNumber+"/UP.png"));
        down.loadFromFile(Paths.get("res/avatars/" + imageNumber+"/DOWN.png"));
        left.loadFromFile(Paths.get("res/avatars/" + imageNumber+"/LEFT.png"));
        right.loadFromFile(Paths.get("res/avatars/" + imageNumber+"/RIGHT.png"));

        }catch(Exception e){
          e.printStackTrace();
        }

        this.setSize(new Vector2f((float)(blockSize / 1.2),(float)(blockSize / 1.2)));
        this.setPosition(startX,startY);
        this.setTexture(down);
        this.oldTile = new Vector2i(0,0);
        System.out.println("");
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

    public int getImageNumber(){
      return this.imageNumber;
    }

    public void updateImageDirection(String direction){

      switch(direction){
        case "UP":
          this.setTexture(up);
          break;
        case "DOWN":
          this.setTexture(down);
          break;
        case "LEFT":
          this.setTexture(left);
          break;
        case "RIGHT":
          this.setTexture(right);
          break;
         default:
            this.setTexture(up);
      }

    }

    public int getTimeSpentInVoid() {
        return timeSpentInVoid;
    }

}
