package com.amaze.entities;

import com.amaze.main.GameScene;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.nio.file.Paths;
import java.util.Random;

/**
 * Represents an avatar. There will only be one avatar in the maze. This will be the player.
 */
public class Avatar extends RectangleShape {

	private Texture[] direction = new Texture[4];

	public static final int MAX_IMAGE_NUMBER = 2;

    /**
     * Produces and avatar for the game and picks the texture of the avatar randomly
     * from the res/avatars.
     * @param startX Start Pixel X location
     * @param startY Start Pixel Y location
     */

    public Avatar(int startX, int startY, int blockSize) {
		Random random = new Random();
		int imageNumber = random.nextInt(MAX_IMAGE_NUMBER) + 1;

        try{
			for (int i = 0; i < direction.length; i++) {
				direction[i] = new Texture();
			}

			//load in the directions
			direction[0].loadFromFile(Paths.get("res/avatars/" + imageNumber + "/UP.png"));
			direction[1].loadFromFile(Paths.get("res/avatars/" + imageNumber + "/DOWN.png"));
			direction[2].loadFromFile(Paths.get("res/avatars/" + imageNumber + "/LEFT.png"));
			direction[3].loadFromFile(Paths.get("res/avatars/" + imageNumber + "/RIGHT.png"));

        }catch(Exception e){
          e.printStackTrace();
        }

        setSize(new Vector2f((float)(blockSize / 1.2),(float)(blockSize / 1.2)));
        setPosition(startX,startY);
        setTexture(direction[1]);
    }

	public Vector2i getTilePosition() {
		return new Vector2i(Math.round(this.getPosition().x / GameScene.getBlockSize()), Math.round(this.getPosition().y / GameScene.getBlockSize()));
    }

    public void updateImageDirection(String dir){
		switch(dir){
			case "UP": setTexture(direction[0]); break;
			case "DOWN": setTexture(direction[1]); break;
			case "LEFT": setTexture(direction[2]); break;
			case "RIGHT": setTexture(direction[3]); break;
			default: setTexture(direction[0]);
		}
    }

}
