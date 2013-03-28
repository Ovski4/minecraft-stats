package studmine.studstats;

import org.bukkit.plugin.java.JavaPlugin;

import studmine.library.StudConnection;
import studmine.studstats.events.OnPlayerDeath;


public class StatsPlugin extends JavaPlugin {

    public static StudConnection connection = null;

    /**
     * onEnable method called when the plugin is loading
     */
    @Override
    public void onEnable()
    {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.listenEvents();
        StatsPlugin.connection = StudConnection.getStudConnection("jdbc:mysql://"+this.getConfig().getString("MySQL.Host")+":3306/"+this.getConfig().getString("MySQL.Database"), this.getConfig().getString("MySQL.Login"), this.getConfig().getString("MySQL.Password"));
        getLogger().info(this.getName()+" v"+this.getDescription().getVersion()+" enabled");
    }

    /**
     * onDisable method called when the plugin is unloading
     */
    @Override
    public void onDisable()
    {
        getLogger().info(this.getName()+" v"+this.getDescription().getVersion()+" disabled");
    }

    public void listenEvents() {
        new OnPlayerDeath(this);
    }
}