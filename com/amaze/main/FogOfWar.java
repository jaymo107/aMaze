package com.amaze.main;

import org.jsfml.graphics.Image;
import org.jsfml.system.Clock;

public class FogOfWar {
  
  public static final int MAX_SIZE = 100;
  private final int CHUNK_SIZE = 20;
  private Image fog;
  private int size;
  private Window window;
  private Clock clock;
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
    System.out.println("hi"+clock.getElapsedTime());
  }
  
  

}
