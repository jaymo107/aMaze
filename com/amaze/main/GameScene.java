package com.amaze.main;
import com.amaze.entities.Avatar;
import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class will Game and all the elements associated with it.
 */
public class GameScene extends Scene {

	private static int blockSize;       //Size of each block. W and H

	private int blockX;                 //Number of blocks in X direction
	private int blockY;                 //Number of blocks in Y direction
	private Tile[][] tileMap;           //Represents the maze
	private Avatar player;              //Represents the player(avatar)
	private Battery battery;            //
	private Music music;                //Background music
	private FogOfWar fog;
	private Text txtScore;
	private Text txtTime;
	private Vector2i startTile;
	private Vector2i endTile;

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;

	private int charges = 0;

	private int score;

	private Texture[] tileTexture;

	private RectangleShape textBackground;
	private Text message;

	private Clock voidClock = new Clock();
	private Clock vc;
	private double timeSpentInVoid = 0;
	private boolean voidClockToggle = true;

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

        Tile currentlyLoaded;

		GameScene.blockSize = blockSize;

		blockX = level.length;
		blockY = level.length;

		tileMap = new Tile[blocks][blocks];
		player = new Avatar(0, 0, blockSize);

        /* Cache textures before we start using them in order to increase performance */
		tileTexture = new Texture[7];
		for (int i = 0; i < tileTexture.length; i++) {
			tileTexture[i] = new Texture();
			tileTexture[i].loadFromFile(Paths.get("res/images/" + Tile.BlockType.values()[i].toString().toLowerCase() + ".png"));
		}

        /* Create new instances of tiles */
		for (int j = 0; j < blocks; j++) {
			for (int i = 0; i < blocks; i++) {
				tileMap[i][j] = new Tile("", translateX(i), translateY(j), GameScene.blockSize, GameScene.blockSize, level[i][j], tileTexture);
			}
		}

		window.create(new VideoMode((int)tileMap[blocks - 1][blocks - 1].getPosition().x + blockSize, (int)(tileMap[blocks - 1][blocks - 1].getPosition().y + blockSize) + 60),"Game");

        /* Create instance of battery */
		battery = new Battery(window.getScreenHeight(), window.getScreenHeight(), 6);

        /* Load background music */
		music = new Music();
		try {
			music.openFromFile(Paths.get("res/music/move.ogg"));
		} catch (IOException e) {
			System.out.println("There was a problem loading the background music \n Error: " + e);
		}

		/* Load font and text*/
		Font scoreFont = new Font();
		try {
			scoreFont.loadFromFile(Paths.get("res/fonts/Arial.ttf"));
		} catch (IOException e) {
			System.out.println("Could not load the font!");
		}

        /* Create fog of war */
		fog = new FogOfWar(FogOfWar.MAX_SIZE / 2, battery, this);

		txtScore = new Text("Score: \t100", scoreFont);
		txtScore.setPosition(15, window.getScreenHeight() - 40);

		txtTime = new Text("Time: \t1:23", scoreFont);
		txtTime.setPosition(window.getScreenWidth() - 180, window.getScreenHeight() - 40);

