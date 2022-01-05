//////////////////////////////////////////////////////////
// Chameleon.java - a ball that changes color
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
// Chameleon - a ball that changes color
//
/////////////////////////////////////////////////////////
public class Chameleon extends Balloon {

  //////////////////////////////////////////////////////////////////
  // Chameleon - constructor for Chameleon
  //
  // calling sequence:
  //   myChameleon = new Chameleon(rad, x, y, color, speed);
  //
  // parameters:
  //   rad - the ball's radius
  //   x - the initial x-position of ball's center
  //   y - the initial y-position of ball's center
  //   color - the ball's initial color
  //   speed - the ball's initial speed
  //
  // result:
  //   the new Chameleon
  //
  // side-effects:
  //   The object is created
  //
  // bugs/anomalies:
  //   If the radius is <= 0, its behavior might be bizarre
  //
  //////////////////////////////////////////////////////////////////
  public Chameleon(int rad, int xCenter, int yCenter, Color c, int spd) {
    super(rad, xCenter, yCenter, c, spd); // perform superclass initialization
   }


  //////////////////////////////////////////////////////////////////
  // tick - perform one time-unit of action
  //
  // calling sequence:
  //   aChameleon.tick(g);
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
      
    // modify the color slightly/randomly
    int oldRed = this.color.getRed();
    int oldGreen = this.color.getGreen();
    int oldBlue = this.color.getBlue();
    int red = oldRed - 30 + (int)(Math.random()*61);
    red = Math.max(0,red);
    red = Math.min(255,red);
    int green = oldGreen - 30 + (int)(Math.random()*61);
    green = Math.max(0,green);
    green = Math.min(255,green );
    int blue = oldBlue - 30 + (int)(Math.random()*61);
    blue= Math.max(0,blue);
    blue= Math.min(255,blue);
    color = new Color(red,green,blue);
    
    // perform all 'tick' operations for superclass, including painting
    super.tick(g);
  }
}
