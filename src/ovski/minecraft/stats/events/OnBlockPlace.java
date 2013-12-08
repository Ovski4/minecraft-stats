package ovski.minecraft.stats.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import ovski.minecraft.api.entities.PlayerStats;
import ovski.minecraft.stats.StatsPlugin;

/**
 * OnBlockPlace
 * 
 * Things to do on block place event
 *
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class OnBlockPlace implements Listener
{
    /**
     * Constructor
     * 
     * @param plugin
     */
    public OnBlockPlace(StatsPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * On block place
     * 
     * @param event
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        //increment the number of blocks placed in playerStats object
        Player player = event.getPlayer();
        PlayerStats playerStats = StatsPlugin.getPlayerStats(player.getName());
        if (playerStats != null) {
            playerStats.incrementBlocksPlaced();
        }
    }
}