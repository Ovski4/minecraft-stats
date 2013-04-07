package studmine.mysqlmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

import studmine.studstats.StatsPlugin;

/**
 * MysqlNoteManager
 * Manage the requests involving the note table
 * Require a plugin with a database connection
 * @author Ovski
 */
public class MysqlNoteManager {

    /**
     * insertNote method insert a new note entry in database
     * @param int donorId : Contains the id of the donor
     * @param int receiverId : Contains the id of the killed recever
     * @param int value : Contains the value of the note (=0, <0 or >0)
     */
    public static void insertNote(String donorPseudo, String receiverPseudo, int value)
    {
        int receiverId = MysqlUserManager.getPlayerIdFromPseudo(receiverPseudo);
        int donorId = MysqlUserManager.getPlayerIdFromPseudo(donorPseudo);
        StatsPlugin.connection.sendData("INSERT INTO note (receiver_user_id, donor_user_id, value) "
                + "VALUES ("
                + receiverId
                + ","
                + donorId
                + ","
                + value+ ")"
        );
    }

    /**
     * exists method check if a note exist in database
     * @param String donorPseudo : Contains the pseudo of the donor of the note
     * @param String receiverPseudo : Contains the pseudo of the receiver of the note
     * @return boolean
     */
    public static boolean noteExists(String donorPseudo, String receiverPseudo)
    {
        int donorId = MysqlUserManager.getPlayerIdFromPseudo(donorPseudo);
        int receiverId = MysqlUserManager.getPlayerIdFromPseudo(receiverPseudo);
        ResultSet resultat = StatsPlugin.connection.getData("SELECT note_id FROM note "
        + "WHERE receiver_user_id="+receiverId+" AND donor_user_id="+donorId);
        try
        {
            if (resultat != null && resultat.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * getValueOfNote return the value of a note
     * @param String donorPseudo : Contains the pseudo of the donor of the note
     * @param String receiverPseudo : Contains the pseudo of the receiver of the note
     * @return int: value
     */
    public static int getValueOfNote(String donorPseudo, String receiverPseudo)
    {
        int donorId = MysqlUserManager.getPlayerIdFromPseudo(donorPseudo);
        int receiverId = MysqlUserManager.getPlayerIdFromPseudo(receiverPseudo);
        ResultSet resultat = StatsPlugin.connection.getData("SELECT value FROM note "
        + "WHERE receiver_user_id="+receiverId+" AND donor_user_id="+donorId);
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
     * getIdOfNote return the id of a note
     * @param String donorPseudo : Contains the pseudo of the donor of the note
     * @param String receiverPseudo : Contains the pseudo of the receiver of the note
     * @return int: id
     */
    public static int getIdOfNote(String donorPseudo, String receiverPseudo)
    {
        int donorId = MysqlUserManager.getPlayerIdFromPseudo(donorPseudo);
        int receiverId = MysqlUserManager.getPlayerIdFromPseudo(receiverPseudo);
        ResultSet resultat = StatsPlugin.connection.getData("SELECT note_id FROM note "
        + "WHERE receiver_user_id="+receiverId+" AND donor_user_id="+donorId);
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
     * updateNote method update a note
     * @param int note_id : Contains the id of the note
     */
    public static void updateNote(int noteId, int value)
    {
        StatsPlugin.connection.sendData("UPDATE note SET "
                + "value="+value
                + " WHERE note_id = "+noteId
        );
    }
}