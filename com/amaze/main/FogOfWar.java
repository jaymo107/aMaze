package com.amaze.main;

import com.amaze.entities.Avatar;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2i;

public class FogOfWar {

	public static final int MAX_SIZE = 8;
	public static final int CHUNK_SIZE = 1;

	private int size;
	private Battery battery;
	private GameScene gameScene;
	private long elapsedTime;

	/**
	 * Set the initial size
	 */
	public FogOfWar(int size, Battery battery, GameScene gameScene) {
		this.size = size;
		this.battery = battery;
		this.gameScene = gameScene;

		elapsedTime = 0;
	}

	public void increase() {
		size += CHUNK_SIZE * 10;
		if (size >= MAX_SIZE) size = MAX_SIZE;
	}

	public int getSize() {
		return size;
	}

	public void drain() {
		size -= CHUNK_SIZE;
		if (size <= 2) size = 2;
	}

	/**
	 * The function which will automatically drain the battery
	 */
	public void update(Clock clock) {
		if (size <= 0) return;

		//update the elapsed time
		elapsedTime = clock.getElapsedTime().asMilliseconds();

		//check if elapsed time is greater than the trigger

		//The amount of time before it drains
		int drainTime = 3000;

		if (elapsedTime >= drainTime) {
			//set the elapsed time to 0 and reset the clock
			elapsedTime = 0;
			drain();
			battery.decreaseChargeLevel(1);
			clock.restart();
		}
	}

	/**
	 * Based off the avatar, check the coordinate and return true if the tile can be rendered
	 */
	public boolean getView(int x, int y, Avatar player) {
		Vector2i startPos = gameScene.getStartTilePos();
		Vector2i endPos = gameScene.getEndTilePos();
		Vector2i playPos = player.getTilePosition();
	 
		return (x == startPos.x && y == startPos.y || x == (endPos.x) && y == (endPos.y)) ||
				x < playPos.x + size && x > playPos.x - size &&
				y < playPos.y + size && y > playPos.y - size;
	}

}
