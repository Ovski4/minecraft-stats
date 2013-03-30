package studmine.mysqlmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

import studmine.studstats.StatsPlugin;

public class MysqlPlayerManager
{
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