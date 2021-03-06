package com.amaze.MapMaker;

import com.amaze.main.Scene;
import com.amaze.main.Window;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Jay Feng
 */
public class MapMakerScene extends Scene {

	private Tile[][] tiles;
	private Texture[] blockTextures;

	private int blocks;
	private int blockSize;
	private int numberOfStart;
	private int numberOfFinish;

	private Tile.BlockType[] allValues = Tile.BlockType.values();

	private RectangleShape textBackground;
	private Text userLevel;

	private Font maze = new Font();

	private ArrayList<Integer> results = new ArrayList<>();

	private Integer highestLevelInFolder;

	public MapMakerScene(String sceneTitle, Window window, int blocks) throws IOException {
		super(sceneTitle, window);

		this.blocks = blocks;
		this.blockSize = window.getSize().x / blocks;  //Work out how many blocks

		tiles = new Tile[this.blocks][this.blocks];

		blockTextures = new Texture[7];
		for (int i = 0; i < blockTextures.length; i++) {
			blockTextures[i] = new Texture();
			blockTextures[i].loadFromFile(Paths.get("res/images/" + allValues[i].toString().toLowerCase() + ".png"));
			blockTextures[i].setSmooth(true);
		}

		for (int y = 0; y < this.blocks; y++) {
			for (int x = 0; x < this.blocks; x++) {
				tiles[x][y] = new Tile(blockTextures, x, y);
				tiles[x][y].setTexture(blockTextures[0]);
				tiles[x][y].setPosition(new Vector2f(translateX(blockSize, x), translateY(blockSize, y)));
				tiles[x][y].setSize(new Vector2f(blockSize, blockSize));
			}
		}

		window.create(new VideoMode((int)tiles[this.blocks - 1][this.blocks - 1].getPosition().x + blockSize, (int)(tiles[this.blocks - 1][this.blocks - 1].getPosition().y + blockSize)),"Game");
		exportSuccessful();
	}

    public void executeEvent(Event event) {
        switch(event.type) {
            case CLOSED: systemExit(); break;
            case MOUSE_BUTTON_PRESSED:
                for (Tile[] rows: tiles) {
                    for (Tile tile: rows) {
                        if (isMouseOn(tile)) {
							Mouse.Button b = event.asMouseButtonEvent().button;
							if (b == Mouse.Button.LEFT) {
								changeTextureCW(tile);
							} else {
								changeTextureACW(tile);
							}
						}
                    }
                }
                break;
            case KEY_PRESSED:
                switch (event.asKeyEvent().key) {
                    case ESCAPE:
                        getWindow().create(new VideoMode(getWindow().getScreenWidth(), getWindow().getScreenHeight()),"aMaze");
                        exitScene(this);
                        break;
                    case RETURN:
                        countNumbersOfStartEnd();

						if (numberOfStart != 1) {
							displayTitle("Must have one start and \n\t\t\tone finish",15);
						} else if (numberOfFinish != 1) {
							displayTitle("Must have one start and \n\t\t\tone finish",15);
						} else {
							outputLevel();
							displayTitle("Export Successful", 12);
							pause(2000);
							this.getWindow().create(new VideoMode(getWindow().getScreenWidth(),getWindow().getScreenHeight()), "aMaze");
							exitScene(this);
						}
						break;
				}
		}
	}

	public void displayTitle(String text, int fSize){
		int fontSize = getWindow().getScreenWidth() / fSize;

		float textXCord = getWindow().getScreenWidth() / -9F;
		float textYCord = getWindow().getScreenHeight() / -2.5F;
		userLevel = new Text(text, maze, fontSize);
		userLevel.setColor(Color.BLACK);
		userLevel.setStyle(Text.BOLD);
		userLevel.setOrigin(textXCord, textYCord);
		drawExportWindow(getWindow());
		getWindow().display();
		pause(2000);
	}

	public void changeTextureCW(Tile tile) {
		int nextImageIndex = Arrays.asList(allValues).indexOf(tile.getBlockType()) + 1;
		if (nextImageIndex > allValues.length - 1) nextImageIndex = 0;
		tile.changeBlockType(allValues[nextImageIndex]);
	}

