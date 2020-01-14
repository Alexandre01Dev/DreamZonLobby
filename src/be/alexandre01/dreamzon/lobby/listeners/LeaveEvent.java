package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Main.getInstance().getScoreboardManager().onLogout(player);
    }
}
