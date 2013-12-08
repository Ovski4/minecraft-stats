package ovski.minecraft.stats.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ovski.minecraft.api.entities.PlayerStats;
import ovski.minecraft.api.mysql.MysqlNoteManager;
import ovski.minecraft.api.mysql.MysqlPlayerManager;
import ovski.minecraft.stats.NoteTools;
import ovski.minecraft.stats.StatsPlugin;

/**
 * NoterCommand
 * 
 * Note a player
 * 
 * @author baptiste <baptiste.bouchereau@gmail.com>
 */
public class NoterCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player) {
            Player commandPlayer = (Player) sender;
            PlayerStats stats = StatsPlugin.getPlayerStats(commandPlayer.getName());
            if (stats == null) {
                commandPlayer.sendMessage(ChatColor.RED+"Vous devez vous enregistrer!");

                return true; 
            }
            // we check if there are enough arguments
            if (args.length != 2) {
                commandPlayer.sendMessage(ChatColor.RED+"Erreur de syntaxe: /noter <NomDuJoueur> <valeur de -5 a 5");

                return true;
            }
            //we check if the player exists
            if(!MysqlPlayerManager.exists(args[0])) {
                commandPlayer.sendMessage(ChatColor.RED+args[0]+" est inconnu sur ce serveur");

                return true;
            }
            //we check if the player wants to note himself
            if(commandPlayer.getName().equals(args[0])) {
                commandPlayer.sendMessage(ChatColor.RED+"Vous ne pouvez pas vous noter vous même!");

                return true;
            }
            //we check if the value is an integer
            try {
                int newNote = Integer.parseInt(args[1]);
                //we check if the value is between -5 and 5
                if(Math.abs(newNote) > 5) {
                    commandPlayer.sendMessage(ChatColor.RED+"La note doit être un entier compris entre -5 et 5");

                    return true;
                }
                String donorPseudo = commandPlayer.getName();
                //we check if the value is authorized for this player
                if(!NoteTools.canNoteWithValue(newNote, donorPseudo)) {
                    int canNote = NoteTools.getNoteMaximaleValue(donorPseudo);
                    commandPlayer.sendMessage(ChatColor.RED+"Vous n'avez pas suffisemment d'ancienneté pour noter jusqu'à "+newNote+". Vous etes limité de -"+canNote+" à "+canNote);

                    return true;
                }

                PlayerStats playerStats = StatsPlugin.getPlayerStats(args[0]);
                //we check if the given player was already noted by the commandPlayer
                if(MysqlNoteManager.noteExists(donorPseudo, args[0])) {
                    int previousNote = MysqlNoteManager.getValueOfNote(donorPseudo, args[0]);
                                      int noteId = MysqlNoteManager.getIdOfNote(donorPseudo, args[0]);
                    //we update the note and the prestige
                    if(playerStats != null) {
                        playerStats.setPrestige(playerStats.getPrestige()-previousNote+newNote);
                    } else {
                        int previousPrestige = MysqlPlayerManager.getPrestige(args[0]);
                        MysqlPlayerManager.updatePrestige(args[0], previousPrestige-previousNote+newNote);
                    }
                    MysqlNoteManager.updateNote(noteId, newNote);
                    commandPlayer.sendMessage(ChatColor.BLUE+"Note ajoutée!");

                    return true;
                } else {
                    //we insert a note and update the prestige
                    MysqlNoteManager.insertNote(donorPseudo, args[0], newNote);
                    if(playerStats != null) {
                        playerStats.setPrestige(playerStats.getPrestige()+newNote);
                    } else {
                        int previousPrestige = MysqlPlayerManager.getPrestige(args[0]);
                        MysqlPlayerManager.updatePrestige(args[0], previousPrestige+newNote);
                    }
                    commandPlayer.sendMessage(ChatColor.BLUE+"Note ajoutée!");

                    return true;
                }
            } catch (NumberFormatException nfe) {
                commandPlayer.sendMessage(ChatColor.RED+"La note doit être un entier compris entre -5 et 5");

                return true;
            }
        }

        return false;
    }
}
