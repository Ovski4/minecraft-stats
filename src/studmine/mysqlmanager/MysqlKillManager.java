package studmine.mysqlmanager;

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
}