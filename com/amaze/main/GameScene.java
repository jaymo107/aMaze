package com.amaze.main;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

import java.nio.file.Paths;

/**
 * This class will Game and all the elements associated with it.
 */
public class GameScene extends Scene {

    private Window window;              //Object reference to the window class.

    private int blockSize;              //Size of each block. W and H
    private int blockX;                 //Number of blocks in X direction
    private int blockY;                 //Number of blocks in Y direction
    private Tile tileMap[][];           //Represents the maze
    private Avatar player;              //Represents the player(avatar)

    /**
     * This constructor creates an instance of a GameScene.
     * Within this class all the game logic should be handled.
     * If needed, supplement with additional classes for OO.
     *
     * @param sceneTitle - sets title of the window.
     *                   set to "aMaze" when creating
     *                   an instance of the GameScene.
     */

    public GameScene(String sceneTitle, Window window,int resolution, int blocks,
                     int blockSize, Tile.BlockType[][] level) throws Exception{
        super(sceneTitle);

        this.blockSize = blockSize;

        blockX = level.length;
        blockY = level.length;

        tileMap = new Tile[blocks][blocks];
        player = new Avatar(0,0);

        /* Cache textures before we start using them in order to increase performance */
        Texture tileTexture[] = new Texture[7];
        tileTexture[0] = new Texture();
        tileTexture[1] = new Texture();
        tileTexture[2] = new Texture();
        tileTexture[3] = new Texture();
        tileTexture[4] = new Texture();
        tileTexture[5] = new Texture();
        tileTexture[6] = new Texture();

        tileTexture[0].loadFromFile(Paths.get("res/images/wall.png"));
        tileTexture[1].loadFromFile(Paths.get("res/images/floor.png"));
        tileTexture[2].loadFromFile(Paths.get("res/images/door.png"));
        tileTexture[3].loadFromFile(Paths.get("res/images/blue.png"));
        tileTexture[4].loadFromFile(Paths.get("res/images/blue.png"));
        tileTexture[5].loadFromFile(Paths.get("res/images/void.png"));
        tileTexture[6].loadFromFile(Paths.get("res/images/charge.png"));

        /* Create new instances of tiles */
        for(int j = 0; j < blocks; j++){
            for(int i = 0; i < blocks; i++){
                tileMap[i][j] = new Tile("",translateX(i),translateY(j),this.blockSize,this.blockSize,level[i][j], tileTexture);
            }
        }

        this.window = window;
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

        try{
         /*Once loaded we play music*/
            Sound s = loadSound();
            s.play();
        }catch(Exception e){
            System.out.println("There was a problem loading sound");
        }

        setRunning(true);
        window.setTitle(getSceneTitle());
        while(this.isRunning()) try {

            window.clear(Color.WHITE);
            drawGraphics(window);

            for (Event event : window.pollEvents()) {
                this.executeEvent(event);
            }
            window.display();

        }catch (Exception e) {
            this.setRunning(false);
        }
    }

    /**
     * When event is performed (e.g - user clicks on the button) Appropriate function
     * should be called within this function to handle the event.
     *
     * P.S I'm unsure whether or not it would be appropriate for this function to deal with
     * collision and general game logic. This is primarily for input events (e.g arrow up key)
     * Check MenuScene class for structure.
     *
     * @param event - user event.
     */
    public void executeEvent(Event event) {

        switch(event.type) {
            case CLOSED:
                window.close();
                break;
            case KEY_PRESSED:
                if(event.asKeyEvent().key == Keyboard.Key.UP){
                    player.move(0,-5);
                }else if(event.asKeyEvent().key == Keyboard.Key.DOWN){
                    player.move(0,5);
                }else if(event.asKeyEvent().key == Keyboard.Key.LEFT){
                    player.move(-5,0);
                }else if(event.asKeyEvent().key == Keyboard.Key.RIGHT){
                    player.move(5,0);
                }
                break;
        }
    }

    /**
     * This function is responsible for drawing graphics on the main window
     *
     * @param window - reference to the main window.
     */

    public void drawGraphics(RenderWindow window) {

        for(int j = 0; j < blockY; j++){
            for(int i = 0; i < blockX; i++){
                window.draw(tileMap[i][j]);
            }
        }

        //Draw the player
        window.draw(player);
    }


    /**
     * Load sound gs2
     * @return Sound object
     * @throws Exception
     */

    public Sound loadSound() throws Exception{

        //Create the sound buffer and load a sound from a file
        SoundBuffer soundBuffer = new SoundBuffer();
        soundBuffer.loadFromFile(Paths.get("res/music/gs2.wav"));
        Sound sound = new Sound();
        sound.setBuffer(soundBuffer);
        sound.setLoop(true);
        System.out.println("Sound duration: " + soundBuffer.getDuration().asSeconds() + " seconds");
        return sound;
    }
}


