package ovski.minecraft.stats.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ovski.minecraft.api.entities.PlayerStats;
import ovski.minecraft.stats.StatsPlugin;

/**
 * OnPlayerChat
 * 
 * Things to do on player chat event
 *
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class OnPlayerChat implements Listener
{
    /**
     * Constructor
     * 
     * @param plugin
     */
    public OnPlayerChat(StatsPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * On player chat
     * 
     * @param event
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        //increment the number of messages sent (verbosity) in playerStats object
        Player player = event.getPlayer();
        PlayerStats playerStats = StatsPlugin.getPlayerStats(player.getName());
        if (playerStats != null) {
             playerStats.incrementVerbosity();
        }
    }
}
