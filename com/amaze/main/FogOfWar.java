package com.amaze.main;

import org.jsfml.graphics.Image;
import org.jsfml.system.Clock;

public class FogOfWar {
  
  public static final int MAX_SIZE = 100;
  private final int CHUNK_SIZE = 20;
  private Image fog;
  private int size;
  private Window window;
  private long elapsedTime;
  /**
   * The amount of time before it drains
   */
  private int drainTime = 1000;
  
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
 
  /**
   * The function which will automatically drain the battery
   */
  public void drain(Clock clock){
    /**
     * update the elapsed time
     */
    this.elapsedTime = clock.getElapsedTime().asMilliseconds();
    
    //check if elapsed time is greater than the trigger
    if(this.elapsedTime >= this.drainTime){
      //set the elapsed time to 0 and reset the clock
      this.elapsedTime = 0;
      clock.restart();
    }
    System.out.println(elapsedTime);
  }
  
  

}
