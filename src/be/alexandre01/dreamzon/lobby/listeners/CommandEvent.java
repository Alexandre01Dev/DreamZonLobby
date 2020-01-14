package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.utils.MessageConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        if(!event.getPlayer().hasPermission("dz.cmd")){


            for(String cmd : Main.getInstance().commands){

                if(event.getMessage().toLowerCase().contains(cmd)){
                    MessageConfig.noPerms(event.getPlayer(),false);
                    event.setCancelled(true);
                    break;
                }
            }
        }


    }
}
