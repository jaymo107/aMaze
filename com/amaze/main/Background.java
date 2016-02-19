package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is responsible for manging background image for Menu Scene
 */
public class Background extends RectangleShape {

    ArrayList<String> results = new ArrayList<>();

    /**
     * Construct a button with following parameters:
     *
     * @param width - width of the background
     * @param height - height of the background
     */
    public Background(float width, float height) throws IOException {
		Vector2f size = new Vector2f(width, height);
		Texture icon = new Texture();

        ArrayList<String> themes = new ArrayList<>();

        File[] files = new File("res/menuGraphics/Theme").listFiles();

        for(File file: files != null ? files : new File[0]) {
            if (file.isFile()) {
                themes.add(file.getName());
                System.out.println(themes);
            }
        }

        Random random = new Random();
        String randomImagePath = themes.get(random.nextInt(themes.size()- 1 + 1));

        try{
            icon.loadFromFile(Paths.get("res/menuGraphics/Theme/" + randomImagePath));
            System.out.println(randomImagePath);
        }catch (IOException e){
            System.out.println("There is either no avatar in the folder or a hidden file that needs to be deleted");
        }

        this.setTexture(icon);
        icon.setSmooth(true);
        this.setSize(size);
    }

}

