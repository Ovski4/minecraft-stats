package studmine.studstats.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import studmine.studstats.PlayerStats;
import studmine.studstats.StatsPlugin;

public class OnPlayerQuit implements Listener
{

    public OnPlayerQuit(StatsPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        //suppression du joueur de la liste
        for (PlayerStats playerStats : StatsPlugin.playerStatsList)
        {
            if(playerStats.getPseudo().equals(player.getName()))
            {
                StatsPlugin.playerStatsList.remove(playerStats);
            }
        }
    }
}