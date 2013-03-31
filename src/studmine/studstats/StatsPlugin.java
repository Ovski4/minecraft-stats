package studmine.studstats;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.bukkit.plugin.java.JavaPlugin;

import studmine.library.StudConnection;
import studmine.mysqlmanager.MysqlStatsManager;
import studmine.studstats.commands.*;
import studmine.studstats.events.*;

public class StatsPlugin extends JavaPlugin
{
    public static StudConnection connection = null;
    public static List<PlayerStats> playerStatsList;
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
        this.getCommands();
        this.initVariables();

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

    public void getCommands()
    {
        getCommand("stats").setExecutor(new StatsCommand(this));
    }

    public void listenEvents()
    {
        if (this.getConfig().getBoolean("StatsToBeRegistered.blockBreak"))
            new OnBlockBreak(this);
        if (this.getConfig().getBoolean("StatsToBeRegistered.blockPlace"))
            new OnBlockPlace(this);
        if (this.getConfig().getBoolean("StatsToBeRegistered.deaths"))
            new OnPlayerDeath(this);
        if (this.getConfig().getBoolean("StatsToBeRegistered.verbosity"))
            new OnPlayerChat(this);
        new OnPlayerJoin(this);
        new OnPlayerQuit(this);
    }

    public void initVariables()
    {
        StatsPlugin.timeBetweenSaves = this.getConfig().getInt("TimebetweenSaves");
        StatsPlugin.lastSaveTime = new Date().getTime();
        StatsPlugin.connection = StudConnection.getStudConnection("jdbc:mysql://"+this.getConfig().getString("MySQL.Host")+":3306/"+this.getConfig().getString("MySQL.Database"), this.getConfig().getString("MySQL.Login"), this.getConfig().getString("MySQL.Password"));
        StatsPlugin.playerStatsList  = new ArrayList<PlayerStats>();
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