package be.alexandre01.dreamzon.lobby.utils;

import be.alexandre01.dreamzon.lobby.Main;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import sun.misc.BASE64Encoder;

import java.util.UUID;

public class NPCManager {

    public void createNpcCustomSkinSpectate(Location loc , Player player, String npcName){
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
        System.out.println("HERE WE GO AGAIN");
        //Property property =  (Property)   SkinsRestorer.getInstance().getSkinsRestorerBukkitAPI().getSkinData("DogPumpkin");


        //GameProfile gameProfile = new GameProfile(UUID.fromString(uuid), "skin174161795");

        //changeSkinWithSkinCustom2(gameProfile/*,texture,signature*/);
        // Skin #174161795 generated on Jul 31, 2019 9:40:41 AM via MineSkin.org


        UUID uuid = (UUID) Main.getInstance().getPlayersSkinConfig().get("skins."+player.getName()+".uuid");

        System.out.println(uuid);
        GameProfile skin174161795 = null;
        if(uuid != null){
            skin174161795 = new GameProfile(uuid, npcName);
        }else {
            return;
            //Crash
            /*uuid= UUID.fromString("8667ba71-b85a-4004-af54-457a9734eed7");
            skin174161795 = new GameProfile(uuid, npcName);*/

        }
       if(skin174161795.equals(null)){
           return;
       }
        if(uuid != null){
            System.out.println("cc");
            String texture = Main.getInstance().getPlayersSkinConfig().getString("skins."+player.getName()+".texture");
            System.out.println(texture);
            String signatures = Main.getInstance().getPlayersSkinConfig().getString("skins."+player.getName()+".signature");
            System.out.println(signatures);
            skin174161795.getProperties().put("textures", new Property("textures", texture,signatures));
        }else {
            System.out.println("cc");
            EntityPlayer playerNMS = ((CraftPlayer) player).getHandle();
            GameProfile profile = playerNMS.getProfile();
            //System.out.println(profile.getProperties().get("textures"));
           //skin174161795.getProperties().put("textures", (Property) profile.getProperties().get("textures"));

        }

        EntityPlayer npc = new EntityPlayer(nmsServer , nmsWorld , skin174161795 , new PlayerInteractManager(nmsWorld));
        npc.getDataWatcher().watch(10, (byte) 0xFF);
        //npc.getDataWatcher().set(new DataWatcherObject<>(12, DataWatcherRegistry.a), (byte) 0xFF);
        Player npcPlayer = npc.getBukkitEntity().getPlayer();
        npcPlayer.setPlayerListName("");
        npc.setLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getYaw(),loc.getPitch());



        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        PacketPlayOutPlayerInfo packet3 = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc);

        // connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,npc));

        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        // connection.sendPacket(new PacketPlayOutEntityStatus(npc, (byte)1));

        connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte) ((loc.getYaw()%360.)*256/360), (byte) ((loc.getPitch()%360.)*256/360), false));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) ((loc.getYaw()%360.)*256/360)));
        connection.sendPacket(packet3);
        connection.sendPacket(new PacketPlayOutCamera(npc));
        npcPlayer.setGameMode(GameMode.SPECTATOR);

        new BukkitRunnable() {
            @Override
            public void run() {
                connection.sendPacket( new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                connection.sendPacket(new PacketPlayOutCamera(((CraftPlayer)player).getHandle()));
                connection.sendPacket(new PacketPlayOutEntityDestroy(npcPlayer.getEntityId()));
                player.sendMessage("c'est bon");
            }
        }.runTaskLater(Main.getInstance(),10*20L);
    }
    public void createNpcCustomSkin(Location loc , Player player, String npcName, String texture , String signature, String uuid){
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();

        //Property property =  (Property)   SkinsRestorer.getInstance().getSkinsRestorerBukkitAPI().getSkinData("DogPumpkin");


        //GameProfile gameProfile = new GameProfile(UUID.fromString(uuid), "skin174161795");

        //changeSkinWithSkinCustom2(gameProfile/*,texture,signature*/);
        // Skin #174161795 generated on Jul 31, 2019 9:40:41 AM via MineSkin.org
        GameProfile skin174161795 = new GameProfile(UUID.fromString(uuid), npcName);
        skin174161795.getProperties().put("textures", new Property("textures", texture,signature));
        EntityPlayer npc = new EntityPlayer(nmsServer , nmsWorld , skin174161795 , new PlayerInteractManager(nmsWorld));
        //npc.getDataWatcher().set(new DataWatcherObject<>(12, DataWatcherRegistry.a), (byte) 0xFF);
        Player npcPlayer = npc.getBukkitEntity().getPlayer();
        npcPlayer.setPlayerListName("");

        npc.setLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getYaw(),loc.getPitch());

        npc.getDataWatcher().watch(10, (byte) 0xFF);
       // Main.getInstance().villagerold.put(player, npc);




        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        PacketPlayOutPlayerInfo packet3 = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc);
        connection.sendPacket(new PacketPlayOutEntityStatus(npc, (byte)3));
        Location blockloc = new Location(player.getWorld(),1,1,1);
        BlockPosition blockPosition = new BlockPosition(blockloc.getBlockX(),blockloc.getBlockY(),blockloc.getBlockZ());

        PacketPlayOutBed pac = new PacketPlayOutBed(npc, blockPosition);
        // connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,npc));

        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        // connection.sendPacket(new PacketPlayOutEntityStatus(npc, (byte)1));
        for(Player players : Bukkit.getOnlinePlayers()){
            players.sendBlockChange(blockloc, Material.BED_BLOCK,(byte)0);
        }

        connection.sendPacket(pac);

        connection.sendPacket(new PacketPlayOutEntityTeleport(npcPlayer.getEntityId(),getFixLocation(loc.getX()),getFixLocation(loc.getZ()),getFixLocation(loc.getY()+0.3),getFixRotation(loc.getYaw()),getFixRotation(loc.getPitch()),true));
        connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte) ((loc.getYaw()%360.)*256/360), (byte) ((loc.getPitch()%360.)*256/360), false));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) ((loc.getYaw()%360.)*256/360)));
        //connection.sendPacket(packet3);
        //npc.teleportTo(loc,true);



        }
    public void followPlayer(Player player, LivingEntity entity, double d) {
        final LivingEntity e = entity;
        final Player p = player;
        final float f = (float) d;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                ((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), f);
            }
        }, 0 * 20, 2 * 20);
    }
    public int getFixLocation(double pos){
        return (int)MathHelper.floor(pos * 32.0D);
    }
    public byte getFixRotation(float yawpitch){
        return (byte) ((int) (yawpitch * 256.0F / 360.0F));
    }
    }
