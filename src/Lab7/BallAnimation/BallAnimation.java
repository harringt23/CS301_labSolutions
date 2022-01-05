//////////////////////////////////////////////////////////
// BallAnimation.java - simple ball animation
//
// Author: Steven R. Vegdahl
// Date: 25 February 1999
// Status: simple stuff working
//
// Modified by: Steven R. Vegdahl
// Date: 4 March 1999
// Reason: fix some screen-glitches and BallCanvas class to make it easier
//   to add buttons, etc.
//
// Modified by: Steven R. Vegdahl
// Date: 27 February 2000
// Reason: fix more screen-glitches
////
// Modified by: Steven R. Vegdahl
// Date: 16 January 2003
// Reason: converted from Frame to JFrame
//////
// Modified by: Steven R. Vegdahl
// Date: 8 April 2005
// Reason: added polymorphic case
//
/////////////////////////////////////////////////////////

// imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//////////////////////////////////////////////////////////
// class BallAnimation - our main class
//
// A frame class that displays moving balls, and allows the user to
// interact with them.
//
/////////////////////////////////////////////////////////
public class BallAnimation extends JFrame {

  // what kind of ball(s):
  // 0 => Ball
  // 1 => Jupiter
  // 2 => Chameleon
  // 3 => Balloon
  // 4 => mixture
  private int ballType;

  //-------- instance-variables --------//

  // our ball-canvas object, where the animating balls will be displayed
  private BallCanvas canvas;

  //////////////////////////////////////////////////////////////////
  // main - the main program
  //
  // calling sequence (from command-line):
  //   java BallAnimation 2 20
  //    or
  //   java BallAnimation 2
  //
  // parameters:
  //   The (optional) command-line arguments are:
  //   - first argument: ball-type:
  //     - 0 => plain ball (the default)
  //     - 1 => balloon
  //     - 2 => chameleon
  //     - 3 => jupiter
  //     - 4 => mixed
  //   - second argument: number of balls (default is 10)
  //
  // side-effects:
  //   Window is displayed on the screen for the user to interact with.
  //
  // bugs/anomalies
  //   none known;
  //
  //////////////////////////////////////////////////////////////////
  public static void main(String args[]) {
      
    // number of balls
    int num = 10; // default value
    
    // ball type(s)
    int ballType = 0; // default value
    
    //Look for command line arguments
    if (args.length > 2) { // if too many arguments, exit with error
      System.err.println("too many arguments");
      System.exit(1);
    }
    else {
        try {
            if (args.length >= 2) { // parse ball-count
                num = Integer.parseInt(args[1]);
            }
            if (args.length >= 1) { // parse ball-type argument
                ballType = Integer.parseInt(args[0]);
            }
        }
        catch (NumberFormatException nfx) {
        }
    }

    //If no command line arguments were specified, then ask the user for information
    JFrame tempFrame = new JFrame();  //We need a parent window so the dialog will appear
    tempFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    tempFrame.setLocationRelativeTo(null);  //center 
    tempFrame.setVisible(true);

    Object[] options = {"Ball", "Balloon", "Chameleon", "Jupiter"};
    ballType = JOptionPane.showOptionDialog(tempFrame, "Which class should I use?", "Configuration", 
                                              JOptionPane.CLOSED_OPTION, JOptionPane.QUESTION_MESSAGE, 
                                              null, options, options[0]);
    

    tempFrame.setVisible(false);
    tempFrame.dispose();


    // create and display the animation
    BallAnimation ba = new BallAnimation(num, ballType, 500, 500);
    ba.setTitle("Ball Animation");
    ba.setVisible(true);
  }

  //////////////////////////////////////////////////////////////////
  // BallAnimation - constructor for BallAnimation
  //
  // calling sequence:
  //   ba = new BallAnimation(n, width, height);
  //
  // parameters:
  //   n - the number of balls in the animation
  //   width - the width, in pixels, of the canvas
  //   height - the height, in pixels, of the canvas
  //
  // result:
  //   a new BallAnimation is created
  //
  // side-effects:
  //   a second thread is created to animate the balls
  //
  // bugs/anomalies:
  //   There may be glitches in the animation, leaving garbage-pixels in
  //   the frame's display-area.  The size of the frame will be a bit
  //   larger than the canvas.
  //
  //////////////////////////////////////////////////////////////////
  BallAnimation(int n, int bType, int width, int height) {

    // initialize ballTypa
    ballType = bType;
    
    // create Panel and a black Canvas on which to display the balls
    Panel p = new Panel();
    canvas = new BallCanvas(n, ballType, width, height);
    canvas.setSize(width,height);
    canvas.setBackground(Color.black);
    p.add(canvas);

    // set the frame's size, trying to leave enough room for any buttons
    // that might be added, etc.
    this.setSize(width+20, height+60);

    // add our Panel to the frame
    this.getContentPane().add(p);
    
    // set frame up to exit application when window is closed
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 

  }

