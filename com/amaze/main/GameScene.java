package com.amaze.main;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

/**
 * This class will Game and all the elements associated with it.
 */
public class GameScene extends Scene {

    private Window window;              //Object reference to the window class.

    int blockSize; //Size of each block. W and H
    int blockX;
    int blockY;
    Tile tileMap[][];

    /**
     * This constructor creates an instance of a GameScene.
     * Within this class all the game logic should be handled.
     * If needed, supplement with additional classes for OO.
     *
     * @param sceneTitle - sets title of the window.
     *                   set to "aMaze" when creating
     *                   an instance of the GameScene.
     */

    public GameScene(String sceneTitle, Window window,int resolutionX, int resolutionY, int blocksX, int blocksY, int blockSize, Tile.BlockType[][] level) throws Exception{
        super(sceneTitle);

        this.blockSize = blockSize;

        blockX = level.length;
        blockY = blocksX;

        tileMap = new Tile[blocksX][blocksY];

        Texture tileTexture = new Texture();

        for(int j = 0; j < blocksY; j++){
            for(int i = 0; i < blocksX; i++){
                tileMap[i][j] = new Tile("",translateX(i),translateY(j),this.blockSize,this.blockSize,level[i][j]);
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
    public void executeEvent(Event event) {}

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
            }
        }
    }

}


