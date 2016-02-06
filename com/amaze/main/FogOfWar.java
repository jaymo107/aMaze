package com.amaze.main;

import org.jsfml.graphics.Image;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

public class FogOfWar {
  
  public static final int MAX_SIZE = 20;
  private final int CHUNK_SIZE = 1;
  private Image fog;
  private int size;
  private Window window;
  private long elapsedTime;
  /**
   * The amount of time before it drains
   */
  private int drainTime = 2000;
  
  /**
   * Set the initial size
   */
  public FogOfWar(int size, Window window){
    this.size = size;
    this.window = window;
    fog = new Image();
    elapsedTime = 0;
  }
  
  public void increase(){
     this.size += CHUNK_SIZE;
  }
  
  public int getSize(){
    return this.size;
  }
  
  private void drain(){
    this.size -= this.CHUNK_SIZE;
    if(this.size <=1)
      this.size = 1;
    
  }
 
  /**
   * The function which will automatically drain the battery
   */
  public void update(Clock clock){
    /**
     * update the elapsed time
     */
    this.elapsedTime = clock.getElapsedTime().asMilliseconds();
    
    if(this.getSize() <= 0) return;
    
    //check if elapsed time is greater than the trigger
    if(this.elapsedTime >= this.drainTime){
      //set the elapsed time to 0 and reset the clock
      this.elapsedTime = 0;
      this.drain();
      clock.restart();
    }
    System.out.println(this.getSize());
  }
  
  /*
   * Based off the avatar, check the coordinate and return true if the tile can be rendered
   */
  public boolean getView(int x, int y, Avatar player){
    System.out.println(player.getXTile());
    if(x > player.getPosition().x - this.getSize() && x < player.getPosition().x + this.getSize() && y < player.getPosition().y + this.getSize() && y > player.getPosition().y - this.getSize()) return true;
    return false;
  }
  
  

}
