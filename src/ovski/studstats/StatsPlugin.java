package ovski.studstats;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.bukkit.plugin.java.JavaPlugin;

import ovski.api.entities.PlayerStats;
import ovski.api.mysql.MysqlPlayerManager;
import ovski.studstats.commands.*;
import ovski.studstats.events.*;

/**
 * StatsPlugin
 * Main class of the plugin
 * @author Ovski
 */
public class StatsPlugin extends JavaPlugin
{
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
        new SaveStats(this).runTaskTimer(this, 400, this.getConfig().getInt("TimebetweenSaves")*20);
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
            if (this.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
            {
                long timeOnServerDisable = new Date().getTime();
                long timePlayed = timeOnServerDisable-playerStats.getTimeSinceLastSave();
                playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
            }
            MysqlPlayerManager.updateStats(playerStats);
        }
        getLogger().info(this.getName()+" v"+this.getDescription().getVersion()+" disabled");
    }

    /**
     * getCommands method instantiate the "Commands" class
     */
    public void getCommands()
    {
        getCommand("stats").setExecutor(new StatsCommand(this));
        if (this.getConfig().getBoolean("StatsToBeRegistered.prestige"))
            getCommand("noter").setExecutor(new NoterCommand());
    }

    /**
     * listenEvents method instantiate the "Events" class, according to the config
     */
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

    /**
     * initVariables method initialize the variables of the plugin
     */
    public void initVariables()
    {
        StatsPlugin.timeBetweenSaves = this.getConfig().getInt("TimebetweenSaves");
        StatsPlugin.lastSaveTime = new Date().getTime();
        StatsPlugin.playerStatsList  = new ArrayList<PlayerStats>();
    }

    /**
     * getPlayerStats method retrieve a PlayerStats object in the playerStatsList
     * @param String pseudo
     * @return PlayerStats playerStats : Contains the stat object of a player
     */
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