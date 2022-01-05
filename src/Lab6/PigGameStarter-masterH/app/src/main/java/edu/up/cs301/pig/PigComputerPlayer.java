package edu.up.cs301.pig;

import android.os.Handler;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.util.Tickable;

/**
 * An AI for Pig
 *
 * @author Andrew M. Nuxoll
 * @version August 2015
 */
public class PigComputerPlayer extends GameComputerPlayer {

    /**
     * ctor does nothing extra
     */


    //Add Handler for delay
    final Handler hand = new Handler();

    public PigComputerPlayer(String name) {
        super(name);
    }

    /**
     * callback method--game's state has changed
     *
     * @param info
     * 		the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // TODO  You will implement this method
        PigGameState pigGameState = new PigGameState((PigGameState) info);

        if (super.playerNum != pigGameState.getTurnId()) {
            return;
        }


        else {

            try {
                TimeUnit.SECONDS.sleep(2);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }


            Random rnd = new Random();
            int actionNum = rnd.nextInt(2 - 0);



            if (actionNum == 0) {
                PigHoldAction newAction = new PigHoldAction(this);
                super.game.sendAction(newAction);
            }

            else {
                PigRollAction newAction = new PigRollAction(this);
                super.game.sendAction(newAction);
            }


        }



    }//receiveInfo

}
