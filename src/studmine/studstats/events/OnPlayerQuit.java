package studmine.studstats.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import studmine.mysqlmanager.MysqlStatsManager;
import studmine.studstats.PlayerStats;
import studmine.studstats.StatsPlugin;

public class OnPlayerQuit implements Listener
{
    StatsPlugin plugin;

    public OnPlayerQuit(StatsPlugin plugin)
    {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        PlayerStats playerStats =  StatsPlugin.getPlayerStats(player.getName());
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
        {
            long timeOnQuit = new Date().getTime();
            long timePlayed = timeOnQuit-playerStats.getTimeSinceLastSave();
            playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
        }
        MysqlStatsManager.updatePlayerStats(playerStats);
        StatsPlugin.playerStatsList.remove(playerStats);
    }
}