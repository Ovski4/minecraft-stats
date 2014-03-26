package net.ovski.minecraft.stats.events;

import java.util.Date;

import net.ovski.minecraft.stats.StatsPlugin;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import net.ovski.minecraft.api.entities.PlayerStats;
import net.ovski.minecraft.api.mysql.MysqlPlayerManager;

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
        //update the stats of the player and remove him from the list
        Player player = event.getPlayer();
        try
        {
			PlayerStats playerStats = StatsPlugin.getPlayerStats(player.getName());
        	if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
            {
                long timeOnQuit = new Date().getTime();
                long timePlayed = timeOnQuit-playerStats.getTimeSinceLastSave();
                playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
            }
            MysqlPlayerManager.updateStats(playerStats);
            StatsPlugin.playerStatsList.remove(playerStats);
        }
        catch (NullPointerException e)
        {
        	return; 
        }
    }
}