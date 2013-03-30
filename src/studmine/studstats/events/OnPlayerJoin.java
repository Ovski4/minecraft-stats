package studmine.studstats.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import studmine.mysqlmanager.MysqlStatsManager;
import studmine.studstats.PlayerStats;
import studmine.studstats.StatsPlugin;

public class OnPlayerJoin implements Listener
{
    public OnPlayerJoin(StatsPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        //ajout du joueur a la liste
        PlayerStats playerStats = MysqlStatsManager.getPlayerStats(player.getName());
        StatsPlugin.playerStatsList.add(playerStats);
    }
}