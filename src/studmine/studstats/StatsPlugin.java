package studmine.studstats;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.bukkit.plugin.java.JavaPlugin;

import studmine.library.StudConnection;
import studmine.mysqlmanager.MysqlStatsManager;
import studmine.studstats.events.OnBlockBreak;
import studmine.studstats.events.OnBlockPlace;
import studmine.studstats.events.OnPlayerDeath;
import studmine.studstats.events.OnPlayerJoin;

public class StatsPlugin extends JavaPlugin
{

    public static StudConnection connection = null;
    public static List<PlayerStats> playerStatsList = new ArrayList<PlayerStats>();
    public static long timeBetweenSaves;
    public static long lastSaveTime;

    /**
     * onEnable method called when the plugin is loading
     */
    @Override
    public void onEnable()
    {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.listenEvents();
        StatsPlugin.timeBetweenSaves = this.getConfig().getInt("TimebetweenSaves");
        StatsPlugin.lastSaveTime = new Date().getTime();
        StatsPlugin.connection = StudConnection.getStudConnection("jdbc:mysql://"+this.getConfig().getString("MySQL.Host")+":3306/"+this.getConfig().getString("MySQL.Database"), this.getConfig().getString("MySQL.Login"), this.getConfig().getString("MySQL.Password"));
        getLogger().info(this.getName()+" v"+this.getDescription().getVersion()+" enabled");
    }

    /**
     * onDisable method called when the plugin is unloading
     */
    @Override
    public void onDisable()
    {
        // Save all the stats datas
        
        for (PlayerStats playerStats : StatsPlugin.playerStatsList)
        {
            MysqlStatsManager.updatePlayerStats(playerStats);
        }
        getLogger().info(this.getName()+" v"+this.getDescription().getVersion()+" disabled");
    }

    public void listenEvents()
    {
        new OnPlayerDeath(this);
        new OnPlayerJoin(this);
        new OnBlockBreak(this);
        new OnBlockPlace(this);
    }

    public static PlayerStats getPlayerStats(String pseudo)
    {
        for (PlayerStats playerStats : StatsPlugin.playerStatsList)
        {
            if(playerStats.getPseudo().equals(pseudo))
            {
                return playerStats;
            }
        }
        return null;
    }
}

//TODO base de donnee etre consistant sur les noms de champ dans les tables