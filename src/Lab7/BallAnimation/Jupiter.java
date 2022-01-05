//////////////////////////////////////////////////////////
// Jupiter.java - a ball with a spot on it
//
// Author: Steven R. Vegdahl
// Date: 6 March 1999
// Reason: Split out from BallAnimation.java
//
// Modified by: Steven R. Vegdahl
// Date: 27 February 2000
// Reason: add 'showing'
//
// Modified by: Steven R. Vegdahl
// Date: 28 October 2000
// Reason: removed 'showing'; now paint normally instead of in XOR mode; also
//   did a more careful job defining what's public, private, etc.
//
// Modified by: Steven R. Vegdahl
// Date: 26 March 2008
// Reason: refactored
//
/////////////////////////////////////////////////////////

// imports
import java.awt.*;

//////////////////////////////////////////////////////////
// class Jupiter - represents one ball (with a spot) in our animation
//
// A class that represents the state of a normal ball, plus state that
// spot information.
//
/////////////////////////////////////////////////////////
public class Jupiter extends Chameleon {

  ///////// instance variables /////////
  private Color spotColor; // the spot's color
  private int spotSize; // the spot's size
  private double spotAngle; // the angle of the spot from the ball's center
  private double spotDistance; // distance of spot from center of ball
  private double rotationSpeed; // rotation-speed of spot

  //////////////////////////////////////////////////////////////////
  // Jupiter - constructor for Jupiter
  //
  // calling sequence:
  //   myJupiter = new Jupiter(rad, x, y, color, speed);
  //
  // parameters:
  //   rad - the ball's radius
  //   x - the initial x-position of ball's center
  //   y - the initial y-position of ball's center
  //   color - the ball's initial color
  //   speed - the ball's initial speed
  //
  // result:
  //   the new Jupiter; the color, size and rotation speed of the spot is
  //   set randomly
  //
  // side-effects:
  //   The object is created
  //
  // bugs/anomalies:
  //   If the radius is <= 0, its behavior might be bizarre
  //
  //////////////////////////////////////////////////////////////////
  public Jupiter(int rad, int xCenter, int yCenter, Color c, int spd) {
    super(rad, xCenter, yCenter, c, spd); // perform superclass initialization
    this.setSpotRandomly(); // initialize the spot
  }

  //////////////////////////////////////////////////////////////////
  // setSpotRandomly - initializes the instance variables to define a random
  // spot on the Jupter
  //
  // calling sequence:
  //   myJupter.setSpotRandomly();
  //
  // side-effects:
  //   All instance variables related to the spot are initialized.  The spot
  //   will not have a radius that is greater than 80% of the radius of the
  //   entire object.  The rotation speed is set randomly between 0.0 and 1.0
  //   radians per second.
  //
  // bugs/anomalies:
  //   This method should be called after the objects Ball-inherited instance
  //   variables are set up.
  //
  //////////////////////////////////////////////////////////////////
  private void setSpotRandomly() {
    spotColor = BallAnimation.randomColor(30); // color
    spotSize = (int)(Math.random()*radius*0.8); // spot-size up to 80% of ball
    spotAngle = 0.0; // start at angle 0
    spotDistance = Math.random()*(radius-spotSize); // keep spot fully inside
    rotationSpeed = Math.random(); // rotation-speed up to 1.0
  }

  //////////////////////////////////////////////////////////////////
  // displayOn - display the ball
  //
  // calling sequence:
  //   aJupiter.displayOn(g);
  //
  // parameters:
  //   g - the graphics object on which to display the ball
  //
  // side-effects:
  //   The ball's image is drawn onto the graphics object
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  protected void displayOn(Graphics g) {
    // draw main ball using superclass method
    super.displayOn(g);

    // draw the spot
    int xDelta = (int)(spotDistance*Math.cos(spotAngle));
    int yDelta = (int)(spotDistance*Math.sin(spotAngle));
    int xCenter = center.x + xDelta;
    int yCenter = center.y + yDelta;
    g.setColor(spotColor); // set the color for the spot
    g.fillOval(xCenter-spotSize, yCenter-spotSize, spotSize*2, spotSize*2);
  }

  //////////////////////////////////////////////////////////////////
  // tick - perform one time-unit of action
  //
  // calling sequence:
  //   aJupiter.tick(g);
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
    // change angle of spot based on rotation speed
    spotAngle += rotationSpeed;
    
    // perform all 'tick' operations for superclass (including painting)
    super.tick(g);
  }
}
