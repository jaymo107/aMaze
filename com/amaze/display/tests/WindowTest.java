//package com.amaze.display.tests;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.amaze.display.Window;
//
//public class WindowTest {
//
//  private Window w;
//
//  @Before
//  public void setUp() throws Exception {
//    w = new Window(1920,1080,25,25);
//  }
//
//  @Test
//  public void testWindowSize() {
//    assertEquals("Width should be as expected", 1920, w.getScreenWidth());
//    assertEquals("Height should be as expected", 1080, w.getScreenHeight());
//  }
//
//  @Test
//  public void testWindowPixel() {
//    assertTrue("Image on screen should not be blank", w.getWindow().capture().getPixels().length > 0);
//  }
//
//}
