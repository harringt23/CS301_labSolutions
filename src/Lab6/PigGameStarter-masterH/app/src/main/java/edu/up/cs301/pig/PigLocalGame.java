package edu.up.cs301.pig;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameState;

import android.util.Log;

import java.util.Random;

// dummy comment, to see if commit and push work from srvegdahl account

/**
 * class PigLocalGame controls the play of the game
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigLocalGame extends LocalGame {

    /**
     * This ctor creates a new game state
     */


    private PigGameState pigGameState;



    public PigLocalGame() {
        //TODO  You will implement this constructor

        pigGameState = new PigGameState();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        //TODO  You will implement this method


        if (playerIdx == pigGameState.getTurnId()) {
            return true;
        }

        return false;
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        //TODO  You will implement this method

        if (action instanceof PigHoldAction) {
            if (pigGameState.getTurnId() == 0) {
                pigGameState.setPlayer0Score(pigGameState.getRunningTotal()
                        + pigGameState.getPlayer0Score());

            }

            else if (pigGameState.getTurnId() == 1) {
                pigGameState.setPlayer1Score(pigGameState.getRunningTotal()
                        + pigGameState.getPlayer1Score());

            }

            //Store the running total before resetting it
            int lastRunningTotal = pigGameState.getRunningTotal();

            pigGameState.setRunningTotal(0);

            if (super.playerNames.length > 1) {
                if (pigGameState.getTurnId() == 0) {
                    pigGameState.setTurnId(1);


                    //New Turn, send message
                    pigGameState.setMessage(super.playerNames[0] + " added " + lastRunningTotal +
                            " to their final score");
                }

                else if (pigGameState.getTurnId() == 1) {
                    pigGameState.setTurnId(0);

                    //New Turn, send message
                    pigGameState.setMessage(super.playerNames[1] + " added " + lastRunningTotal +
                            " to their final score");
                }
            }

            return true;
            }


        if (action instanceof PigRollAction) {
            Random rnd = new Random();

            pigGameState.setCurrDieValue(rnd.nextInt(6 - 1) + 1);

            if (pigGameState.getCurrDieValue() != 1) {
               pigGameState.setRunningTotal(pigGameState.getRunningTotal()+ pigGameState.getCurrDieValue());
            }
            else if (pigGameState.getCurrDieValue() == 1){
                pigGameState.setRunningTotal(0);


                if (super.playerNames.length > 1) {
                    if (pigGameState.getTurnId() == 0) {
                        pigGameState.setTurnId(1);

                        //New Turn, send message

                        pigGameState.setMessage(super.playerNames[0] + " rolled a 1 and lost everything!" );
                    }
                    else if (pigGameState.getTurnId() == 1) {
                        pigGameState.setTurnId(0);

                        //New Turn, send Message

                        pigGameState.setMessage(super.playerNames[1] + " rolled a 1 and lost everything!" );
                    }
                }

                return true;
            }



            return true;



        }
        return false;
        }






    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        //TODO  You will implement this method
        PigGameState pigGameStateCopy = new PigGameState(pigGameState);

        p.sendInfo(pigGameStateCopy);




    }//sendUpdatedSate

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        //TODO  You will implement this method
        if (pigGameState.getPlayer0Score() >= 50) {
            return "The Winner is: " + super.playerNames[0] + " Their score was: "  + pigGameState.getPlayer0Score();
        }

        if (pigGameState.getPlayer1Score() >=50) {
            return "The Winner is: " + super.playerNames[1] + " Their score was: " + pigGameState.getPlayer1Score();
        }



        return null;
    }

}// class PigLocalGame
