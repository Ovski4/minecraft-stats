package studmine.studstats.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import java.util.Date;

import studmine.studstats.StatsPlugin;

public class OnPlayerDeath implements Listener {

    public OnPlayerDeath(StatsPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player playerKilled = (Player) event.getEntity();
        if (playerKilled.getKiller() instanceof Player) {
            Player playerKiller = playerKilled.getKiller();
            Date date = new Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);
            String sql =
                    "INSERT INTO `PlayerKill`(`player_killed_id`, `player_killer_id`, `date`, `weapon`) "+
                    "VALUES ("+playerKilled.getEntityId()+
                    ","+playerKiller.getEntityId()+
                    ",'"+currentTime+
                    "',"+playerKiller.getItemInHand().getTypeId()+
                    ")";
            System.out.println(sql);
            StatsPlugin.connection.sendData(sql);
        }
    }
}