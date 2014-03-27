package net.ovski.minecraft.stats;

import net.ovski.minecraft.api.mysql.MysqlPlayerManager;

/**
 * OnPlayerQuit
 * 
 * A set of function usefull to handle notes
 *
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class NoteTools
{
    /**
     * canNoteWithValue method check is the player can note players with the given value
     * It depends on the played time
     * 
     * @param value
     * @param pseudo
     * @return true or false
     */
    public static boolean canNoteWithValue(int value, String pseudo)
    {
        if(Math.abs(value) <= NoteTools.getNoteMaximaleValue(pseudo)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * getNoteMaximaleValue give the maximal value which can be used by the player to note someone
     * It depends on the played time
     * 
     * @param pseudo
     * @return boolean
     */
    public static int getNoteMaximaleValue(String pseudo)
    {
        long playedTime = MysqlPlayerManager.getStats(pseudo).getTimePlayed();
        
        if(playedTime < 18000000) { //time played < 5h
            return 1;
        } else if(playedTime < 54000000) { //time played < 15h
            return 2;
        } else if(playedTime < 144000000 ) { //time played < 40h
            return 3;
        } else if(playedTime < 360000000) { //time played < 100h
            return 4;
        } else {
            return 5;
        }
    }
}
