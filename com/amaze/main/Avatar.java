package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.nio.file.Paths;

/**
 * Represents an avatar. There will only be one avatar in the maze. This will be the player.
 */
public class Avatar extends RectangleShape {

    private int currentX;
    private int currentY;
    private int level;          // Level that player is on
    private int score;          // Holds player score
    private Vector2i oldTile;

    /**
     * Basically does some initial housekeeping.
     * @param startX Start Pixel X location
     * @param startY Start Pixel Y location
     * @throws Exception If fails to load texture
     */

    public Avatar(int startX, int startY, int blockSize) throws Exception {
        level = 0;
        score = 0;
        Texture t = new Texture();
        t.loadFromFile(Paths.get("res/face.png"));
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
    
    public Vector2i tilePosition(){
      return new Vector2i(Math.round(this.getPosition().x / GameScene.blockSize),
          Math.round(this.getPosition().y / GameScene.blockSize));
    }
    
    /**
     * Event for when the user has moved tiles
     * @return
     */
    public boolean hasMovedTiles(){
      if(!this.oldTile.equals(this.tilePosition())){
        this.oldTile = this.tilePosition();
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
}
