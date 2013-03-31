package studmine.studstats.events;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import studmine.mysqlmanager.MysqlStatsManager;
import studmine.studstats.PlayerStats;
import studmine.studstats.StatsPlugin;

public class OnBlockPlace implements Listener
{
    public OnBlockPlace(StatsPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        //increment the number of blocks placed in playerStats object
        Player player = event.getPlayer();
        PlayerStats playerStats = StatsPlugin.getPlayerStats(player.getName());
        playerStats.incrementBlocksPlaced();

      //save all stats if time since last time is > at time in the config.yml file
        long thisTime = new Date().getTime();
        if ((thisTime-StatsPlugin.lastSaveTime)>(StatsPlugin.timeBetweenSaves*1000))
        {
            for (PlayerStats stats : StatsPlugin.playerStatsList)
            {
                MysqlStatsManager.updatePlayerStats(stats);
            }
            StatsPlugin.lastSaveTime = thisTime;
        }
    }
}