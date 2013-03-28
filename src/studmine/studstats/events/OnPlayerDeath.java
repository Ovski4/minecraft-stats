package studmine.studstats.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import studmine.studstats.StatsPlugin;

public class OnPlayerDeath implements Listener {

    public OnPlayerDeath(StatsPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player playerKilled = (Player) event.getEntity();
        if (playerKilled.getKiller() instanceof Player) {
            Player playerKiller = playerKilled.getKiller();
            String sql_select_killer_id = "SELECT id FROM Player WHERE pseudo='"
                    + playerKiller.getName() + "'";
            String sql_select_killed_id = "SELECT id FROM Player WHERE pseudo='"
                    + playerKilled.getName() + "'";
            System.out.println(sql_select_killed_id);
            int killer_id = -1;
            int killed_id = -1;
            ResultSet resultat1 = StatsPlugin.connection.getData(sql_select_killer_id);
            try {
                if (resultat1 != null && resultat1.next()) {
                    killer_id = resultat1.getInt("id");
                    System.out.println("id = "+killer_id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ResultSet resultat2 = StatsPlugin.connection.getData(sql_select_killed_id);
            try {
                if (resultat2 != null && resultat2.next()) {
                    killed_id = resultat2.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Date date = new Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);
            String sql_insert = "INSERT INTO PlayerKill(player_killed_id, player_killer_id, date, weapon) "
                    + "VALUES ("
                    + killed_id
                    + ","
                    + killer_id
                    + ",'"
                    + currentTime
                    + "',"
                    + playerKiller.getItemInHand().getTypeId() + ")";

            System.out.println(sql_insert);
            StatsPlugin.connection.sendData(sql_insert);
        }
    }
}