        /* Change avatar location */
        for(int i = 0;i < blocks; i++){
            for(int j = 0; j < blocks; j++){
                currentlyLoaded = tileMap[i][j];

                if (currentlyLoaded.getTileType() == Tile.BlockType.START) {
                    player.setPosition(currentlyLoaded.getPosition());
					startTile = new Vector2i(Math.round(player.getPosition().x/blockSize), Math.round(player.getPosition().y/blockSize));
                }
				if (currentlyLoaded.getTileType() == Tile.BlockType.FINISH) {
					endTile = new Vector2i(Math.round(currentlyLoaded.getPosition().x/blockSize), Math.round(currentlyLoaded.getPosition().y/blockSize));
				}
            }
        }
	}

	/**
	 * (
	 * Translates X to raw pixels
	 *
	 * @param blockX Block number
	 * @return Raw pixel value
	 */

	public int translateX(int blockX) {
		return blockSize * blockX;
	}

	/**
	 * Translates Y to raw pixels
	 *
	 * @param blockY Block number
	 * @return Raw pixel value
	 */

	public int translateY(int blockY) {
		return blockSize * blockY;
	}

	/**
	 * When called, this function displays all the graphics on the main window.
	 */
	public void display() {
		setRunning(true);
		getWindow().setTitle(getSceneTitle());

		music.play();
		music.setLoop(true);
		Clock clock = new Clock();
		Clock timer = new Clock();

		Clock gameClock = new Clock();

		int minute = 0;

		while (isRunning()){
			try {
				getWindow().clear(Color.BLACK);
				drawGraphics(getWindow());

				fog.update(clock);

				voidDetection();

				int second = (int) timer.getElapsedTime().asSeconds();
				txtTime.setString("Time: \t" + minute + ":" + ((second < 10) ? "0" + second : second));

				updateScore(gameClock, voidClock);
				txtScore.setString("Score: \t" + score);

				if (second >= 60) {
					timer.restart();
					minute++;
				}

				for (Event event : getWindow().pollEvents()) {
					executeEvent(event);
				}
				getWindow().display();
			} catch (Exception e) {
				music.stop();
				System.out.println("There has been an issue drawing something, exiting to level menu\n\n");
				e.printStackTrace();
				setRunning(false);
			}
		}
	}

	/**
	 * When event is performed (e.g - user clicks on the button) Appropriate function
	 * should be called within this function to handle the event.
	 *
	 * @param event - user event.
	 */
	public void executeEvent(Event event) {
		/* Sets flag to true when key pressed*/
		switch (event.type) {
			case KEY_PRESSED:
				switch (event.asKeyEvent().key) {
					case UP:up = true;break;
					case DOWN:down = true;break;
					case LEFT:left = true;break;
					case RIGHT:right = true;break;
					case ESCAPE:
						music.stop();
						exitScene(this);
						break;
				}
				break;

			case CLOSED:systemExit();break;
		}

		/* Sets boolean if the key has been released */
		switch (event.type) {
			case KEY_RELEASED:
				switch (event.asKeyEvent().key) {
					case UP:up = false;break;
					case DOWN:down = false;break;
					case LEFT:left = false;break;
					case RIGHT:right = false;break;
					case ESCAPE:
						music.stop();
						exitScene(this);
						break;
				}
				break;
		}
	}

	/**
	 * Function to detect if the player has moved onto a tile.
	 */
	public Tile detectCollision() {
		//Find the block location from the pixel X&Y
		int playerX = Math.round(getPlayerX() / blockSize);
		int playerY = Math.round(getPlayerY() / blockSize);

		//Debugging - enable to display Player X & Y
		//System.out.println("Player X: " + playerX + " - Player Y: " + playerY);

		//Return the block the player is behind
		return tileMap[playerX][playerY];
	}

	/**
	 * Function to see what type of block you have collided with and act accordingly.
	 *
	 * @param reboundDir The direction the avatar should be rebounded.
	 * @param tile       The tile that has been detected.
	 */
	public void detectionHandler(Tile tile, String reboundDir) {
		switch (tile.getTileType()) {
			case WALL: reboundPlayer(reboundDir); break;
			case DOOR: closeDoor(tile); break;
			case START: break;
			case FINISH:
				System.out.println("Time spent in void " + Double.toString(timeSpentInVoid / 1000) + " seconds");
				drawFinishWindow(getWindow());
				getWindow().display();
				pause(3000);
				music.stop();
				exitScene(this);
				break;
			case VOID:
				//TODO Insert the void handling code here.
				break;
			case CHARGE:
				battery.increaseChargeLevel(Battery.MAX - battery.getChargeLevel());
				battery.changeChargeLevel(battery.getChargeLevel() + (Battery.MAX - battery.getChargeLevel()));
				fog.increase();
				charges++;
				tile.setTileType(Tile.BlockType.FLOOR);
				tile.setTexture(tileTexture[1]);
				break;
			case FLOOR: break;
			default: System.out.println("Please select a defined BlockType.");
		}
	}

	/**
	 * Function to rebound the player the amount of steps defined, given a direction.
	 *
	 * @param dir The direction the avatar should be rebounded.
	 */
	public void reboundPlayer(String dir) {
		int reboundStep = 7; //Number of steps to rebound the player.

		switch (dir) {
			case "UP":
				player.move(0, -reboundStep);
				break;
			case "DOWN":
				player.move(0, reboundStep);
				break;
			case "LEFT":
				player.move(-reboundStep, 0);
				break;
			case "RIGHT":
				player.move(reboundStep, 0);
				break;
			default:
				System.out.println("Please select a direction defined.");
				break;
		}
	}

	/**
	 * Function to return the X pixels of the player.
	 */
	public float getPlayerX() {
		return player.getPosition().x;
	}

	/**
	 * Function to return the Y pixels of the player.
	 */


	public float getPlayerY() {
		return player.getPosition().y;
	}

	/**
	 * This function is responsible for drawing graphics on the main window
	 *
	 * @param window - reference to the main window.
	 */


	public void drawGraphics(RenderWindow window) {
		for (int y = 0; y < blockY; y++) {
			for (int x = 0; x < blockX; x++) {
				if (fog.getView(x, y, player)) {
					window.draw(tileMap[x][y]);
				}
			}
		}

		/* Check if the key has been pressed with window edge detection*/
		if (up && getPlayerY() >= 0) {
			player.move(0, -1);
			detectionHandler(detectCollision(), "DOWN");
		}
		else if (down && getPlayerY() <= translateY(blockY-1)) {
			player.move(0, 1);
			detectionHandler(detectCollision(), "UP");
		}
		else if (left && getPlayerX() >= 0) {
			player.move(-1, 0);
			detectionHandler(detectCollision(), "RIGHT");
		}
		else if (right && getPlayerX() < translateY(blockX - 1)) {
			player.move(1, 0);
			detectionHandler(detectCollision(), "LEFT");
		}

		//Draw the player
		window.draw(player);

		//Draw the battery
		window.draw(battery);

		//Draw score text
		window.draw(txtScore);

		//Draw time text
		window.draw(txtTime);
	}

	public Vector2i getStartTilePos() {
		return startTile;
	}

	public Vector2i getEndTilePos() {
		return endTile;
	}

	public static int getBlockSize() {
		return blockSize;
	}

	public static void setBlockSize(int blockSize) {
		if (blockSize > 0) {
			GameScene.blockSize = blockSize;
		}
	}

	public void updateScore(Clock gameClock, Clock voidClock) {
		float gameTime = gameClock.getElapsedTime().asSeconds();
		float voidTime = voidClock.getElapsedTime().asSeconds();

		if (gameTime == 0 || voidTime == 0) return;

		if (charges == 0) {
			score = (int) ((1000 / gameTime) + (100 / (timeSpentInVoid + 1)));
		} else {
			score = (int) ((1000 / gameTime) + (100 / (1 + timeSpentInVoid))) + (100/charges);
		}
	}

	public void closeDoor(Tile door) {
		Runnable r = () -> {
			try {
				Thread.sleep(500);
				door.setTexture(tileTexture[0]);
				door.setTileType(Tile.BlockType.WALL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		new Thread(r).start();
	}

	public void drawFinishWindow(RenderWindow window) {
		try {
			Vector2f size = new Vector2f(getWindow().getScreenWidth() / 1.2F, (getWindow().getScreenHeight() / 4));
			textBackground = new RectangleShape(size);
			textBackground.setPosition(getWindow().getScreenWidth() / 12F, (getWindow().getScreenHeight() / 2.5F)-65);

			Font textFont = new Font();
			textFont.loadFromFile(Paths.get("res/fonts/Maze.ttf"));

			message = new Text("Congratulations\n\t\tYou won", textFont, 70);
			message.setColor(Color.BLACK);
			message.setStyle(Text.BOLD);
			message.setOrigin((getWindow().getScreenWidth() / 9.5F) * -1, (getWindow().getScreenHeight() / 3F) * -1);

		} catch (IOException e) {
			System.err.println("There was a problem loading the finish window.");
		}

		window.draw(textBackground);
		window.draw(message);
	}

	public Vector2i rawPlayertoBlockPos(){
		int playerPosBlockX = (int)((getPlayerX() + 1) / blockSize);
		int playerPosBlockY = (int)((getPlayerY() + 1) / blockSize);
		Vector2i temp = new Vector2i(playerPosBlockX,playerPosBlockY);
		return temp;
	}

	public boolean isVoid(int x, int y){
		try{
			return tileMap[x][y].getTileType() == Tile.BlockType.VOID;
		}catch (ArrayIndexOutOfBoundsException e){
			//System.out.println("Out of bounds :P");
			return false;
		}
	}

	public void voidDetection(){
		Vector2i playerPos = rawPlayertoBlockPos();
		int voidCount = 0;
		boolean voidAround = false;
		vc = new Clock();
		vc.restart();

		for(int i = playerPos.x - 1; i < playerPos.x + 1; i++){
			for(int j = playerPos.y - 1; j < playerPos.y + 1; j++){
				if(isVoid(i,j)){
					voidCount++;
				}
			}
		}

		if(voidCount > 0){
			voidAround = true;
			timeSpentInVoid = timeSpentInVoid + vc.restart().asMicroseconds();
			System.out.println(timeSpentInVoid);
		}else{
			voidAround = false;
		}
		//System.out.println("hello");
	}

}