package com.amaze.main;
        import org.jsfml.graphics.Drawable;
        import org.jsfml.graphics.CircleShape;
        import org.jsfml.graphics.ConstView;
        import org.jsfml.graphics.FloatRect;
        import org.jsfml.graphics.View;
        import org.jsfml.system.*;
        import java.io.IOException;
import com.amaze.main.Window;

/**
 * This class is responsible for creating an instance of a Maze.
 */


class Driver{

    public static void main(String[] args) throws Exception{

        com.amaze.main.Window window = new Window(800,600,25,25);

        MenuScene menu = new MenuScene("Main Menu",window);
        //GameScene game = new GameScene("aMaze",window);
        Game game = new Game(800,800,5,5,160,new LevelReader().getLevel());
        window.addScene(menu);
        window.addScene(game);

        //Start Displaying
        window.displayThis();

    }
}