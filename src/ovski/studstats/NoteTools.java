package ovski.studstats;

import ovski.api.mysql.MysqlStatsManager;

public class NoteTools {

    /**
     * canNoteWithValue method check is the player can note players with the given value
     * It depends on the played time
     * @return boolean
     */
    public static boolean canNoteWithValue(int value, String pseudo)
    {
        if(Math.abs(value) <= NoteTools.getNoteMaximaleValue(pseudo))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * getNoteMaximaleValue give the maximal value which can be used by the player to note someone
     * It depends on the played time
     * @return boolean
     */
    public static int getNoteMaximaleValue(String pseudo)
    {
        long playedTime = MysqlStatsManager.getPlayerStats(pseudo).getTimePlayed();
        //time played < 5h
        if(playedTime < 18000000)
        {
            return 1;
        }
        //time played < 15h
        else if(playedTime < 54000000)
        {
            return 2;
        }
        //time played < 40h
        else if(playedTime < 144000000 )
        {
            return 3;
        }
        //time played < 100h
        else if(playedTime < 360000000)
        {
            return 4;
        }
        else
        {
            return 5;
        }
    }
}
