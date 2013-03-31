package studmine.studstats.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;

import studmine.mysqlmanager.MysqlKillManager;
import studmine.mysqlmanager.MysqlPlayerManager;
import studmine.mysqlmanager.MysqlStatsManager;
import studmine.studstats.PlayerStats;
import studmine.studstats.StatsPlugin;

public class OnPlayerDeath implements Listener
{
    public OnPlayerDeath(StatsPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Date date = new Date();
        Player playerKilled = (Player) event.getEntity();
        PlayerStats playerKilledStats = StatsPlugin.getPlayerStats(playerKilled.getName());

        if (playerKilled.getKiller() instanceof Player)
        {
            playerKilledStats.incrementNormalDeaths();

            Player playerKiller = playerKilled.getKiller();
            PlayerStats playerKillerStats = StatsPlugin.getPlayerStats(playerKiller.getName());
            playerKillerStats.incrementKills();

            //add a new kill in PlayerKill table
            int killer_id = MysqlPlayerManager.getPlayerIdFromPseudo(playerKiller.getName());
            int killed_id = MysqlPlayerManager.getPlayerIdFromPseudo(playerKilled.getName());
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);

            MysqlKillManager.insertKill(killer_id, killed_id, playerKiller.getItemInHand().getTypeId(), currentTime);
        }
        else
        {
            playerKilledStats.incrementStupidDeaths();
        }

        MysqlStatsManager.updatePlayerStats(playerKilledStats);
    }
}