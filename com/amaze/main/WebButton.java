package com.amaze.main;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

/**
 * This class holds information associated with the web button.
 */
public class WebButton extends Button {

    /* Music button for MenuScene. */
    public WebButton(float xCord, float yCord, float width, float height, Window window, MenuScene menu) throws IOException {
        super(xCord, yCord, width, height, window, menu);

        getDefaultIcon().loadFromFile(Paths.get("res/menuGraphics/web.png"));
        getDefaultIcon().setSmooth(true);
        this.setTexture(getDefaultIcon());

    }

    public void performAction() {
        System.out.println("WebButton Button Pressed");
        try {
            openWebpage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void openWebpage() throws IOException {

        String url = "http://amazegame.xyz/leaderboard.php";
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        if (os.contains("win")) {

            rt.exec("rundll32 url.dll,FileProtocolHandler " + url);

        } else if (os.contains("mac")) {

            rt.exec("open " + url);
        }

    }
}
