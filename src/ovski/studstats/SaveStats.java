package ovski.studstats;

import java.util.Date;
import org.bukkit.scheduler.BukkitRunnable;
import ovski.api.entities.PlayerStats;
import ovski.api.mysql.MysqlStatsManager;

/**
 * SaveStats
 * Save the statistic each period (= timeBetweensaves in config.yml)
 * @author Ovski
 */
public class SaveStats extends BukkitRunnable {

    private StatsPlugin plugin;

    public SaveStats(StatsPlugin plugin)
    {
        this.plugin = plugin;
    }
    public void run() {
        long thisTime = new Date().getTime();
        for (PlayerStats stats : StatsPlugin.playerStatsList)
        {
            if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
            {
                //set the time played by the player
                long timeOnSave = new Date().getTime();
                long timePlayed = timeOnSave-stats.getTimeSinceLastSave();
                stats.setTimePlayed(stats.getTimePlayed()+timePlayed);
                //reset the time since the last save for next time
                stats.setTimeSinceLastSave(timeOnSave);
            }
            MysqlStatsManager.updatePlayerStats(stats);
        }
        StatsPlugin.lastSaveTime = thisTime;
    }
}