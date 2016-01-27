<<<<<<< HEAD:com/amaze/display (k.caudrey-joshi@lancaster.ac.uk)/Window.java
package com.amaze.display;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

=======
package com.amaze.main;
>>>>>>> Menu:com/amaze/main/Window.java
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;

<<<<<<< HEAD:com/amaze/display (k.caudrey-joshi@lancaster.ac.uk)/Window.java
import com.amaze.entities.Tile;

import javax.swing.border.TitledBorder;
=======
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
>>>>>>> Menu:com/amaze/main/Window.java

/**
 * This is a class responsible for holding a window for aMaze
 */

public class Window extends RenderWindow{

    private int screenWidth;
    private int screenHeight;
<<<<<<< HEAD:com/amaze/display (k.caudrey-joshi@lancaster.ac.uk)/Window.java
    private Sound sound; //Sound to be played
    private int gridX;
    private int gridY;
=======
>>>>>>> Menu:com/amaze/main/Window.java

    Sound sound;                                        //Sound to be played

    ArrayList<Displayable> Scenes = new ArrayList<>();  //Holds all the scenes. This ArrayList is populated from the Main function.

    /*******************************************************************************************************
     *                                       !IMPORTANT!                                                   *
     *                                                                                                     *
     * Since scenes are stored in the arrayList, they are accessed                                         *
     * throught indexing (e.g - Scenes.get(scene)). Therefore, it is                                       *
     * important to remember that with the current logic Menu is Scenes.get(0)                             *
     * because it was a first element that was saved to an ArrayList and Game                              *
     * will be Scenes.get(1).                                                                              *
     *                                                                                                     *
     * Since Menu is not yet fully developed, new scenes will be added.                                    *
     * Example:                                                                                            *
     *                                                                                                     *
     *        Scenes for different menu options (E.G settings, credits, about, leaderboards, etc...)       *
     *        These scenes will be placed right after the Menu (1,2,3,4,5....)                             *
     *        This will be constantly shifting the Game index.                                             *
     *                                                                                                     *
     * At the moment, it should be 1.                                                                      *
     *                                                                                                     *
     *******************************************************************************************************/


    int scene = 0;
    int currentScene = 0;

    /**
     *
     * @param resolutionX Width in pixels(physical size of window)
     * @param resolutionY Height in pixels(physical size of window)
     * @param blocksX Size of X grid overlayed onto window
     * @param blocksY Size of y grid overlayed onto window
     *
     * Example:
     *                maze(1920,1080,25,25)
     *                This would create a window of 1920x1080 pixels but with 25 by 25
     *                grid to place objects into a maze TEST
     */

    public Window(int resolutionX, int resolutionY, int blocksX, int blocksY) throws IOException {
        //Instantiate
        this.screenWidth = resolutionX;
        this.screenHeight = resolutionY;

        // Creating a new window
        this.create(new VideoMode(screenWidth, screenHeight), "aMaze", WindowStyle.DEFAULT);
        this.setFramerateLimit(60);

        //Load Sounds
        SoundBuffer soundBuffer = new SoundBuffer();
        soundBuffer.loadFromFile(Paths.get("res/beep.wav"));

        //Create a sound and set its buffer
        sound = new Sound();
        sound.setBuffer(soundBuffer);
    }

    /**
     * Used to start displaying the window. Once this is called nothing can be changed.
     */

<<<<<<< HEAD:com/amaze/display (k.caudrey-joshi@lancaster.ac.uk)/Window.java
    public void displayThis(){
        while (this.isOpen( )) {
            // Clear the screen
            this.clear(Color.BLUE);
            drawItems(this.drawList);

            this.display();

            // the user pressed the close button
            for (Event event : this.pollEvents( )) {
                //Different behaviour depending on
                switch(event.type){
                    case CLOSED:
                        this.close();
                        break;
                    case KEY_PRESSED:
                        if(event.asKeyEvent().key == Keyboard.Key.UP){
                            drawList.get(0).deltaY(-10F);
                        }else if(event.asKeyEvent().key == Keyboard.Key.DOWN){
                            drawList.get(0).deltaY(10F);
                        }else if(event.asKeyEvent().key == Keyboard.Key.LEFT){
                            drawList.get(0).deltaX(-10F);
                        }else if(event.asKeyEvent().key == Keyboard.Key.RIGHT){
                            drawList.get(0).deltaX(10F);
                        }else{
                            sound.play();
                        }
                        break;
                }
=======
    public void displayThis() {
        while (this.isOpen()) {
            while (scene >= currentScene) {

                //Shows the scene that was selected on the menu.
                Scenes.get(scene).display(this);

                if (scene < 0) System.exit(0);
>>>>>>> Menu:com/amaze/main/Window.java
            }
        }
    }

    /**
     * Adds a scene to ArrayList
     * @param scene - visual representation of a certain aMaze game part.
     */
<<<<<<< HEAD:com/amaze/display (k.caudrey-joshi@lancaster.ac.uk)/Window.java
    public void addItem(Tile tile,int gridPosX, int gridPosY ){

        drawList.add(tile);
    }
=======
    public void addScene(Displayable scene) {Scenes.add(scene);}
>>>>>>> Menu:com/amaze/main/Window.java

    public int getScreenHeight() {return screenHeight;}

    public int getScreenWidth() {return  screenWidth;}

    /**
     * Sets scene variable which is responsible for selecting appropriate scene to be displayed.
     * @param i - used as an ArrayList index.
     */

<<<<<<< HEAD:com/amaze/display (k.caudrey-joshi@lancaster.ac.uk)/Window.java
    private void drawItems(ArrayList<Tile> list){
        for(Tile tile : list){
            this.draw(tile);
        }
    }

    public RenderWindow getWindow(){
        return this;
    }

    /**
     * Translates a grid positon into raw resolution for X axis
     * @return Integer on X value
     */

    private int translateX(){
        return 1;
    }

    /**
     * Translate a grid position into raw resolution for Y axis
     * @return
     */
    private int translateY(){
        return 1;
    }

}
=======
    public void setScene(int i) {scene = i;}
}
>>>>>>> Menu:com/amaze/main/Window.java
