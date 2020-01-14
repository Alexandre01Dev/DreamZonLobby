package be.alexandre01.dreamzon.lobby.utils;

import be.alexandre01.dreamzon.lobby.Main;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class FakeBlock {
    public static void sendBlock(Player player, Location location, Material mat , Byte b){
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendBlockChange(location,mat,b);
                BlockPosition bp = new BlockPosition(location.getBlockX(),location.getBlockY(),location.getBlockZ());

                HashMap<BlockPosition,Material> hashMap = new HashMap<BlockPosition,Material>();
                hashMap.put(bp, mat);
                Main.getInstance().protect.put(player,hashMap);
                System.out.println(Main.getInstance().protect.get(player));
            }
        }.runTaskLater(Main.getInstance(),5L);
    }
}
