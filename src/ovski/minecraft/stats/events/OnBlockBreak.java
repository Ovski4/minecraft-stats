package ovski.minecraft.stats.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import ovski.minecraft.api.entities.PlayerStats;
import ovski.minecraft.stats.StatsPlugin;

public class OnBlockBreak implements Listener
{
    public OnBlockBreak(StatsPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        //increment the number of blocks broken in playerStats object
        Player player = event.getPlayer();
        PlayerStats playerStats = StatsPlugin.getPlayerStats(player.getName());
        if (playerStats != null)
        {
        	playerStats.incrementBlocksBroken();
        }
    }
}