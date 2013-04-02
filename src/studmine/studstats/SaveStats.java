package studmine.studstats;

import java.util.Date;
import org.bukkit.scheduler.BukkitRunnable;
import studmine.mysqlmanager.MysqlStatsManager;


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
                long timeOnQuit = new Date().getTime();
                long timePlayed = timeOnQuit-stats.getTimeSinceLastSave();
                stats.setTimePlayed(stats.getTimePlayed()+timePlayed);
            }
            MysqlStatsManager.updatePlayerStats(stats);
        }
        StatsPlugin.lastSaveTime = thisTime;
    }
 
}