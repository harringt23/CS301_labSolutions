//////////////////////////////////////////////////////////
// Balloon.java - a ball that grows and shrinks
//
// Author: Steven R. Vegdahl
// Date: 28 March 2001
//
// Modified by: Steven R. Vegdahl
// Date: 26 March 2008
// Reason: refactored
//
/////////////////////////////////////////////////////////

// imports
import java.awt.*;


//////////////////////////////////////////////////////////
// class Balloon - a ball that grows and shrinks
//
/////////////////////////////////////////////////////////
public class Balloon extends Ball {

  //////////////////////////////////////////////////////////////////
  // Balloon - constructor for Balloon
  //
  // calling sequence:
  //   myBalloon = new Balloon(rad, x, y, color, speed);
  //
  // parameters:
  //   rad - the ball's radius
  //   x - the initial x-position of ball's center
  //   y - the initial y-position of ball's center
  //   color - the ball's initial color
  //   speed - the ball's initial speed
  //
  // result:
  //   the new Balloon
  //
  // side-effects:
  //   The object is created
  //
  // bugs/anomalies:
  //   If the radius is <= 0, its behavior might be bizarre
  //
  //////////////////////////////////////////////////////////////////
  public Balloon(int rad, int xCenter, int yCenter, Color c, int spd) {
    super(rad, xCenter, yCenter, c, spd); // perform superclass initialization
   }

  //////////////////////////////////////////////////////////////////
  // tick - perform one time-unit of action
  //
  // calling sequence:
  //   aBalloon.tick(g);
  //
  // parameters:
  //   g - the graphics object on which to display the ball
  //
  // side-effects:
  //   The ball modified.  The intent is that a call
  //   to tick be done at regular intervals, to simulate some unit of
  //   time passing.
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  public void tick(Graphics g) {
      
    // slightly/randomly change the size of the ball, ensuring that the radius
    // stays within the 5..100 range
    radius += -7 + (int)(Math.random()*15);
    radius = Math.max(5,radius);
    radius = Math.min(radius,100);
    
    // perform all 'tick' operations for superclass
    super.tick(g);
  }
}
