package ovski.minecraft.stats.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import ovski.minecraft.api.entities.PlayerStats;
import ovski.minecraft.api.mysql.MysqlPlayerManager;
import ovski.minecraft.stats.StatsPlugin;

/**
 * OnPlayerQuit
 * 
 * Things to do on player quit event
 *
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class OnPlayerQuit implements Listener
{
    StatsPlugin plugin;

    /**
     * Constructor
     * 
     * @param plugin
     */
    public OnPlayerQuit(StatsPlugin plugin)
    {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * On player quit
     * 
     * @param event
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        //update the stats of the player and remove him from the list
        Player player = event.getPlayer();
        try {
            PlayerStats playerStats = StatsPlugin.getPlayerStats(player.getName());
            if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed")) {
                long timeOnQuit = new Date().getTime();
                long timePlayed = timeOnQuit-playerStats.getTimeSinceLastSave();
                playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
            }
            MysqlPlayerManager.updateStats(playerStats);
            StatsPlugin.playerStatsList.remove(playerStats);
        } catch (NullPointerException e) {
            return; 
        }
    }
}