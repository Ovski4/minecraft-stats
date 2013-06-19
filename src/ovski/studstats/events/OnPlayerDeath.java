package ovski.studstats.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;

import ovski.api.entities.PlayerStats;
import ovski.api.mysql.MysqlKillManager;
import ovski.api.mysql.MysqlStatsManager;
import ovski.api.mysql.MysqlUserManager;
import ovski.studstats.StatsPlugin;

public class OnPlayerDeath implements Listener
{
    public OnPlayerDeath(StatsPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Date date = new Date();
        Player playerKilled = (Player) event.getEntity();
        PlayerStats playerKilledStats = StatsPlugin.getPlayerStats(playerKilled.getName());

        //killed by a NPC
        if (!(playerKilled.getKiller() instanceof Player) && playerKilled.getKiller() instanceof HumanEntity)
        {
            playerKilledStats.incrementNormalDeaths();
        }
        //killed by a player
        else if (playerKilled.getKiller() instanceof Player)
        {
            playerKilledStats.incrementNormalDeaths();
            Player playerKiller = playerKilled.getKiller();
            PlayerStats playerKillerStats = StatsPlugin.getPlayerStats(playerKiller.getName());
            playerKillerStats.incrementKills();

            //add a new kill in PlayerKill table
            int killer_id = MysqlUserManager.getPlayerIdFromPseudo(playerKiller.getName());
            int killed_id = MysqlUserManager.getPlayerIdFromPseudo(playerKilled.getName());
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);

            MysqlKillManager.insertKill(killer_id, killed_id, playerKiller.getItemInHand().getTypeId(), currentTime);
        }
        //stupid kill
        else
        {
            playerKilledStats.incrementStupidDeaths();
        }

        MysqlStatsManager.updatePlayerStats(playerKilledStats);
    }
}