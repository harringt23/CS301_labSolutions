package cs301.Soccer;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import cs301.Soccer.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database
 *
 * @author *** Brynn Harrington ***
 * @version *** October 15, 2021 ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    // initialize a new hash table object that stores the soccer player
    // as the key and the mapping type as an integer
    private final Hashtable<String, SoccerPlayer> database = new Hashtable<>();

    // initialize new variable for whitespace token
    private final String wsTok = " ## ";

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {
        // append the first and last name together separated by “ ## ”
        String fullName = firstName + wsTok + lastName;

        // if the player is already in the table, return false
        if (database.containsKey(fullName)) return false;

        // create a new soccer player object to store the new player
        SoccerPlayer newPlayer = new SoccerPlayer(
                firstName, lastName, uniformNumber, teamName);

        // add the players name as key and soccer player object
        // as value to the hashtable
        database.put(fullName, newPlayer);

        // return true since successfully added player
        return true;
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        // determine if player exists and if so remove them
        return database.remove(firstName + wsTok + lastName, getPlayer(firstName, lastName));
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        // create the hashtable key to look up the player
        String lookupPlayer = firstName + wsTok + lastName;

        // look up the player in the hash table and return player object if found
        if (database.containsKey(lookupPlayer)) return database.get(lookupPlayer);

        // if player is not found return null
        return null;
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        // initialize a variable to store the soccer player
        SoccerPlayer soccerPlayer = getPlayer(firstName, lastName);

        // if the player does not exist, return false
        if (soccerPlayer == null) return false;

        // otherwise bump the number of goals and return true
        else {
            soccerPlayer.bumpGoals();
            return true;
        }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        // initialize a variable to store the soccer player
        SoccerPlayer soccerPlayer = getPlayer(firstName, lastName);

        // if the player does not exist, return false
        if (soccerPlayer == null) return false;

        // otherwise bump the number of yellow cards and return true
        else {
            soccerPlayer.bumpYellowCards();
            return true;
        }
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        // initialize a variable to store the soccer player
        SoccerPlayer soccerPlayer = getPlayer(firstName, lastName);

        // if the player does not exist, return false
        if (soccerPlayer == null) return false;

        // otherwise bump the number of red cards and return true
        else {
            soccerPlayer.bumpRedCards();
            return true;
        }
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        // initialize a new collection of soccer players
        Collection<SoccerPlayer> soccerPlayers = database.values();

        // initialize variable to track number of players
        int numberPlayers = 0;

        // if all teams selected, return the number of associations
        if (teamName == null) return soccerPlayers.size();

        // for each player, check if they are a part of that time
        // if so increment the number of players
        for (SoccerPlayer soccerPlayer : soccerPlayers) {
            // verify if the player is on this team
            if (soccerPlayer.getTeamName().equals(teamName)) numberPlayers++;
        }
        return numberPlayers;
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        // if current currIdx is greater than the number of players of team, return null
        if (idx > numPlayers(teamName) || idx < 0) return null;
        
        // create a new key set to iterate through the players
        Set<String> keys = database.keySet();
        
        // initialize a variable to track the current index in team
        int currIdx = 0;
        
        // iterate through the hash table to find the player as long as team name is nonnull
        if(teamName != null) {
            for(String key: keys) {
                // get the current player from the database
                SoccerPlayer currPlayer = database.get(key);

                // ensure the current player is nonnull
                assert currPlayer != null;

                // get the current team of the player
                String currTeam = currPlayer.getTeamName();

                // if the current index is zero & the team name is correct, return that player
                if(idx == 0) {
                    if(currTeam.equals(teamName)) return database.get(key);
                }

                // otherwise verify the current index is equal the given index and return that
                else {
                    if(currTeam.equals(teamName)) {
                        if(currIdx == idx) return database.get(key);

                        // since player not found, increment the current index
                        currIdx++;
                    }
                }
            }
        }

        // if the team name is null, iterate through teams until correct player found
        else {
            for(String key: keys) {
                // if the current index is equal to the
                if(currIdx == idx) return database.get(key);

                // since player not found, increment the current index
                currIdx++;
            }
        }
        // otherwise return null
        return null;
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        try{
            // initialize a new print writer from the passed file
            PrintWriter pw = new PrintWriter(file);

            // get the player values for the player data
            for (SoccerPlayer currPlayer : database.values()) {
                pw.println(logString(currPlayer.getFirstName()));
                pw.println(logString(currPlayer.getLastName()));
                pw.println(logString("" + currPlayer.getUniform()));
                pw.println(logString(currPlayer.getTeamName()));
                pw.println(logString("" + currPlayer.getGoals()));
                pw.println(logString("" + currPlayer.getYellowCards()));
                pw.println(logString("" + currPlayer.getRedCards()));
                // print the values to LogCat
                Log.v("player",
                             logString(currPlayer.getFirstName())
                                + "\n" + logString(currPlayer.getLastName())
                                + "\n" + logString("" + currPlayer.getUniform())
                                + "\n" + logString(currPlayer.getTeamName())
                                + "\n" + logString("" + currPlayer.getGoals())
                                + "\n" + logString("" + currPlayer.getYellowCards())
                                + "\n" + logString("" + currPlayer.getRedCards()));
            }
            // print the absolute path to the file
            logString(file.getAbsolutePath());

            // close the print file
            pw.close();

            // return true since valid read
            return true;
            // otherwise the file was not found - return to lof
        } catch(Exception FileNotFoundException){
            Log.i("Error", "File Not found");

            // get the path of a new file
            file = new File(file.getPath());

            // write the data to that file
            writeData(file);
        }
        // return false otherwise
        return false;
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        //Log.i("write string", s);
        return s;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from files
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean readData(File file) {
        // if the file exists try reading the player
         try{
            if(file.exists()) {
                Scanner sc = new Scanner(file);
                while(sc.hasNextLine()){
                    SoccerPlayer tempPlayer;
                    String firstName = sc.nextLine();
                    String lastName = sc.nextLine();
                    int uniformNum = Integer.parseInt(sc.nextLine());
                    String teamName = sc.nextLine();
                    int goals = Integer.parseInt(sc.nextLine());
                    int yellowCards = Integer.parseInt(sc.nextLine());
                    int redCards = Integer.parseInt(sc.nextLine());

                    // get the temporary player
                    tempPlayer = getPlayer(firstName,lastName);

                    // if non-null player, remove them
                    if(tempPlayer != null) removePlayer(firstName,lastName);

                    // add the player
                    addPlayer(firstName,lastName,uniformNum,teamName);

                    // get the added player
                    tempPlayer = getPlayer(firstName,lastName);

                    // increment goals, yellow cards, and red cards to correct value
                    for (int i = 0; i < goals; i++) bumpGoals(tempPlayer.getFirstName(),tempPlayer.getLastName());
                    for (int i = 0; i < yellowCards; i++) bumpYellowCards(tempPlayer.getFirstName(),tempPlayer.getLastName());
                    for (int i = 0; i < redCards; i++) bumpRedCards(tempPlayer.getFirstName(),tempPlayer.getLastName());

                    // get the players and print them
                    // print the values to LogCat
                Log.v("player",
                             logString(tempPlayer.getFirstName())
                                + "\n" + logString(tempPlayer.getLastName())
                                + "\n" + logString("" + tempPlayer.getUniform())
                                + "\n" + logString(tempPlayer.getTeamName())
                                + "\n" + logString("" + tempPlayer.getGoals())
                                + "\n" + logString("" + tempPlayer.getYellowCards())
                                + "\n" + logString("" + tempPlayer.getRedCards()));
                }
                // return true since player added
                return true;
            }
        } catch(Exception FileNotFoundException){
            logString("File does not exist");
        }

         // return false if unable to read in file
        return false;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        // return the new set of teams based on key set
        return new HashSet<>(database.keySet());
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        database.clear();
        return true;
    }
}
