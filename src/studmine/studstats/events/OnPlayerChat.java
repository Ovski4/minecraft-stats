package studmine.studstats.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import studmine.api.entities.PlayerStats;
import studmine.studstats.StatsPlugin;

public class OnPlayerChat implements Listener {
    public OnPlayerChat(StatsPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        //increment the number of messages sent (verbosity) in playerStats object
        Player player = event.getPlayer();
        PlayerStats playerStats = StatsPlugin.getPlayerStats(player.getName());
        playerStats.incrementVerbosity();

        //save all stats if time since last time is > at time in the config.yml
        /*long thisTime = new Date().getTime();
        if ((thisTime-StatsPlugin.lastSaveTime)>(StatsPlugin.timeBetweenSaves*1000))
        {
            for (PlayerStats stats : StatsPlugin.playerStatsList)
            {
                MysqlStatsManager.updatePlayerStats(stats);
            }
            StatsPlugin.lastSaveTime = thisTime;
        }*/
    }
}
