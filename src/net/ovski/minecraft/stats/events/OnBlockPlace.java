package net.ovski.minecraft.stats.events;

import net.ovski.minecraft.stats.StatsPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.ovski.minecraft.api.entities.PlayerStats;

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
        if (playerStats != null)
        {
        	playerStats.incrementBlocksPlaced();
        }
    }
}