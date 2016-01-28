package com.amaze.main;
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

    int blockSize; //Size of each block. W and H
    int blockX;
    int blockY;
    Tile tileMap[][];
    Avatar player;

    /**
     * This constructor creates an instance of a GameScene.
     * Within this class all the game logic should be handled.
     * If needed, supplement with additional classes for OO.
     *
     * @param sceneTitle - sets title of the window.
     *                   set to "aMaze" when creating
     *                   an instanc of the GameScene.
     */

    public GameScene(String sceneTitle, Window window,int resolutionX, int resolutionY, int blocksX, int blocksY, int blockSize, Tile.BlockType[][] level) throws Exception{
        super(sceneTitle);

        this.blockSize = blockSize;

        blockX = level.length;
        blockY = blocksX;

        tileMap = new Tile[blocksX][blocksY];
        player = new Avatar(0,0);

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


        for(int j = 0; j < blocksY; j++){
            for(int i = 0; i < blocksX; i++){
                tileMap[i][j] = new Tile("",translateX(i),translateY(j),this.blockSize,this.blockSize,level[i][j], tileTexture);
            }
        }

        this.window = window;
    }

    /**(
     * Translates X
     * @param blockX
     * @return
     */

    public int translateX(int blockX){
        return blockSize * blockX;
    }

    /**
     * Translates Y to raw
     * @param blockY
     * @return
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
        while(this.isRunning()) try {

            window.clear(Color.WHITE);
            drawGraphics(window);

            for (Event event : window.pollEvents()) {

                //Different behaviour depending on
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
                    //this.arrowKeyUp();
                }else if(event.asKeyEvent().key == Keyboard.Key.DOWN){
                    player.move(0,5);
                    //this.arrowKeyDown();
                }else if(event.asKeyEvent().key == Keyboard.Key.LEFT){
                    player.move(-5,0);
                    //this.enterPressed();
                }else if(event.asKeyEvent().key == Keyboard.Key.RIGHT){
                    player.move(5,0);
                    //this.enterPressed();
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
                //this.addScene(tileMap[i][j]);
                window.draw(tileMap[i][j]);
                //window.draw(player);
            }
        }

        window.draw(player);
    }

}


