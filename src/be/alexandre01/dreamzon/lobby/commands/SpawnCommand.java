package be.alexandre01.dreamzon.lobby.commands;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.utils.MessageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("spawn")){

                if(Main.getInstance().spawn == null){
                    player.sendMessage("§c§lDésolé , le spawn n'est pas trouvé dans les données du serveur");
                }else {
                    player.teleport(Main.getInstance().spawn);
                }
            }
            if(cmd.getName().equalsIgnoreCase("setspawn")){
                if(player.hasPermission("dz.spawn")){
                try {
                    Main.getInstance().spawnConfig.set("spawn",player.getLocation());
                    Main.getInstance().spawnConfig.save(Main.getInstance().spawnFile);
                    Main.getInstance().spawn = player.getLocation();
                    player.getWorld().setSpawnLocation(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());
                    player.sendMessage("§7Spawn AJOUTE avec SUCCES !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }else {
                    MessageConfig.noPerms(player,false);
                }
            }
        }
        return false;
    }
}
