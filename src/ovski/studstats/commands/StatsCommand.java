package ovski.studstats.commands;

import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import ovski.api.entities.PlayerStats;
import ovski.api.mysql.MysqlPlayerManager;
import ovski.studstats.StatsPlugin;

/**
 * StatsCommand
 * this command permit to display statistics of a player
 * @author Ovski
 */
public class StatsCommand implements CommandExecutor
{
    private StatsPlugin plugin;

    public StatsCommand(StatsPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player commandPlayer = (Player) sender;

            if (args.length == 0)
            {
                PlayerStats playerStats = StatsPlugin.getPlayerStats(commandPlayer.getName());
                if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
                {
                    //set the time played by the player
                    long timeOnCommand = new Date().getTime();
                    long timePlayed = timeOnCommand-playerStats.getTimeSinceLastSave();
                    playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
                    //reset the time since the last save for next time
                    playerStats.setTimeSinceLastSave(timeOnCommand);
                }

                plugin.reloadConfig();
                commandPlayer.sendMessage(getFormettedStats(playerStats));
                return true;
            }

            if (args.length == 1)
            {
            	try {
	            	String playerName = plugin.getServer().getPlayer(args[0]).getName();
                    PlayerStats playerStats = StatsPlugin.getPlayerStats(playerName);
                    if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
                    {
                        //set the time played by the player
                        long timeOnCommand = new Date().getTime();
                        long timePlayed = timeOnCommand-playerStats.getTimeSinceLastSave();
                        playerStats.setTimePlayed(playerStats.getTimePlayed()+timePlayed);
                        //reset the time since the last save for next time
                        playerStats.setTimeSinceLastSave(timeOnCommand);
                    }

                    plugin.reloadConfig();
                    commandPlayer.sendMessage(getFormettedStats(playerStats));
                    return true;
            	} catch (NullPointerException e) {
                    //we check if the required player exists in database
                    if (MysqlPlayerManager.exists(args[0]))
                    {
                        PlayerStats playerStats = MysqlPlayerManager.getStats(args[0]);
                        plugin.reloadConfig();
                        commandPlayer.sendMessage(getFormettedStats(playerStats));
                    }
                    else
                    {
                        commandPlayer.sendMessage(ChatColor.RED+args[0]+" est inconnu sur ce serveur");
                    }
                    return true;
                }
            }

            commandPlayer.sendMessage(ChatColor.RED+"Syntaxe de la commande: /stats nomJoueur (nomJoueur facultatif pour vos propres statistiques)");
            return true;
        }
        return false;
    }

    /**
     * getFormettedStats method format the statistic of the player who launch the command in a string
     * @param PlayerStats playerStats
     * @return String formattedStats
     */
    public String getFormettedStats(PlayerStats playerStats)
    {
        boolean containStats = false;
        String blockBreakString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.blockBreak"))
        {
            containStats = true;
            blockBreakString = ChatColor.BLUE+"blocs cassés: "+ChatColor.WHITE+playerStats.getBlocksBroken()+"\n";
        }
        String blockPlaceString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.blockPlace"))
        {
            containStats = true;
            blockPlaceString =  ChatColor.BLUE+"blocs placés: "+ChatColor.WHITE+playerStats.getBlocksPlaced()+"\n";
        }
        String deathsString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.deaths"))
        {
            containStats = true;
            deathsString =  ChatColor.BLUE+"tués: "+ChatColor.WHITE+playerStats.getKills()+"\n"
                    + ChatColor.BLUE+"morts au combat: "+ChatColor.WHITE+playerStats.getNormalDeaths()+"\n"
                    + ChatColor.BLUE+"morts stupides: "+ChatColor.WHITE+playerStats.getStupidDeaths()+"\n"
                    + ChatColor.BLUE+"ratio tués/morts: "+ChatColor.WHITE+String.valueOf(playerStats.getRatio())+"\n";
        }
        String timeplayedString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
        {
            containStats = true;
            timeplayedString = ChatColor.BLUE+"temps de jeu: "+ChatColor.WHITE+playerStats.getFormattedTimePlayed()+"\n";
        }
        String verbosityString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.verbosity"))
        {
            containStats = true;
            verbosityString = ChatColor.BLUE+"bavardise: "+ChatColor.WHITE+playerStats.getVerbosity()+"\n";
        }
        String appreciationString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.prestige"))
        {
            containStats = true;
            appreciationString = ChatColor.BLUE+"prestige: "+ChatColor.WHITE+playerStats.getPrestige();
        }

        if(containStats)
        {
            return "--------- "+playerStats.getPseudo()+" - Statistiques ---------\n"
            + blockBreakString
            + blockPlaceString
            + deathsString
            + timeplayedString
            + verbosityString
            + appreciationString
            ;
        }
        else
        {
            return "Desole, aucune statistique n'est activee";
        }
    }

}
