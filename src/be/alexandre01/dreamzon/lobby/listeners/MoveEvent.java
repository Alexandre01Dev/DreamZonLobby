package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.data.SkinsDataBase;
import be.alexandre01.dreamzon.lobby.utils.*;
import be.alexandre01.dreamzon.lobby.utils.NPC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class MoveEvent implements Listener {
    @EventHandler
    public void onMoveOnVillagerZone(PlayerMoveEvent event){
        Player player = event.getPlayer();

        //Villager Zone
        Location loc1 = new Location(player.getWorld(), 216 , 76 , 1605 );
        Location loc2 = new Location(player.getWorld(),  209 ,80 ,1598 );
        NPC npc = null;
        Cuboid cuboid = new Cuboid(loc1, loc2);
        //RENTRE
        if (!cuboid.contains(event.getFrom())){
            if(cuboid.contains(event.getTo())){
                RecordUtil.playRecord(event.getPlayer(), SkinsDataBase.VillagerLocation(),Material.RECORD_5);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,999999,1,false,false));
                player.sendMessage("DEBUG : TU RENTRE DANS LA ZONE DU VILLAGEOIS");
                //new NPCManager().createNpcCustomSkin(SkinsDataBase.VillagerLocation(),player,"Vieux villageois",SkinsDataBase.VillagerText(),SkinsDataBase.VillagerSignature(),SkinsDataBase.VillagerUUID());
                npc = new NPC(player , "Vieux villageois", SkinsDataBase.VillagerLocation());
                Main.getInstance().villagerold.put(player, npc);
                npc.changeSkin(SkinsDataBase.VillagerText(),SkinsDataBase.VillagerSignature());
                npc.spawn();
                npc.sleep(true);

            }
        }
        //SORS
        if (cuboid.contains(event.getFrom())){
            if(!cuboid.contains(event.getTo())){

                getNpc(player,Main.getInstance().villagerold).sleep(false);
                    getNpc(player,Main.getInstance().villagerold).destroy();

                /*PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
                PacketPlayOutPlayerInfo packetrem = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, Main.getInstance().villagerold.get(player));
                PacketPlayOutEntityDestroy packetdes = new PacketPlayOutEntityDestroy(Main.getInstance().villagerold.get(player).getBukkitEntity().getEntityId());
                connection.sendPacket(packetrem);
                connection.sendPacket(packetdes);*/
                for(Block blocks : cuboid.getBlocksBukkit()){
                    RecordUtil.stopRecord(event.getPlayer(),blocks.getLocation());
                }
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                RecordUtil.stopRecord(event.getPlayer(),SkinsDataBase.VillagerLocation());
              //  player.sendMessage("DEBUG : TU SORS DANS LA ZONE DU VILLAGEOIS");

            }
        }


    }
    @EventHandler
    public void onMoveOnScary(PlayerMoveEvent event){
        Player player =event.getPlayer();
        Location loc1 = new Location(event.getPlayer().getWorld(),525 ,96 ,1468 );
        Location loc2 = new Location(event.getPlayer().getWorld(),524 ,99, 1469 );
        Cuboid cuboid = new Cuboid(loc1,loc2);
        Location loc3 = new Location(player.getWorld(),521 , 96, 1469 );
        Location loc4 = new Location(player.getWorld(),529 ,100 ,1475 );
        Cuboid cuboid2 = new Cuboid(loc3,loc4);
        if(cuboid.contains(event.getPlayer().getLocation())){
            if(player.getLocation().getYaw() >= -180 &&player.getLocation().getYaw() <= 150 || player.getLocation().getYaw() <= 150 &&player.getLocation().getYaw() <= 180){
                int heads = 0;
            for (Block block : cuboid2.getBlocksBukkit()){
                if(block.getType().equals(Material.SKULL)){
                    player.sendBlockChange(block.getLocation(),Material.JUKEBOX,(byte)0);
                    heads++;
                }
            }
            if(heads==0){
                player.sendMessage("Il n'y a pas de tête ici");
            }else {
                player.sendMessage("Il y a "+heads+" tête ici !");
            }
        }else {
                for (Block block : cuboid2.getBlocksBukkit()){
                    if(block.getType().equals(Material.SKULL)){
                        WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
                        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(nmsWorld, new BlockPosition(block.getX(), block.getY(), block.getZ()));
                        IBlockData ibd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(block.getTypeId() + (block.getData()<< 12));
                        TileEntitySkull skullTile = (TileEntitySkull)((CraftWorld)block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));

                        packet.block= ibd;
                        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                    }
                }
            }
    }
    }
    @EventHandler
    public void onQuest1(PlayerMoveEvent event){
        Player player = event.getPlayer();

        //Villager Zone
        Location loc1 = new Location(player.getWorld(), 218, 74 ,1570  );
        Location loc2 = new Location(player.getWorld(),  275 ,140 ,1616);
        NPC npc = null;
        Cuboid cuboid = new Cuboid(loc1, loc2);
        //RENTRE
        if (!cuboid.contains(event.getFrom())){
            if(cuboid.contains(event.getTo())){
                //player.sendMessage("DEBUG : TU RENTRE DANS LA ZONE DE LA PREMIERE QUEST");
                //new NPCManager().createNpcCustomSkin(SkinsDataBase.VillagerLocation(),player,"Vieux villageois",SkinsDataBase.VillagerText(),SkinsDataBase.VillagerSignature(),SkinsDataBase.VillagerUUID());
                npc = new NPC(player , "Villageoise", SkinsDataBase.getQuest1Loc());
                Main.getInstance().quest1.put(player, npc);
                npc.changeSkin(SkinsDataBase.getQuest1Text(),SkinsDataBase.getQuest1Sign());
                npc.spawn();
                npc.setCrouch();
                NPC npc2 = new NPC(player,"BlablaCar",player.getLocation());
            }
        }
        if(cuboid.contains(event.getPlayer().getLocation())){
        if(player.getLocation().distance(SkinsDataBase.getQuest1Loc())< 10){
            getNpc(player,Main.getInstance().quest1).rotateToPlayer(player,SkinsDataBase.getQuest1Loc());
        }
        }
        //SORS
        if (cuboid.contains(event.getFrom())){
            if(!cuboid.contains(event.getTo())){


                getNpc(player,Main.getInstance().quest1).destroy();

                /*PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
                PacketPlayOutPlayerInfo packetrem = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, Main.getInstance().villagerold.get(player));
                PacketPlayOutEntityDestroy packetdes = new PacketPlayOutEntityDestroy(Main.getInstance().villagerold.get(player).getBukkitEntity().getEntityId());
                connection.sendPacket(packetrem);
                connection.sendPacket(packetdes);*/

                //player.sendMessage(event.getFrom().toString());
               // player.sendMessage(event.getTo().toString());
                //player.sendMessage("DEBUG : TU SORS DANS LA ZONE DE LA PREMIERE QUEST");

            }
        }
    }
    public NPC getNpc(Player player , HashMap<Player,NPC> hashMap){
        return hashMap.get(player);
    }

}
