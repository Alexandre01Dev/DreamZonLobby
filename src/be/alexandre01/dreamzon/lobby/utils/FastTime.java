package be.alexandre01.dreamzon.lobby.utils;

import be.alexandre01.dreamzon.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FastTime {
    public static void setPlayer(Player player){
        Main.getInstance().fastTime.add(player);
        new BukkitRunnable() {

            int time = 0;
            int maxtime = 24000;
            @Override
            public void run() {
            time = time + 500;
            if(time == maxtime){
                time = 0;
            }

                    player.setPlayerTime(time,true);
                if(!Main.getInstance().fastTime.contains(player)){
                    player.resetPlayerTime();
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(),0,1L);
    }
    public static void removePlayer(Player player){
        if(Main.getInstance().fastTime.contains(player)){
         Main.getInstance().fastTime.remove(player);
        }
        }

}
