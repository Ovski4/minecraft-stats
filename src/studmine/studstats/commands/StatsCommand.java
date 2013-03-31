package studmine.studstats.commands;

import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import studmine.studstats.PlayerStats;
import studmine.studstats.StatsPlugin;

/**
 * StatsCommand
 * this command permit to display statistics of a player
 * @author Ovski
 */
public class StatsCommand implements CommandExecutor
{
    private StatsPlugin plugin;

    public StatsCommand(StatsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
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
                if(plugin.getServer().getPlayer(args[0]).isOnline())
                {
                    PlayerStats playerStats = StatsPlugin.getPlayerStats(args[0]);
                    plugin.reloadConfig();
                    commandPlayer.sendMessage(getFormettedStats(playerStats));
                }
                else
                {
                    commandPlayer.sendMessage("Pour pouvoir consulter ses statistiques, le joueur "+args[0]+" doit être en ligne.");
                    return true;
                }
            }

            commandPlayer.sendMessage("Syntaxe de la commande: /stats nomJoueur (nomJoueur facultatif pour vos propres statistiques)");
            return true;
        }
        return false;
    }

    /**
     * getFormettedStats method format the statistic in a string
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
            blockBreakString = "blocs cassés: "+playerStats.getBlocksBroken()+"\n";
        }
        String blockPlaceString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.blockPlace"))
        {
            containStats = true;
            blockPlaceString =  "blocs placés: "+playerStats.getBlocksPlaced()+"\n";
        }
        String deathsString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.deaths"))
        {
            containStats = true;
            deathsString =  "nombre de tués: "+playerStats.getKills()+"\n"
                    + "nombre de morts: "+playerStats.getTotalDeaths()+"\n"
                    + "ratio tués/morts: "+String.valueOf(playerStats.getRatio())+"\n";
        }
        String timeplayedString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.timeplayed"))
        {
            containStats = true;
            timeplayedString = "temps de jeu: "+playerStats.getFormattedTimePlayed()+"\n";
        }
        String verbosityString = "";
        if (plugin.getConfig().getBoolean("StatsToBeRegistered.verbosity"))
        {
            containStats = true;
            verbosityString = "bavardise: "+playerStats.getVerbosity();
        }

        if(containStats)
        {
            return "--------- "+playerStats.getPseudo()+" - statistiques ---------\n"
            + blockBreakString
            + blockPlaceString
            + deathsString
            + timeplayedString
            + verbosityString
            ;
        }
        else
        {
            return "Desole, aucune statistique n'est activee";
        }
    }

}
