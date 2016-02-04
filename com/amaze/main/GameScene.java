package com.amaze.main;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class will Game and all the elements associated with it.
 */
public class GameScene extends Scene {

    private int blockSize;              //Size of each block. W and H
    private int blockX;                 //Number of blocks in X direction
    private int blockY;                 //Number of blocks in Y direction
    private Tile tileMap[][];           //Represents the maze
    private Avatar player;              //Represents the player(avatar)

    private Music music;                //Background music

    /**
     * This constructor creates an instance of a GameScene.
     * Within this class all the game logic should be handled.
     * If needed, supplement with additional classes for OO.
     *
     * @param sceneTitle - sets title of the window.
     *                   set to "aMaze" when creating
     *                   an instance of the GameScene.
     */

    public GameScene(String sceneTitle, Window window, int blocks, int blockSize, Tile.BlockType[][] level) throws Exception {
        super(sceneTitle, window);

        this.blockSize = blockSize;

        blockX = level.length;
        blockY = level.length;

        tileMap = new Tile[blocks][blocks];
        player = new Avatar(0,0);

        /* Cache textures before we start using them in order to increase performance */
        Texture tileTexture[] = new Texture[7];
        for (int i = 0; i < tileTexture.length; i++) {
            tileTexture[i] = new Texture();
        }

        tileTexture[0].loadFromFile(Paths.get("res/images/wall.png"));
        tileTexture[1].loadFromFile(Paths.get("res/images/floor.png"));
        tileTexture[2].loadFromFile(Paths.get("res/images/door.png"));
        tileTexture[3].loadFromFile(Paths.get("res/images/blue.png"));
        tileTexture[4].loadFromFile(Paths.get("res/images/blue.png"));
        tileTexture[5].loadFromFile(Paths.get("res/images/void.png"));
        tileTexture[6].loadFromFile(Paths.get("res/images/charge.png"));

        /* Create new instances of tiles */
        for (int j = 0; j < blocks; j++) {
            for (int i = 0; i < blocks; i++) {
                tileMap[i][j] = new Tile("",translateX(i),translateY(j),this.blockSize,this.blockSize,level[i][j], tileTexture);
            }
        }

        /* Load background music */
        music = new Music();
        try {
            music.openFromFile(Paths.get("res/music/gs2.wav"));
        } catch (IOException e) {
            System.out.println("There was a problem loading the background music \n Error: " + e);
        }
    }

    /**(
     * Translates X to raw pixels
     * @param blockX Block number
     * @return Raw pixel value
     */

    public int translateX(int blockX){
        return blockSize * blockX;
    }

    /**
     * Translates Y to raw pixels
     * @param blockY Block number
     * @return Raw pixel value
     */

    public int translateY(int blockY){
        return blockSize * blockY;
    }

    /**
     * When called, this function displays all the graphics on the main window.
     * @param window - reference to the main window.
     */
    public void display(RenderWindow window) {
        setRunning(true);
        window.setTitle(getSceneTitle());
        music.play();
        music.setLoop(true);

        while(this.isRunning()) try {
            window.clear(Color.WHITE);
            drawGraphics(window);

            for (Event event : window.pollEvents()) {
                executeEvent(event);
            }
            window.display();

        }catch (Exception e) {
            setRunning(false);
        }
    }

    /**
     * When event is performed (e.g - user clicks on the button) Appropriate function
     * should be called within this function to handle the event.
     *
     * @param event - user event.
     */
    public void executeEvent(Event event) {

        int stepDepth = 5; //The distance the player is moved on keypress.

        switch(event.type) {
            case CLOSED:
                getWindow().close();
                System.exit(0);
                break;
            case KEY_PRESSED:
                switch (event.asKeyEvent().key) {
                    case UP:
                        player.move(0,-stepDepth);
                        detectionHandler(detectCollision(), "DOWN");
                        break;
                    case DOWN:
                        player.move(0,stepDepth);
                        detectionHandler(detectCollision(), "UP");
                        break;
                    case LEFT:
                        player.move(-stepDepth,0);
                        detectionHandler(detectCollision(), "RIGHT");
                        break;
                    case RIGHT:
                        player.move(stepDepth,0);
                        detectionHandler(detectCollision(), "LEFT");
                        break;
                    case ESCAPE:
                        getWindow().setScene(0);
                        getWindow().getScene(0).display(getWindow());
                        break;
                }
                break;
        }
    }

    /**
     * Function to detect if the player has moved onto a tile.
     */
    public Tile.BlockType detectCollision() {

        //Find the block location from the pixel X&Y
        int playerX = Math.round(getPlayerX() / blockSize);
        int playerY = Math.round(getPlayerY() / blockSize);

        //Return the block the player is behind
        return tileMap[playerX][playerY].getTileType();
    }

    /**
     * Function to see what type of block you have collided with and act accordingly.
     *
     * @param reboundDir The direction the avatar should be rebounded.
     * @param type The type of block that has been detected.
     */
    public void detectionHandler(Tile.BlockType type, String reboundDir) {

        switch(type) {
            case WALL:
                reboundPlayer(reboundDir);
            case DOOR:
                //TODO Insert the door handling code here.
            case START:
                break;
            case FINISH:
                //TODO Insert the finish handling code here.
            case VOID:
                //TODO Insert the void handling code here.
            case CHARGE:
                //TODO Insert the charge handling code here.
            case FLOOR:
                break;
            default:
                System.out.println("Please select a defined BlockType.");
        }
    }

    /**
     * Function to rebound the player the amount of steps defined, given a direction.
     *
     * @param dir The direction the avatar should be rebounded.
     */
    public void reboundPlayer(String dir) {

        int reboundStep = 7; //Number of steps to rebound the player.

        switch(dir) {
            case "UP":player.move(0,-reboundStep); break;
            case "DOWN":player.move(0,reboundStep); break;
            case "LEFT":player.move(-reboundStep,0); break;
            case "RIGHT":player.move(reboundStep,0); break;
            default:
                System.out.println("Please select a direction defined.");
                break;
        }
    }

    /**
     * Function to return the X pixels of the player.
     */
    public float getPlayerX() {
        Vector2f res = player.getPosition();
        return res.x;
    }

    /**
     * Function to return the Y pixels of the player.
     */
    public float getPlayerY() {
        Vector2f res = player.getPosition();
        return res.y;
    }

    /**
     * This function is responsible for drawing graphics on the main window
     *
     * @param window - reference to the main window.
     */

    public void drawGraphics(RenderWindow window) {
        for (int j = 0; j < blockY; j++) {
            for (int i = 0; i < blockX; i++) {
                window.draw(tileMap[i][j]);
            }
        }

        //Draw the player
        window.draw(player);
    }

}


