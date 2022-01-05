//////////////////////////////////////////////////////////
// Ball.java - a single ball
//
// Author: Steven R. Vegdahl
// Date: 4 March 1999
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
// class Ball - represents one ball in our animation
//
// A class that represents the state of a ball, including color, position,
// speed.
//
/////////////////////////////////////////////////////////
public class Ball {

  ///////// instance variables /////////
  protected Point center; // location of the ball's center
  protected int radius; // length of the ball's radius
  protected Color color; // ball's color
  private int speed; // ball's speed

  //////////////////////////////////////////////////////////////////
  // Ball - constructor for Ball
  //
  // calling sequence:
  //   myBall = new Ball(rad, x, y, color, speed);
  //
  // parameters:
  //   rad - the ball's radius
  //   x - the initial x-position of ball's center
  //   y - the initial y-position of ball's center
  //   color - the ball's initial color
  //   speed - the ball's initial speed
  //
  // result:
  //   the new Ball
  //
  // side-effects:
  //   The Ball is created.
  //
  // bugs/anomalies:
  //   If the radius is <= 0, its behavior might be bizarre
  //
  //////////////////////////////////////////////////////////////////
  public Ball(int rad, int xCenter, int yCenter, Color c, int spd) {
    // initialize the instance variables
    center = new Point(xCenter, yCenter);
    radius = rad;
    color = c;
    speed = spd;
  }

  //////////////////////////////////////////////////////////////////
  // displayOn - display the ball
  //
  // calling sequence:
  //   aBall.displayOn(g);
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
      
    // compute the graphics parameters for the graphics call
    int y = center.y-radius;
    int x = center.x-radius;
    int size = radius*2;
    
    // set the ball to XOR mode, with the right color
    g.setColor(color);
    
    // make the call to draw the ball
    g.fillOval(x,y,size,size);
  }

  //////////////////////////////////////////////////////////////////
  // moveTo - move a ball to a new location
  //
  // calling sequence:
  //   aBall.moveTo(x,y,g);
  //
  // parameters:
  //   x - the new x-position of ball's center
  //   y - the new y-position of ball's center
  //   g - the graphics object on which to display the ball
  //
  // side-effects:
  //   The ball is moved to a different location
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  public void moveTo(int x, int y, Graphics g) {
    // reset the ball's center
    center = new Point(x,y);

    // update the display
    this.displayOn(g);
  }

  //////////////////////////////////////////////////////////////////
  // tick - do one time-step of modifications
  //
  // calling sequence:
  //   aBall.tick(g);
  //
  // parameters:
  //   g - the graphics object on which to display the ball
  //
  // side-effects:
  //   The ball is modified in manner appropriate to it.  The intent is
  //   that a call to 'tick' be done at regular intervals, to simulate
  //   some unit of time passing.
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  public void tick(Graphics g) {
    // compute new x-position and y-position based on speed
    int speed = this.getSpeed();
    int deltaX = -speed+(int)(Math.random()*(speed*2+1));
    int deltaY = -speed+(int)(Math.random()*(speed*2+1));
    
    // compute the new position and change it
    this.moveTo(center.x+deltaX,center.y+deltaY, g);
  }

  //////////////////////////////////////////////////////////////////
  // placeRandomlyIn - placed a ball randomly within a rectangle
  //
  // calling sequence:
  //   aBall.placeRandomlyIn(width,height);
  //
  // parameters:
  //   width - the width of the rectangle into which the ball is to be placed
  //   height - the height of the rectangle into which the ball is to be placed
  //
  // side-effects:
  //   The ball's location is changed.
  //
  // bugs/anomalies:
  //   This is intended for initialization.  It does not change the
  //   ball graphically.  Behavior is undetermined if the ball will entirely
  //   fit in the rectangle.
  //
  //////////////////////////////////////////////////////////////////
  public void placeRandomlyIn(int width, int height) {
    // compute minimum x and y coordinates
    int minX = radius;
    int minY = radius;
    // compute maximum x and y coordinates
    int maxX = width-radius;
    int maxY = height-radius;
    // set a new center for the ball
    center = new Point((int)(minX+Math.random()*(maxX-minX)),
                       (int)(minY+Math.random()*(maxY-minY)));
  }

  //////////////////////////////////////////////////////////////////
  // getSpeed - tells the ball's speed
  //
  // calling sequence:
  //   anInt = aBall.getSpeed();
  //
  // return value:
  //   the speed of the ball
  //
  // side-effects:
  //   none
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  public int getSpeed() {
    return speed;
  }
}
