package studmine.mysqlmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

import studmine.studstats.StatsPlugin;

/**
 * MysqlPlayerManager
 * Manage requests involving the Player table
 * Require a plugin with a database connection
 * @author Ovski
 */
public class MysqlPlayerManager
{
    /**
     * getPlayerIdFromPseudo method retrieve the id of a player according to his pseudo
     * @param String pseudo : Contains the pseudo of the player
     * @return int playerId : The id of the player
     */
    public static int getPlayerIdFromPseudo(String pseudo)
    {
        ResultSet resultat = StatsPlugin.connection.getData("SELECT id FROM Player WHERE pseudo='"+pseudo+"'");
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
}