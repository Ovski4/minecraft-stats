package studmine.mysqlmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

import studmine.studstats.PlayerStats;
import studmine.studstats.StatsPlugin;

/**
 * MysqlStatsManager
 * Manage requests involving the stats table
 * Require a plugin with a database connection
 * @author Ovski
 */
public class MysqlStatsManager
{
    /**
     * updatePlayerStats method update the statistics of a player
     * @param PlayerStats playerStats : Contains the playerStats of a player
     */
    public static void updatePlayerStats(PlayerStats playerStats)
    {
        int playerId = MysqlUserManager.getPlayerIdFromPseudo(playerStats.getPseudo());
        StatsPlugin.connection.sendData("UPDATE stats SET "
                + "broken_blocks="+playerStats.getBlocksBroken()+", "
                + "placed_blocks="+playerStats.getBlocksPlaced()+", "
                + "stupid_deaths="+playerStats.getStupidDeaths()+", "
                + "pvp_deaths="+playerStats.getNormalDeaths()+", "
                + "kills="+playerStats.getKills()+", "
                + "played_time="+playerStats.getTimePlayed()+", "
                + "verbosity="+playerStats.getVerbosity()+", "
                + "prestige="+playerStats.getPrestige()
                + " WHERE user_id = "+playerId
        );
        System.out.println("The stats of "+playerStats.getPseudo()+" have been updated");
    }

    /**
     * updatePrestige method update the prestige of a player
     * @param int prestige : Contains the value of the prestige
     */
    public static void updatePrestige(String pseudo, int prestige)
    {
        int playerId = MysqlUserManager.getPlayerIdFromPseudo(pseudo);
        StatsPlugin.connection.sendData("UPDATE stats SET "
                + "prestige="+prestige
                + " WHERE user_id = "+playerId
        );
    }

    /**
     * getPrestige method retrieve the prestige of a player
     * @param String pseudo : Contains the pseudo of a player
     * @return int prestige : The prestige value
     */
    public static int getPrestige(String pseudo)
    {
        int playerId = MysqlUserManager.getPlayerIdFromPseudo(pseudo);
        ResultSet resultat = StatsPlugin.connection.getData("SELECT prestige FROM stats WHERE user_id="+playerId);
        try
        {
            if (resultat != null && resultat.next())
            {
                return resultat.getInt(1);
            }
            else
            {
                return -1000;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1000;
        }
    }

    /**
     * getPlayerStats method retrieve the statistics of a player, and load them in a PlayerStats object
     * @param String pseudo : Contains the pseudo of a player
     * @return PlayerStats playerStats : The stats object of the player
     */
    public static PlayerStats getPlayerStats(String pseudo)
    {
        int playerId = MysqlUserManager.getPlayerIdFromPseudo(pseudo);
        PlayerStats playerStats = new PlayerStats();
        ResultSet resultat = StatsPlugin.connection.getData("SELECT * FROM stats WHERE user_id="+playerId);
        try
        {
            if (resultat != null && resultat.next())
            {
                    playerStats.setPseudo(pseudo);
                    playerStats.setBlocksBroken(resultat.getInt(2));
                    playerStats.setBlocksPlaced(resultat.getInt(3));
                    playerStats.setStupidDeaths(resultat.getInt(4));
                    playerStats.setNormalDeaths(resultat.getInt(5));
                    playerStats.setKills(resultat.getInt(6));
                    playerStats.setTimePlayed(resultat.getLong(7));
                    playerStats.setVerbosity(resultat.getInt(8));
                    playerStats.setPrestige(resultat.getInt(9));
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return playerStats;
    }
}