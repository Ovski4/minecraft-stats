package studmine.mysqlmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

import studmine.studstats.PlayerStats;
import studmine.studstats.StatsPlugin;

public class MysqlStatsManager
{
    public static void updatePlayerStats(PlayerStats playerStats)
    {
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
        System.out.println("the stats of "+playerStats.getPseudo()+" have been updated");
    }

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
                    playerStats.setBlocksBroken(resultat.getInt(1));
                    playerStats.setBlocksPlaced(resultat.getInt(2));
                    playerStats.setStupidDeaths(resultat.getInt(3));
                    playerStats.setNormalDeath(resultat.getInt(4));
                    playerStats.setKills(resultat.getInt(5));
                    playerStats.setTimePlayed(resultat.getInt(6));
                    playerStats.setVerbosity(resultat.getInt(7));
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