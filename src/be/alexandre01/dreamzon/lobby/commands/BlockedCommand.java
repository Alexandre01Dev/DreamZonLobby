package be.alexandre01.dreamzon.lobby.commands;

import be.alexandre01.dreamzon.lobby.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class BlockedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("blockcmd")){
                if(args[0].equalsIgnoreCase("add")){
                    if(args.length == 2){
                        for(String commands : Main.getInstance().commands){
                            if(commands.equals(args[1])){
                                player.sendMessage("Cette commande est déjà dans la liste");
                                return false;
                            }
                        }
                        Main.getInstance().commands.add(args[1]);
                        Main.getInstance().commandConfig.set("commands" ,Main.getInstance().commands);
                        try {
                            Main.getInstance().commandConfig.save(Main.getInstance().commandFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        player.sendMessage("Erreur");
                    }
                }else {
                    if(args[0].equalsIgnoreCase("del")){
                        if(args.length == 2){
                            int counter = 0;
                            for(String commands : Main.getInstance().commands){
                                if(commands.equals(args[1])){
                                    Main.getInstance().commands.remove(args[1]);
                                    Main.getInstance().commandConfig.set("commands",Main.getInstance().commands);
                                    try {
                                        Main.getInstance().commandConfig.save(Main.getInstance().commandFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    player.sendMessage("Cette commande à été delete de la liste");
                                    return false;
                                }

                                if(counter == Main.getInstance().commands.size()){
                                    player.sendMessage("La commande n'as pas été trouvé");
                                }
                                counter++;
                            }

                        }else {
                            player.sendMessage("Erreur");
                        }
                    }else {
                        if(args[0].equalsIgnoreCase("list")){
                            for(String commands : Main.getInstance().commands){
                                player.sendMessage("- "+commands);
                            }
                        }else {
                            player.sendMessage("Command Introuvable");
                            player.sendMessage("/blockcmd add/del commande");
                        }

                    }
                }
            }
        }
        return false;
    }
}
