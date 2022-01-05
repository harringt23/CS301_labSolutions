package cs301.birthdaycake;
/* CakeModel
* A class strictly data-containing class with information about
* the current state of the cake.*/
public class CakeModel {
    /* Instance Variables */
    // lit - candles are lit or not
    public boolean lit;
    // numCandles - number of candles currently on the cake
    public int numCandles;
    // frosted - cake is frosted or not
    public boolean frosted;
    // hasCandles - whether cake has candles or not
    public boolean hasCandles;
    // x/y - the coordinates of where the user touched
    public float x, y;

    // constructor for the CakeModel class
    public CakeModel() {
        // initialize lit to true
        lit = true;
        // initialize number of candles as 2
        numCandles = 2;
        // initialize frosted to true
        frosted = true;
        // initialize hasCandles to true
        hasCandles = true;
        // initialize coordinate to invalid coordinates
        x = -1;
        y = -1;
    }
}
