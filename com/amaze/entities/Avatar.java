package com.amaze.entities;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import com.amaze.main.GameScene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Produces and avatar for the game and picks the texture of the avatar randomly
     * from the res/avatars.
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

        List<String> avatarFileNames = new ArrayList<String>();

        File[] files = new File("res/avatars").listFiles();

        for(File file: files) {

            if (file.isFile()) {

                avatarFileNames.add(file.getName());
            }
        }

        Random random = new Random();
        String randomImagePath = avatarFileNames.get(random.nextInt(avatarFileNames.size() - 1 + 1) + 0);
        try{
            t.loadFromFile(Paths.get("res/avatars/" + randomImagePath));
        }catch (IOException e){
            System.out.println("There is either no avatar in the folder or a hidden file that needs to be deleted");
        }
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