  //////////////////////////////////////////////////////////////////
  // randomColor - returns a random color
  //
  // calling sequence:
  //   myColor = randomColor(k);
  //
  // parameters:
  //   k - the minimum brightness-value to be given to any of the red, green,
  //       or blue components of the color
  //
  // result:
  //   the desired "random" color
  //
  // side-effects:
  //   the frame is painted
  //
  // bugs/anomalies:
  //   If n is less than zero or greater than 255, the Color will not
  //   not necessarily follow any of the constraints.
  //
  //////////////////////////////////////////////////////////////////
  public static Color randomColor(int min) {
    // independently compute random colors for each component between min
    // and 255, inclusive
    int red = min+(int)(Math.random()*(256-min));
    int green = min+(int)(Math.random()*(256-min));
    int blue = min+(int)(Math.random()*(256-min));
    // compose and return the color
    return new Color(red,green,blue);
  }

}

class BallCanvas extends Canvas
  implements MouseListener, MouseMotionListener, Runnable {
      
  // ======== instance variables ========

  // the array of ball objects
  private Ball[] allBalls;

  // animation buffer
  private Image buff;
  private Graphics g2;

  //////////////////////////////////////////////////////////////////
  // BallCanvas - constructor for BallCanvas
  //
  // calling sequence:
  //   ba = new BallCanvas(n, width, height);
  //
  // parameters:
  //   n - the number of balls in the animation
  //   width - the width, in pixels, of the canvas
  //   height - the height, in pixels, of the canvas
  //
  // result:
  //   a new BallCanvas is created
  //
  // side-effects:
  //   a second thread is created to animate the balls
  //
  // bugs/anomalies:
  //   There may be glitches in the animation, leaving garbage-pixels in
  //   the frame's display-area.  The size of the frame will be a bit
  //   larger than the canvas.
  //
  //////////////////////////////////////////////////////////////////
  BallCanvas(int n, int ballType, int width, int height) {

    // set my size and background
    this.setSize(width,height);
    this.setBackground(Color.black);

    // set myself up to listen to various events
    this.addMouseListener(this); // for 'mousePressed' event
    this.addMouseMotionListener(this); // for 'mouseDragged' event

    // create the array of n balls; initialize each of them with a radius-value
    // that is randomly selected between 10 and 49.  Also select a random color
    // that is at least 30 in each of the RGB values (so that we can see it).
    // place it randomly within the viewing area
    allBalls = new Ball[n];
    for (int i = 0; i < n; i++) {
      int currentType = ballType;
      if (currentType == 4) { // we want a mixture
          currentType = (int)(4*Math.random()); // random int in 0..3
      }
      if (currentType == 3) {
        allBalls[i] = new Jupiter(10+(int)(Math.random()*40), 0, 0, BallAnimation.randomColor(30),
                                      1+(20*i/(n+1)));
      }
      else if (currentType == 2) {
        allBalls[i] = new Chameleon(10+(int)(Math.random()*40), 0, 0, BallAnimation.randomColor(30),
                                      1+(20*i/(n+1)));
      }
      else if (currentType == 1) {
        allBalls[i] = new Balloon(10+(int)(Math.random()*40), 0, 0, BallAnimation.randomColor(30),
                                      1+(20*i/(n+1)));
      }
      else {
        allBalls[i] = new Ball(10+(int)(Math.random()*40), 0, 0, BallAnimation.randomColor(30),
                                      1+(20*i/(n+1)));
      }
      allBalls[i].placeRandomlyIn(this.getSize().height,this.getSize().width);
    }

    // create and start our 'run' thread that performs the ball
    // movement/animation
    Thread t = new Thread(this);
    t.start();
  }

  //////////////////////////////////////////////////////////////////
  // paint - paint method for a BallCanvas
  //
  // calling sequence:
  //   paint(g); // typically called automatically by system
  //
  // parameters:
  //   g - the Graphics object on which to paint
  //
  // side-effects:
  //   the frame is painted
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  public void paint(Graphics g) {

    synchronized (this) {
      setUpBuffer();

      // overwrite everything in hidden buffer with the background color      
      g2.setColor(Color.black);
      g2.fillRect(0,0,500,500);
      
      // display all balls onto hidden buffer
      for (int i = 0; i < allBalls.length; i++) {
        allBalls[i].displayOn(g2);
      }
      
      // copy hidden buffer to visible buffer
      g.drawImage(buff,0,0,null);
      
    }

  }


  private void setUpBuffer() {
    if (buff != null) return;
    this.getGraphics().setColor(Color.black);
    buff = this.createImage(1000,1000);
    g2 = buff.getGraphics();
  }

  //////////////////////////////////////////////////////////////////
  // setPositions - sets the center-position of each ball to be the same place
  //
  // calling sequence:
  //   setPositions(x, y, g);
  //
  // parameters:
  //   x - the x-position to set the positions at
  //   y - the y-position to set the positions at
  //   g - the graphics object
  //
  // side-effects:
  //   All balls are set to the desired position; the screen is updated
  //
  // bugs/anomlies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  void setPositions(int x, int y, Graphics g) {
    synchronized (this) {
      // set the position of each ball to (x,y) and animate it
      for (int i = 0; i < allBalls.length; i++) {
        allBalls[i].moveTo(x,y,g);
      }
    }
  }

  //////////////////////////////////////////////////////////////////
  // run - the code for our alternate thread.  Randomly modifies the
  //       position of each ball (and animates it) at 0.1 second intervals
  //
  // calling sequence:
  //   run(); // called automatically by the system
  //
  // side-effects:
  //   The balls' positions are continuously modified and animated
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  public void run() {
    // our infinite run-loop
    for (;;) {

      // get the graphics object
      Graphics g = this.getGraphics();

      // only perform the animation if it's enabled and graphics object exists
      if (g != null) {
        setUpBuffer();
        
        // overwrite background with black
        g2.setColor(Color.black);
        g2.fillRect(0,0,500,500);

        synchronized(this) {
            
          // move/animate each ball
          for (int i = 0; i < allBalls.length; i++) {
            // tell the current ball to peform one time-step, and to paint itself
            allBalls[i].tick(g2);
          }
          g.drawImage(buff,0,0,null);
        }
      }

      // pause for 1/10th of a second before trying again
      pause(100);
    }
  }

  //////////////////////////////////////////////////////////////////
  // pause - pause for a set period of time
  //
  // calling sequence:
  //   pause(n);
  //
  // parameters:
  //   n - the number of milliseconds to pause
  //
  // side-effects:
  //   the thread goes to sleep for n milliseconds
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  public void pause(int milliseconds) {
    // go to sleep for the specified number of milliseconds; continue by
    // catching the exception when woken up
    try {
      Thread.sleep(milliseconds);
    }
    catch (InterruptedException x)
      {}
  }

  //////////////////////////////////////////////////////////////////
  // mousePressed - handle 'mousePressed' event
  //
  // calling sequence:
  //   mousePressed(e); // typically called by System
  //
  // parameters:
  //   e - the event object
  //
  // side-effects:
  //   causes all balls to center on same location
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  public void mousePressed(MouseEvent e) {
    // get x and y positions
    int x = e.getX();
    int y = e.getY();
    // center all balls on location where mouse was pressed
    setPositions(x,y,this.getGraphics());
  }

  //////////////////////////////////////////////////////////////////
  // mouseDragged - handle 'mouseDragged' event
  //
  // calling sequence:
  //   mouseDragged(e); // typically called by System
  //
  // parameters:
  //   e - the event object
  //
  // side-effects:
  //   causes all balls to center on same location
  //
  // bugs/anomalies:
  //   None known.
  //
  //////////////////////////////////////////////////////////////////
  public void mouseDragged(MouseEvent e) {
    // get x and y positions
    int x = e.getX();
    int y = e.getY();
    // center all balls on location where mouse was pressed
    setPositions(x,y,this.getGraphics());
  }

  // "unused" event-handling methdos
  public void mouseReleased(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseMoved(MouseEvent e) {}
}

