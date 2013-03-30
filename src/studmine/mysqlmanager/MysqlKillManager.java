package studmine.mysqlmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

import studmine.studstats.StatsPlugin;

public class MysqlKillManager {

    public static void insertKill(int killerId, int killedId, int weaponId, String date)
    {
        StatsPlugin.connection.sendData("INSERT INTO PlayerKill(player_killed_id, player_killer_id, date, weapon) "
                + "VALUES ("
                + killedId
                + ","
                + killerId
                + ",'"
                + date
                + "',"
                + weaponId+ ")"
        );
    }

    public static int getkillsNumber(int killerId)
    {
        ResultSet resultat = StatsPlugin.connection.getData("SELECT COUNT(*) FROM PlayerKill WHERE player_killer_id = "+killerId);
        try
        {
            if (resultat != null && resultat.next())
            {
                return resultat.getInt("id");
            }
            else
            {
                return -1;
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getDeathsNumber(int killedId)
    {
        ResultSet resultat = StatsPlugin.connection.getData("SELECT COUNT(*) FROM playerKill WHERE player_killed_id = "+killedId);
        try
        {
            if (resultat != null && resultat.next())
            {
                return resultat.getInt("id");
            }
            else
            {
                return -1;
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public static double getRatio(int playerId)
    {
        double deathsNumber = (double) MysqlKillManager.getDeathsNumber(playerId);
        double killsNumber = (double) MysqlKillManager.getkillsNumber(playerId);
        return killsNumber/deathsNumber;
    }
}