package com.amaze.main;
import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import org.jsfml.window.event.Event;

import com.amaze.entities.Avatar;
import com.amaze.levelmaker.Tile.BlockType;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class will Game and all the elements associated with it.
 */
public class GameScene extends Scene {

    public static int blockSize;              //Size of each block. W and H
    public static int blockX;                 //Number of blocks in X direction
    public static int blockY;                 //Number of blocks in Y direction
    private Tile tileMap[][];           //Represents the maze
    private Avatar player;              //Represents the player(avatar)
    private Clock clock;
    private Battery battery;            //
    private Music music;                //Background music
    private FogOfWar fog;
    private Texture[] tileTexture;
    

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
        player = new Avatar(0,0,blockSize);

        /* Cache textures before we start using them in order to increase performance */
        this.tileTexture = new Texture[7];
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

        /* Create instance of battery */
        battery = new Battery(window.getScreenHeight(),window.getScreenHeight());
        battery.changeChargeLevel(6);

        /* Load background music */
        music = new Music();
        try {
            music.openFromFile(Paths.get("res/music/gs2.wav"));
        } catch (IOException e) {
            System.out.println("There was a problem loading the background music \n Error: " + e);
        }
         
        fog = new FogOfWar(FogOfWar.MAX_SIZE / 2, this.getWindow());
        
        
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
        
        clock = new Clock();

        while(this.isRunning()) try {
            window.clear(Color.BLACK);
            drawGraphics(window);
            
            fog.update(clock);
            
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
        
        
        //check if the block is a carge
        if(detectCollision().equals(Tile.BlockType.CHARGE)){
          fog.increase();
          try {
            tileMap[player.tilePosition().x][player.tilePosition().y] =
                new Tile(null, player.tilePosition().x, player.tilePosition().y, GameScene.blockSize, GameScene.blockSize, Tile.BlockType.FLOOR, this.tileTexture);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        

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


    public void drawGraphics(RenderWindow window) throws Exception{

        for (int j = 0; j < blockY; j++) {
            for (int i = 0; i < blockX; i++) {
              
              if(fog.getView(i, j, player))
                window.draw(tileMap[i][j]);
              
                
            }
        }

        //Draw the player
        window.draw(player);

        //Draw the battery
        window.draw(battery);

        /*batteryRectangleShape r = new RectangleShape(new Vector2f(500,0));
        r.setFillColor(Color.YELLOW);
        r.setSize(new Vector2f(10,10));
        r.setPosition(50,690);*/
        //window.draw(r);
    }


    /**
     * Generates a new map
     * @param window
     * @param blocks
     * @param blockSize
     * @param level
     * @throws Exception
     */
    public void loadNewTileMap(Window window, int blocks, int blockSize,Tile.BlockType[][] level) throws Exception{
        this.blockSize = blockSize;

        blockX = level.length;
        blockY = level.length;
        tileMap = new Tile[blocks][blocks];

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
    }

}


