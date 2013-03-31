package studmine.mysqlmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

import studmine.studstats.PlayerStats;
import studmine.studstats.StatsPlugin;

/**
 * MysqlStatsManager
 * Manage requests involving the PlayerStats table
 * Require a plugin with a database connection
 * @author Ovski
 */
public class MysqlStatsManager
{
    public static void updatePlayerStats(PlayerStats playerStats)
    {
        /**
         * updatePlayerStats method update the statistics of a player
         * @param PlayerStats playerStats : Contains the playerStats of a player
         */
        int playerId = MysqlPlayerManager.getPlayerIdFromPseudo(playerStats.getPseudo());
        StatsPlugin.connection.sendData("UPDATE PlayerStats SET "
                + "blocksBroken="+playerStats.getBlocksBroken()+", "
                + "blocksPlaced="+playerStats.getBlocksPlaced()+", "
                + "stupidDeaths="+playerStats.getStupidDeath()+", "
                + "deaths="+playerStats.getNormalDeaths()+", "
                + "kills="+playerStats.getKills()+", "
                + "timePlayed="+playerStats.getTimePlayed()+", "
                + "verbosity="+playerStats.getVerbosity()
                + " WHERE player_id = "+playerId
        );
        System.out.println("The stats of "+playerStats.getPseudo()+" have been updated");
    }

    /**
     * getPlayerStats method retrieve the statistics of a player, and load them in a PlayerStats object
     * @param String pseudo : Contains the pseudo of a player
     * @return PlayerStats playerStats : The stats object of the player
     */
    public static PlayerStats getPlayerStats(String pseudo)
    {
        int playerId = MysqlPlayerManager.getPlayerIdFromPseudo(pseudo);
        PlayerStats playerStats = new PlayerStats();
        ResultSet resultat = StatsPlugin.connection.getData("SELECT * FROM PlayerStats WHERE player_id="+playerId);
        try
        {
            if (resultat != null && resultat.next())
            {
                    playerStats.setPseudo(pseudo);
                    playerStats.setBlocksBroken(resultat.getInt(2));
                    playerStats.setBlocksPlaced(resultat.getInt(3));
                    playerStats.setStupidDeaths(resultat.getInt(4));
                    playerStats.setNormalDeath(resultat.getInt(5));
                    playerStats.setKills(resultat.getInt(6));
                    playerStats.setTimePlayed(resultat.getLong(7));
                    playerStats.setVerbosity(resultat.getInt(8));
                    return playerStats;
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
    }
}