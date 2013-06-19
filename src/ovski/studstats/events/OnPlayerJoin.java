package ovski.studstats.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import ovski.api.entities.PlayerStats;
import ovski.api.mysql.MysqlStatsManager;
import ovski.studstats.StatsPlugin;

public class OnPlayerJoin implements Listener
{
    StatsPlugin plugin;

    public OnPlayerJoin(StatsPlugin plugin)
    {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        //add the player to the list
        PlayerStats playerStats = MysqlStatsManager.getPlayerStats(player.getName());
        //initialize the time (it will be used when a player launch statsCommand or when he leaves)
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
        {
            long timeOnJoin = new Date().getTime();
            playerStats.setTimeSinceLastSave(timeOnJoin);
        }
        StatsPlugin.playerStatsList.add(playerStats);
    }
}