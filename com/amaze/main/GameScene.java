package com.amaze.main;
import com.amaze.entities.Avatar;
import org.jsfml.audio.Music;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
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

	private int blockCount;             //Number of blocks in a row
	private Tile[][] tileMap;           //Represents the maze
	private Avatar player;              //Represents the player(avatar)
	private Battery battery;            //
	private Music music;                //Background music
	private FogOfWar fog;
	private Text txtScore;
	private Text txtTime;
	private Vector2i startTile;
	private Vector2i endTile;
	private boolean state = true;
	private String userName = "";

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;

	private int charges = 0;

	private int score = 1200;

	private Texture[] tileTexture;

	private RectangleShape textBackground;
	private Text message;
	private Text message2;
	private MusicButton musicButton;

	private int fontSizeUserInput;
	private float textXCordUserInput;
	private float textYCordUserInput;

	private Font textFontUserInput;
	private boolean listeningForUserName;

	private int currentLevel;

	private Clock voidDetectionClock = new Clock();
	private double totalTimeSpentInVoid = 0;
	private boolean voidClockToggled = false;

	private double timeExposedToVoid = 0;

	private Music chargesSound;
	private Music voidsSound;

	/**
	 * This constructor creates an instance of a GameScene.
	 * Within this class all the game logic should be handled.
	 * If needed, supplement with additional classes for OO.
	 *
	 * @param sceneTitle - sets title of the window.
	 *                   set to "aMaze" when creating
	 *                   an instance of the GameScene.
	 */

	public GameScene(String sceneTitle, Window window, int blocks, int blockSize, Tile.BlockType[][] level, int currentLevel) throws Exception {
		super(sceneTitle, window);

		this.currentLevel = currentLevel;

        Tile currentlyLoaded;

		GameScene.blockSize = blockSize;

		blockCount = level.length;

		tileMap = new Tile[blocks][blocks];
		player = new Avatar(0, 0, blockSize);

        /* Cache textures before we start using them in order to increase performance */
		tileTexture = new Texture[7];
		for (int i = 0; i < tileTexture.length; i++) {
			tileTexture[i] = new Texture();
			tileTexture[i].loadFromFile(Paths.get("res/images/" + Tile.BlockType.values()[i].toString().toLowerCase() + ".png"));
			tileTexture[i].setSmooth(true);
		}

        /* Create new instances of tiles */
		for (int j = 0; j < blocks; j++) {
			for (int i = 0; i < blocks; i++) {
				tileMap[i][j] = new Tile("", translateX(i), translateY(j), GameScene.blockSize, GameScene.blockSize, level[i][j], tileTexture);
			}
		}

		window.create(new VideoMode((int)tileMap[blocks - 1][blocks - 1].getPosition().x + blockSize, (int)(tileMap[blocks - 1][blocks - 1].getPosition().y + blockSize) + 60),"Game");

		float batteryXCord = window.getScreenWidth();
		float batteryYCord = window.getScreenHeight();
		/* Create instance of battery */
		battery = new Battery(batteryXCord, batteryYCord, 6, window);

        /* Load background music */
		music = new Music();
		chargesSound = new Music();
		voidsSound = new Music();
		try {
			music.openFromFile(Paths.get("res/music/move.ogg"));
			chargesSound.openFromFile(Paths.get("res/music/Charge.wav"));
			voidsSound.openFromFile(Paths.get("res/music/Void.wav"));
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

		int txtScoreFont = window.getScreenWidth()/25;
		float txtScoreXCord = window.getScreenWidth() - window.getScreenWidth() / 1.02F;
		float txtScoreYCord = window.getScreenHeight() -40;

		txtScore = new Text("Score: \t100", scoreFont, txtScoreFont);
		txtScore.setPosition(txtScoreXCord, txtScoreYCord);

		int txtTimeFont = window.getScreenWidth()/25;
		float txtTimeXCord = window.getScreenWidth() - window.getScreenWidth() / 4;
		float txtTimeYCord = (window.getScreenHeight() - window.getScreenHeight() / 16);
		txtTime = new Text("Time: \t1:23", scoreFont, txtTimeFont);
		txtTime.setPosition(txtTimeXCord, txtTimeYCord);

		float musicButtonHeight = window.getScreenHeight() / 14;
		float musicButtonWidth = window.getScreenHeight() / 12;
		float musicButtonXCord = window.getScreenWidth() / 3F;
		float musicButtonYCord = window.getScreenHeight() / 1.08F;

		musicButton = new MusicButton(musicButtonXCord,musicButtonYCord,musicButtonWidth,musicButtonHeight, window);

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

		fontSizeUserInput = getWindow().getScreenWidth() / 14;
		textXCordUserInput = getWindow().getScreenWidth() / -4.5F;
		textYCordUserInput = getWindow().getScreenHeight() / -3.5F;

		textFontUserInput = new Font();
		try {
			textFontUserInput.loadFromFile(Paths.get("res/fonts/Arial.ttf"));
		} catch (IOException e) {
			e.printStackTrace();
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

				int second = (int) timer.getElapsedTime().asSeconds();
				txtTime.setString("Time: \t" + minute + ":" + ((second < 10) ? "0" + second : second));

				updateScore(gameClock);
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
					case M:
						state = !state;
						musicPlaying(state); break;
				}
				break;

			case JOYSTICK_BUTTON_PRESSED:

				switch (event.asJoystickButtonEvent().button) {

					case 0: left = true; break;
					case 1: down = true; break;
					case 2: right = true; break;
					case 3: up = true; break;
                    case 12:
                        music.stop();
                        exitScene(this);
                        break;
                    case 9:
                        state = !state;
                        musicPlaying(state); break;
				}
				break;

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
			case JOYSTICK_BUTTON_RELEASED:
				switch (event.asJoystickButtonEvent().button) {

					case 0: left = false; break;
					case 1: down = false; break;
					case 2: right = false; break;
					case 3: up = false; break;

				}
				break;
			case CLOSED:
				music.stop();
				systemExit();
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
				System.out.println("Time spent in void " + totalTimeSpentInVoid/1000 + " seconds");
				musicPlaying(false);
				listeningForUserName = true;

				while(listeningForUserName) {
					drawFinishWindow(getWindow());
					drawUserName(getWindow());
					getWindow().display();

					for (Event event : getWindow().pollEvents()) {
						listenForInput(event);
					}
					getWindow().clear();

				}
				exportToDB();
				getWindow().setScene(0);
				this.setRunning(false);
			case VOID: break;
			case CHARGE:
				//chargeSound();
				battery.increaseChargeLevel(Battery.MAX - battery.getChargeLevel());
				battery.changeChargeLevel(battery.getChargeLevel() + (Battery.MAX - battery.getChargeLevel()));
				fog.increase();
				charges++;
				tile.setTileType(Tile.BlockType.FLOOR);
				tile.setTexture(tileTexture[1]);
				break;
			case FLOOR: voidDetection(); break;
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
		for (int y = 0; y < blockCount; y++) {
			for (int x = 0; x < blockCount; x++) {
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
		else if (down && getPlayerY() <= translateY(blockCount - 1)) {
			player.move(0, 1);
			detectionHandler(detectCollision(), "UP");
		}
		else if (left && getPlayerX() >= 0) {
			player.move(-1, 0);
			detectionHandler(detectCollision(), "RIGHT");
		}
		else if (right && getPlayerX() < translateY(blockCount - 1)) {
			player.move(1, 0);
			detectionHandler(detectCollision(), "LEFT");
		}

		//Draw the player
		window.draw(player);

		//Draw the battery
		window.draw(battery);

		//Draw music button
		window.draw(musicButton);

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

	public void updateScore(Clock gameClock) {
		float gameTime = gameClock.getElapsedTime().asSeconds();

		if (gameTime == 0) return;

		int score0 = (int) (1000 / gameTime + 100 / ((totalTimeSpentInVoid == 0) ? 1 : totalTimeSpentInVoid));
		if (charges == 0) {
			score = score0;
			if(score >= 1200) score = 1200;
		} else {
			score = score0 + 100 / charges;
		}
	}

	public void closeDoor(Tile door) {
		Runnable r = () -> {
			try {
				Thread.sleep(1000);
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

			float textBackgroundHeight = getWindow().getScreenHeight() / 5;
			float textBackgroundWidth  = getWindow().getScreenWidth() / 1.25F;
			float textBackgroundXCord  = getWindow().getScreenWidth() / 2 - (textBackgroundWidth / 2);
			float textBackgroundYCord  = getWindow().getScreenHeight() / 2  - (textBackgroundHeight / 1.5F);

			Vector2f size = new Vector2f(textBackgroundWidth, textBackgroundHeight);
			textBackground = new RectangleShape(size);

			Texture backgroundImage = new Texture();
			backgroundImage.loadFromFile(Paths.get("res/menuGraphics/Wall.png"));
			textBackground.setTexture(backgroundImage);

			textBackground.setPosition(textBackgroundXCord, textBackgroundYCord);

			Font textFont = new Font();
			textFont.loadFromFile(Paths.get("res/fonts/Maze.ttf"));

			float textXCord = getWindow().getScreenWidth() / -4.5F;
			float textYCord = getWindow().getScreenHeight() / -2.8F;

			int fontSize = getWindow().getScreenWidth() / 14;

			message = new Text("Level Completed!", textFont, fontSize);
			message.setColor(Color.BLACK);
			message.setStyle(Text.BOLD);
			message.setOrigin(textXCord, textYCord);

			message2 = new Text("Enter your username: \n", textFont, fontSize);
			message2.setColor(Color.BLACK);
			message2.setStyle(Text.BOLD);
			message2.setOrigin(textXCord + 35, textYCord - 40);

		} catch (IOException e) {
			System.err.println("There was a problem loading the finish window.");

		}

		window.draw(textBackground);
		window.draw(message);
		window.draw(message2);
	}
	public void drawUserName(RenderWindow window) {

		Text un = new Text(userName, textFontUserInput, fontSizeUserInput);
		un.setColor(Color.BLACK);
		un.setStyle(Text.BOLD);
		un.setOrigin(textXCordUserInput + 35, textYCordUserInput - 130);
		window.draw(un);
	}
	public void listenForInput(Event event) {

		switch (event.type) {
			case TEXT_ENTERED:
				if (event.asTextEvent().unicode >= 32 && event.asTextEvent().unicode <= 126) {

					if(userName.length() >= 12) { break; }
					else {

						userName += (char) event.asTextEvent().unicode;
						break;
					}
				}
				if (event.asTextEvent().unicode == 8) {

					if(userName.length() == 0) { userName = ""; }

					else {
						userName = userName.substring(0, userName.length() - 1);
						break;
					}
				}
		}
		switch (event.type){
			case KEY_PRESSED:
				switch (event.asKeyEvent().key) {

					case RETURN:
						getWindow().setScene(getWindow().getArrayList().indexOf(0));
						listeningForUserName = false;
						break;

			}
		}
	}
	public void exportToDB() {
		System.out.println("Username: "+userName);
		System.out.println("Score: " +score);
		System.out.println("Level: " +currentLevel);
		System.out.println("Level Completion Time: " + txtTime.getString().substring(7));
	}

	public void musicPlaying(boolean state) {
		if (!state) {
			music.pause();
			musicButton.setSelected(true);
		} else {
			musicButton.setSelected(false);
			music.play();
		}
	}
	public Vector2i rawPlayerToBlockPos(){
		int playerPosBlockX = (int)((getPlayerX() + 1) / blockSize);
		int playerPosBlockY = (int)((getPlayerY() + 1) / blockSize);
		return new Vector2i(playerPosBlockX, playerPosBlockY);
	}

	public boolean isVoid(int x, int y){
		try {
			return tileMap[x][y].getTileType() == Tile.BlockType.VOID;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public void voidDetection(){
		Vector2i playerPos = rawPlayertoBlockPos();
		int voidCount = 0;

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (isVoid(playerPos.x + i, playerPos.y + j)) {
					voidCount++;
					voidClockToggled = true;
					Runnable r = () ->{
						try {
							battery.decreaseChargeLevel(1);
							Thread.sleep(500);
							fog.drain();
						} catch (InterruptedException e) {
							System.err.println("Something went wrong.");
						}
					};
					new Thread(r).start();
				}
			}
		}

		if (voidCount == 0) voidClockToggled = false;

		if (voidClockToggled) {
			timeExposedToVoid = voidDetectionClock.getElapsedTime().asMilliseconds();
		} else {
			voidDetectionClock.restart();
			totalTimeSpentInVoid += timeExposedToVoid;
			timeExposedToVoid = 0;
		}
	}
//	public void chargeSound() {
//
//		chargesSound.play();
//	}
//	public void voidSound() {
//
//		voidsSound.play();
//	}

}