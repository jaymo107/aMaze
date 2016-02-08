package com.amaze.main;

import com.amaze.entities.Avatar;
import org.jsfml.graphics.Image;
import org.jsfml.system.Clock;

public class FogOfWar {

	public static final int MAX_SIZE = 15;
	public static final int CHUNK_SIZE = 1;

	private final int CHARGE_SIZE = CHUNK_SIZE * 10;
	private int size;
	private long elapsedTime;

	private Image fog;
	private Window window;
	private Battery battery;

	/**
	 * The amount of time before it drains
	 */
	private int drainTime = 3000;

	/**
	 * Set the initial size
	 */
	public FogOfWar(int size, Window window, Battery battery){
		this.size = size;
		this.window = window;
		this.battery = battery;
		fog = new Image();
		elapsedTime = 0;
	}

	public void increase(){
		size += CHARGE_SIZE;
	}

	public int getSize(){
		return size;
	}

	private void drain(){
		size -= CHUNK_SIZE;
		if (size <= 2) size = 2;
	}

	/**
	 * The function which will automatically drain the battery
	 */
	public void update(Clock clock){
		//update the elapsed time
		elapsedTime = clock.getElapsedTime().asMilliseconds();

		if (size <= 0) return;

		//check if elapsed time is greater than the trigger
		if (elapsedTime >= drainTime) {
			//set the elapsed time to 0 and reset the clock
			elapsedTime = 0;
			drain();
			clock.restart();
		}
	}

	/**
	 * Based off the avatar, check the coordinate and return true if the tile can be rendered
	 */
	public boolean getView(int x, int y, Avatar player){
		return  x > player.tilePosition().x - this.getSize() &&
				x < player.tilePosition().x + this.getSize() &&
				y < player.tilePosition().y + this.getSize() &&
				y > player.tilePosition().y - this.getSize();
	}

}
