package net.ovski.minecraft.stats.events;

import net.ovski.minecraft.stats.StatsPlugin;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;

import net.ovski.minecraft.api.entities.PlayerStats;
import net.ovski.minecraft.api.mysql.MysqlKillManager;
import net.ovski.minecraft.api.mysql.MysqlPlayerManager;

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
        if (playerKilledStats == null)
        {
        	return;
        }
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
            if (playerKillerStats == null) { return; }
            playerKillerStats.incrementKills();

            //add a new kill in PlayerKill table
            int killer_id = MysqlPlayerManager.getPlayerIdFromPseudo(playerKiller.getName());
            int killed_id = MysqlPlayerManager.getPlayerIdFromPseudo(playerKilled.getName());
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);

            MysqlKillManager.insertKill(killer_id, killed_id, playerKiller.getItemInHand().getTypeId(), currentTime);
        }
        //stupid kill
        else
        {
            playerKilledStats.incrementStupidDeaths();
        }

        MysqlPlayerManager.updateStats(playerKilledStats);
    }
}