	public void changeTextureACW(Tile tile) {
		int nextImageIndex = Arrays.asList(allValues).indexOf(tile.getBlockType()) - 1;
		if (nextImageIndex < 0) nextImageIndex = allValues.length - 1;
		tile.changeBlockType(allValues[nextImageIndex]);
	}

	public void drawGraphics(RenderWindow window) {
		for (Tile[] rows: tiles) {
			for (Tile tile: rows) {
				window.draw(tile);
			}
		}
	}

	/**(
	 * Translates X to raw pixels
	 * @param blockX Block number
	 * @return Raw pixel value
	 */
	public int translateX(int blockSize, int blockX){
		return blockSize * blockX;
	}

	/**
	 * Translates Y to raw pixels
	 * @param blockY Block number
	 * @return Raw pixel value
	 */
	public int translateY(int blockSize, int blockY){
		return blockSize * blockY;
	}

	public boolean isMouseOn(Tile tile) {
		Vector2i mousePos = Mouse.getPosition(getWindow());
		Vector2f tilePos = tile.getPosition();

		return (mousePos.x > tilePos.x &&
				mousePos.y > tilePos.y &&
				mousePos.x < tilePos.x + blockSize &&
				mousePos.y < tilePos.y + blockSize
		);
	}

	public void outputLevel() {
		checkHighestLevelInFolder();
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("res/Levels/" + (++highestLevelInFolder).toString() + ".txt", true));

            for (int y = 0; y < blocks; y++) {
                for (int x = 0; x < blocks; x++) {
                    writer.print(tiles[x][y].getBlockType().toString() + ",");
                }
                writer.println("");
            }
            writer.close();
        }
        catch (IOException f) {
            System.err.println("Export Failed");
        }
    }

	public void checkHighestLevelInFolder() {
		File[] files = new File("res/Levels").listFiles();

		for (File f: files != null ? files : new File[0]) {
			if (f.isFile()) {
				String[] strings = f.getName().split(".txt");

				if(!strings[0].equals(".DS_Store")){
					Collections.addAll(results, Integer.parseInt(strings[0]));
				}

			}
		}

		Collections.sort(results);
		highestLevelInFolder = results.get(results.size() - 1);
	}

	public void exportSuccessful() {
		try {
			float textBackgroundHeight = getWindow().getScreenHeight() / 5;
			float textBackgroundWidth  = getWindow().getScreenWidth() / 1.25F;
			float textBackgroundXCord  = getWindow().getScreenWidth() / 2 - (textBackgroundWidth / 2);
			float textBackgroundYCord  = getWindow().getScreenHeight() / 2  - (textBackgroundHeight / 1.5F);

			float textXCord = getWindow().getScreenWidth() / -9F;
			float textYCord = getWindow().getScreenHeight() / -2.5F;

			Vector2f size = new Vector2f(textBackgroundWidth, textBackgroundHeight);
			textBackground = new RectangleShape(size);
			textBackground.setPosition(textBackgroundXCord, textBackgroundYCord);

			maze.loadFromFile(Paths.get("res/fonts/Roboto.ttf"));

			Texture backgroundImage = new Texture();
			backgroundImage.loadFromFile(Paths.get("res/menuGraphics/Wall.png"));
			textBackground.setTexture(backgroundImage);

			//Create text
			int fontSize = getWindow().getScreenWidth() / 11;
			userLevel = new Text("Export Successful", maze, fontSize);
			userLevel.setColor(Color.BLACK);
			userLevel.setStyle(Text.BOLD);
			userLevel.setOrigin(textXCord, textYCord);
		}
		catch (Exception e){
			System.err.println("Export Failed");
		}
	}

	public void drawExportWindow(RenderWindow window) {
		window.draw(textBackground);
		window.draw(userLevel);
	}

	public void countNumbersOfStartEnd(){
		numberOfStart = numberOfFinish = 0;
		for (Tile[] row : tiles) {
			for (Tile tile: row) {
				switch (tile.getBlockType()) {
					case FINISH: numberOfFinish++; break;
					case START: numberOfStart++; break;
				}
			}
		}
	}

}