package up.edu.dimcalc;

import static org.junit.Assert.*;

import android.graphics.Point;

import org.junit.Test;

public class TwoPointsTest {


    /** when created, getPoint() should show both points at the origin */
    @Test
    public void getPoint() throws Exception {
        TwoPoints testPoints = new TwoPoints();
        Point p1 = testPoints.getPoint(0);
        Point p2 = testPoints.getPoint(1);
        assertEquals(0, p1.x);
        //assertEquals(-2, p1.x); // fails
        assertEquals(0, p1.y);
        assertEquals(0, p2.x);
        assertEquals(0, p2.y);
    }

    /** verify that arbitrary values are properly stored via setPoint() */
    @Test
    public void setPoint() throws Exception {
        TwoPoints testPoints = new TwoPoints();
        testPoints.setPoint(0, 5, -3);
        testPoints.setPoint(1, -3, 5);
        Point p1 = testPoints.getPoint(0);
        Point p2 = testPoints.getPoint(1);
        assertEquals(5, p1.x);
        assertEquals(-3, p1.y);
        assertEquals(-3, p2.x);
        assertEquals(5, p2.y);

    }
    @Test
    public void randomValue() {
        // verify the number is between bounds (-10 -> 9)

    }

    @Test
    public void setOrigin() {
        // initialize new two points object
        TwoPoints testPoints = new TwoPoints();

        // set the origin
        testPoints.setOrigin(0); // set 0 as the origin
        //testPoints.setOrigin(1); // set 1 as the origin

        // get the points
        Point p0 = testPoints.getPoint(0);

        // find point 1 and point 2 for the origin
        assertEquals(0, p0.x);
        assertEquals(0, p0.y);
        //assertEquals(1, p0.x); // fails
    }

    @Test
    public void copy() {
    }

    @Test
    public void distance() {
    }

    @Test
    public void slope() {
    }
}