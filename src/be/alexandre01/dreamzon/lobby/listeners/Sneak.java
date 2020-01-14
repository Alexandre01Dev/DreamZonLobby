package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.utils.NPC;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Sneak implements Listener {
   // @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
      //  ArmorStandNMS armorStandNMS = new ArmorStandNMS(event.getPlayer(),"Test", event.getPlayer().getLocation());
        if(event.isSneaking()){
            NPC npc = new NPC(event.getPlayer(),"Hello",event.getPlayer().getLocation());
            EntityPlayer entityPlayer = npc.spawn();
            HashMap<NPC, EntityPlayer> entityPlayerHashMap = new HashMap<>();
            entityPlayerHashMap.put(npc, entityPlayer);
            Main.getInstance().test.put(event.getPlayer(), entityPlayerHashMap);
            //npc.sit( Main.getInstance().test);
            new BukkitRunnable() {
                int choice = 0;
                @Override
                public void run() {
                    if(choice ==0){
                        npc.setCrouch();
                        choice++;
                    }else {
                        if(choice==1){
                            npc.setStanding();
                            choice++;
                        }else {
                            if(choice==2){
                                npc.setInvisible();
                                choice++;
                            }else {
                                if(choice==3){
                                    npc.setOnFire();
                                    choice =0;
                                }
                            }
                        }
                    }
                }
            }.runTaskTimer(Main.getInstance(),0,5*20);

        }


    /*    armorStandNMS.spawn();
        armorStandNMS.setup(true,false,true);
        armorStandNMS.setPoseToAnotherPose(ArmorStand.RIGHT_ARM,0,100,0,0,140,0,3f,false);
        new BukkitRunnable() {
            @Override
            public void run() {
                armorStandNMS.setPoseToAnotherPose(ArmorStand.RIGHT_ARM,0,140,0,90,150,0,5,false);
            }
        }.runTaskLater(Main.getInstance(),13);*/


    }
}
