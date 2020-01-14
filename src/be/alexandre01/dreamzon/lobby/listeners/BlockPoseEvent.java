package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.data.SkinsDataBase;
import be.alexandre01.dreamzon.lobby.utils.*;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockPoseEvent implements Listener {
    @EventHandler
    public void onBlockPose(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(event.getItemInHand()!=null){
            if(event.getItemInHand().getItemMeta().getDisplayName().equals("§eReveilleur")){
                event.setCancelled(true);
                FakeBlock.sendBlock(event.getPlayer(),event.getBlock().getLocation(),Material.JUKEBOX,(byte)0);
                RecordUtil.playRecord(player,event.getBlock().getLocation(),Material.RECORD_8);

                Location loc1 = new Location(player.getWorld(), 216 , 76 , 1605 );
                Location loc2 = new Location(player.getWorld(),  209 ,80 ,1598 );

                Cuboid cuboid = new Cuboid(loc1, loc2);
                if(cuboid.contains(player.getLocation())){
                    Location newloc = SkinsDataBase.VillagerLocation().add(0,-1,-4);
                    NPC oldnpc = Main.getInstance().villagerold.get(player);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ArmorStandNMS armorStandNMS = new ArmorStandNMS(player,"•",SkinsDataBase.VillagerLocation().add(0,0,0));
                            EntityArmorStand as = armorStandNMS.spawn();
                            armorStandNMS.setInvisible();
                            armorStandNMS.setNameVisible(as);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    armorStandNMS.destroy();
                                    ArmorStandNMS armorStandNMS = new ArmorStandNMS(player,"• •",SkinsDataBase.VillagerLocation().add(0,0,0));
                                    EntityArmorStand as = armorStandNMS.spawn();
                                    armorStandNMS.setInvisible();
                                    armorStandNMS.setNameVisible(as);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            armorStandNMS.destroy();
                                            ArmorStandNMS armorStandNMS = new ArmorStandNMS(player,"• • •",SkinsDataBase.VillagerLocation().add(0,0,0));
                                            EntityArmorStand as = armorStandNMS.spawn();
                                            armorStandNMS.setInvisible();
                                            armorStandNMS.setNameVisible(as);
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    armorStandNMS.destroy();
                                                    oldnpc.destroy();
                                                }
                                            } .runTaskLater(Main.getInstance(),20);
                                        }
                                    } .runTaskLater(Main.getInstance(),20);
                                }
                            } .runTaskLater(Main.getInstance(),20);
                        }
                    }.runTaskLater(Main.getInstance(),20*2);

                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    RecordUtil.stopRecord(event.getPlayer(),SkinsDataBase.VillagerLocation());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(SkinsDataBase.VillagerLocation(), Sound.ZOMBIE_WOODBREAK,1,0.5f);
                            NPC npc = new NPC(player,"Villageois faché",newloc);
                            //npc.changeSkin(SkinsDataBase.VillagerText(),SkinsDataBase.VillagerSignature());
                            npc.changeSkin(SkinsDataBase.AngryVillagerText(),SkinsDataBase.AngryVillagerSignature());
                            npc.spawn();

                            Main.getInstance().villagerold.put(player, npc);
                            npc.animation(0);
                            npc.teleport(SkinsDataBase.VillagerLocation().add(0,-1,-4));
                            npc.headRotation(-90f,2.8f);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    npc.animation(0);
                                    npc.rotateToPlayer(player,newloc);
                                    //npc.teleport(newloc.setDirection(player.getLocation().subtract(newloc).toVector()));
                                    if(!cuboid.contains(player.getLocation())){
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(Main.getInstance(),0,1L);
                            player.sendMessage("§l A ! Sa va pas de réveiller les personnes pendant la nuit ?! Quoi ? Il fait jour ?");

                        }
                    }.runTaskLater(Main.getInstance(),5*20);
                }
            }
        }
    }
}
