package studmine.mysqlmanager;

import studmine.studstats.StatsPlugin;

/**
 * MysqlKillManager
 * Manage requests involving the kill table
 * Require a plugin with a database connection
 * @author Ovski
 */
public class MysqlKillManager {

    public static void insertKill(int killerId, int killedId, int weaponId, String date)
    {
        /**
         * insertKill method insert a new kill in database
         * @param int killerId : Contains the id of the killer
         * @param int killedId : Contains the id of the killed player
         * @param int weaponId : Contains the id of the weapon use for the kill
         * @param String date : Contains the date of the kill
         */
        StatsPlugin.connection.sendData("INSERT INTO kill (killed_user_id, killer_user_id, date, weapon_id) "
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
}