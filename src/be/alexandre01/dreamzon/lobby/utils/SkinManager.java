package be.alexandre01.dreamzon.lobby.utils;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.reflections.ReflectionUtil;
import be.alexandre01.dreamzon.lobby.reflections.UniversalSkinFactory;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.UUID;



public class SkinManager {
    public static void setSkin(Player player , GameProfile profile,boolean isConnected) {
        try {
            UUID uuid;
            if(!Main.getInstance().getPlayersSkinConfig().contains("skins."+player.getName()+".uuid")){


                uuid = fromTrimmed(getUUID(player.getName()));

            System.out.println("UUID  !!!  "+uuid);
            if(uuid == null){
                player.sendMessage("Votre compte n'est pas enregistré sur mojang");
                Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".uuid", "null");
                Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                return;

            }
                Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".uuid", uuid.toString());
                Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
            }else {

               if(Main.getInstance().getPlayersSkinConfig().getString("skins."+player.getName()+".uuid").equalsIgnoreCase("null")){
                   player.sendMessage("Votre compte n'est pas enregistré sur mojang");
                   return;
               }
                uuid = UUID.fromString(Main.getInstance().getPlayersSkinConfig().getString("skins."+player.getName()+".uuid"));
            }

            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                String skin = reply.split("\"value\":\"")[1].split("\"")[0];
                String signature = reply.split("\"signature\":\"")[1].split("\"")[0];
                System.out.println(profile.getProperties().values());
                profile.getProperties().removeAll("textures");
                profile.getProperties().put("textures", new Property("textures", skin, signature));
                if(isConnected){
                    new UniversalSkinFactory().updateSkin(player);
                    Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".texture", skin);
                    Main.getInstance().textures.put(player,skin);
                    Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".signature", signature);
                    Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                }else {
                    EntityPlayer playerNMS = ((CraftPlayer) player).getHandle();
                    for(Player players : Bukkit.getOnlinePlayers()){
                        PlayerConnection connectionspacket = ((CraftPlayer)players).getHandle().playerConnection;
                        connectionspacket.sendPacket(new PacketPlayOutEntityHeadRotation(playerNMS,(byte) ((player.getLocation().getYaw()%360.)*256/360)));
                        connectionspacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS));
                        //BlockPosition blockPos = new BlockPosition(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ());
                        //  connection.sendPacket(new PacketPlayOutSpawnPosition(PacketPlayOutSpawnPosition));
                        //connection.sendPacket(new PacketPlayOutRespawn(playerNMS));
                        // connection.sendPacket(new PacketPlayOutPosition(player.getLocation()));
                        //connectionspacket.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
                        connectionspacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerNMS));
                    }

                    PlayerConnection connectionpacket = ((CraftPlayer)player).getHandle().playerConnection;
                    connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS));
                    BlockPosition blockPos = new BlockPosition(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ());
                    //connectionpacket.sendPacket(new PacketPlayOutSpawnPosition(PacketPlayOutSpawnPosition));
                    //connectionpacket.sendPacket(new PacketPlayOutRespawn(playerNMS));
                    //connectionpacket.sendPacket(new PacketPlayOutPosition(player.getLocation()));
                    connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerNMS));
                    Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".texture", skin);
                    Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".signature", signature);
                    Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".texture", null);
                            Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".signature", null);
                            try {
                                Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.runTaskLater(Main.getInstance(),2*60*20L);
                    player.hidePlayer(player);
                    player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                    //playerNMS.server.getPlayerList().moveToWorld(playerNMS, 0, false, player.getLocation(), true);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            player.sendMessage("ok");
                            playerNMS.server.getPlayerList().moveToWorld(playerNMS, 0, false, player.getLocation(), true);
                            player.showPlayer(player);
                        }
                    }.runTaskLater(Main.getInstance(),0L);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS));
                            //BlockPosition blockPos = new BlockPosition(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ());
                            //  connection.sendPacket(new PacketPlayOutSpawnPosition(PacketPlayOutSpawnPosition));
                            //connection.sendPacket(new PacketPlayOutRespawn(playerNMS));
                            // connection.sendPacket(new PacketPlayOutPosition(player.getLocation()));
                            connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerNMS));
                        }
                    }.runTaskLater(Main.getInstance(),30L);

                    //connection.sendPacket(new PacketPlayOutNamedEntitySpawn(playerNMS));
                }



                //new UniversalSkinFactory().updateSkin(player);
                //playerNMS.server.getPlayerList().moveToWorld(playerNMS, 0, false, player.getLocation(), true);
                // new UniversalSkinFactory().applySkin(player, player.getUniqueId());
                //new UniversalSkinFactory().applySkin(player, player.getUniqueId());




            /*DBObject r = new BasicDBObject("nickname", player.getName());
            DBObject found = Main.getInstance().collection.findOne(r);
            if(found == null){
                player.sendMessage("§c§lVeuillez ressayer plus tard , notre base de donnée semble avoir un probleme , veuillez contacter les administrateurs");
                return false;
            }else {

                BasicDBObject set = new BasicDBObject("COINS", r);
                set.append("COINS", new BasicDBObject("COINS", 1000));
                Main.getInstance().collection.update(found , set);

                return true;
            }*/

//Give skull to player etc.


            } else {
                player.sendMessage("§c§lVeuillez ressayer plus tard , mojang reçois trop de requete de notre part .");
                System.out.println("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void setSkin(String name , Player player , GameProfile profile,boolean isConnected) {

            UUID uuid;



                uuid = fromTrimmed(getUUID(name));


                System.out.println("UUID  !!!  "+uuid);
                if(uuid == null){
                    player.sendMessage("Votre compte n'est pas enregistré sur mojang");
                    Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".uuid", "null");
                    try {
                        Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;

                }
                Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".uuid", uuid.toString());
                try {
                    Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }

        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                String skin = reply.split("\"value\":\"")[1].split("\"")[0];
                String signature = reply.split("\"signature\":\"")[1].split("\"")[0];
                System.out.println(profile.getProperties().values());
                profile.getProperties().removeAll("textures");
                profile.getProperties().put("textures", new Property("textures", skin, signature));
                if(isConnected){
                    new UniversalSkinFactory().updateSkin(player);
                    Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".texture", skin);
                    Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".signature", signature);
                    Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                }else {
                    EntityPlayer playerNMS = ((CraftPlayer) player).getHandle();
                    for(Player players : Bukkit.getOnlinePlayers()){
                        PlayerConnection connectionspacket = ((CraftPlayer)players).getHandle().playerConnection;
                        connectionspacket.sendPacket(new PacketPlayOutEntityHeadRotation(playerNMS,(byte) ((player.getLocation().getYaw()%360.)*256/360)));
                        connectionspacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS));
                        //BlockPosition blockPos = new BlockPosition(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ());
                        //  connection.sendPacket(new PacketPlayOutSpawnPosition(PacketPlayOutSpawnPosition));
                        //connection.sendPacket(new PacketPlayOutRespawn(playerNMS));
                        // connection.sendPacket(new PacketPlayOutPosition(player.getLocation()));
                        //connectionspacket.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
                        connectionspacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerNMS));
                    }

                    PlayerConnection connectionpacket = ((CraftPlayer)player).getHandle().playerConnection;
                    connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS));
                    BlockPosition blockPos = new BlockPosition(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ());
                    //connectionpacket.sendPacket(new PacketPlayOutSpawnPosition(PacketPlayOutSpawnPosition));
                    //connectionpacket.sendPacket(new PacketPlayOutRespawn(playerNMS));
                    //connectionpacket.sendPacket(new PacketPlayOutPosition(player.getLocation()));
                    connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerNMS));
                    Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".texture", skin);
                    Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".signature", signature);
                    Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".texture", null);
                            Main.getInstance().getPlayersSkinConfig().set("skins."+player.getName()+".signature", null);
                            try {
                                Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.runTaskLater(Main.getInstance(),2*60*20L);
                    player.hidePlayer(player);
                    player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                    //playerNMS.server.getPlayerList().moveToWorld(playerNMS, 0, false, player.getLocation(), true);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            player.sendMessage("ok");
                            playerNMS.server.getPlayerList().moveToWorld(playerNMS, 0, false, player.getLocation(), true);
                            player.showPlayer(player);
                        }
                    }.runTaskLater(Main.getInstance(),0L);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS));
                            //BlockPosition blockPos = new BlockPosition(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ());
                            //  connection.sendPacket(new PacketPlayOutSpawnPosition(PacketPlayOutSpawnPosition));
                            //connection.sendPacket(new PacketPlayOutRespawn(playerNMS));
                            // connection.sendPacket(new PacketPlayOutPosition(player.getLocation()));
                            connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerNMS));
                        }
                    }.runTaskLater(Main.getInstance(),30L);

                    //connection.sendPacket(new PacketPlayOutNamedEntitySpawn(playerNMS));
                }



                //new UniversalSkinFactory().updateSkin(player);
                //playerNMS.server.getPlayerList().moveToWorld(playerNMS, 0, false, player.getLocation(), true);
                // new UniversalSkinFactory().applySkin(player, player.getUniqueId());
                //new UniversalSkinFactory().applySkin(player, player.getUniqueId());




            /*DBObject r = new BasicDBObject("nickname", player.getName());
            DBObject found = Main.getInstance().collection.findOne(r);
            if(found == null){
                player.sendMessage("§c§lVeuillez ressayer plus tard , notre base de donnée semble avoir un probleme , veuillez contacter les administrateurs");
                return false;
            }else {

                BasicDBObject set = new BasicDBObject("COINS", r);
                set.append("COINS", new BasicDBObject("COINS", 1000));
                Main.getInstance().collection.update(found , set);

                return true;
            }*/

//Give skull to player etc.


            } else {
                player.sendMessage("§c§lVeuillez ressayer plus tard , mojang reçois trop de requete de notre part .");
                System.out.println("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void setSkin(Player player , GameProfile profile, String skin , String signature,boolean isconnected) {
        if(signature== null || skin==null){
            player.sendMessage("Votre compte n'est pas enregistré sur mojang");
            return;
        }

        System.out.println(profile.getProperties().values());

        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", new Property("textures", skin, signature));

        //new UniversalSkinFactory().updateSkin(player);
        EntityPlayer playerNMS = ((CraftPlayer) player).getHandle();


       //playerNMS.getDataWatcher().watch(10 , (byte)-2);

//VERIF DATAOBJECT
     /*   for (DataWatcher.WatchableObject list :playerNMS.getDataWatcher().b() ){
            System.out.println(list);
            System.out.println(list.a());
            System.out.println(list.b());
            System.out.println(list.c());
            System.out.println(list.d());
        }*/
        if(isconnected){
            new UniversalSkinFactory().updateSkin(player);
        }else {
            PlayerConnection connectionpacket = ((CraftPlayer)player).getHandle().playerConnection;

                    for(Player players : Bukkit.getOnlinePlayers()){
                        PlayerConnection connectionspacket = ((CraftPlayer)players).getHandle().playerConnection;
                        connectionspacket.sendPacket(new PacketPlayOutEntityHeadRotation(playerNMS,(byte) ((player.getLocation().getYaw()%360.)*256/360)));
                        connectionspacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS));
                        //BlockPosition blockPos = new BlockPosition(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ());
                        //  connection.sendPacket(new PacketPlayOutSpawnPosition(PacketPlayOutSpawnPosition));
                        //connection.sendPacket(new PacketPlayOutRespawn(playerNMS));
                        // connection.sendPacket(new PacketPlayOutPosition(player.getLocation()));
                        //connectionspacket.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
                        connectionspacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerNMS));
                    }

                    connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS));
                    //BlockPosition blockPos = new BlockPosition(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ());
                    //  connection.sendPacket(new PacketPlayOutSpawnPosition(PacketPlayOutSpawnPosition));
                    //connection.sendPacket(new PacketPlayOutRespawn(playerNMS));
                    // connection.sendPacket(new PacketPlayOutPosition(player.getLocation()));
                    //connectionpacket.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
                    connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerNMS));

                    final Location loc = player.getLocation().clone();
                    final Location otherWorldLocation = new Location(player.getWorld(), 0, 0, 0);
                        player.teleport(otherWorldLocation);
                 new BukkitRunnable() {
                      @Override
                      public void run() {
                             player.teleport(loc);
                             //playerNMS.playerConnection.sendPacket(new PacketPlayOutRespawn(playerNMS.dimension, playerNMS.getWorld().getDifficulty(), playerNMS.getWorld().getWorldData().getType(), playerNMS.playerInteractManager.getGameMode()));
                             player.updateInventory();
                }
            }.runTaskLater(Main.getInstance(),10L);







            player.hidePlayer(player);

           // player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            new BukkitRunnable(){
                @Override
                public void run() {
                    player.sendMessage("ok");
                    playerNMS.server.getPlayerList().moveToWorld(playerNMS, 0, false, player.getLocation(), true);
                    player.showPlayer(player);
                }
            }.runTaskLater(Main.getInstance(),2L);


          /*new BukkitRunnable() {
                @Override
                public void run() {
                    profile.getProperties().removeAll("textures");
                    profile.getProperties().put("textures", new Property("textures", skin, signature));
                    connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS));
                    //BlockPosition blockPos = new BlockPosition(player.getLocation().getBlock().getX(), player.getLocation().getBlock().getY(), player.getLocation().getBlock().getZ());
                    //  connection.sendPacket(new PacketPlayOutSpawnPosition(PacketPlayOutSpawnPosition));
                    //connection.sendPacket(new PacketPlayOutRespawn(playerNMS));
                    // connection.sendPacket(new PacketPlayOutPosition(player.getLocation()));
                    connectionpacket.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
                    connectionpacket.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerNMS));
                }
            }.runTaskLater(Main.getInstance(),50L);*/

        }


        //new UniversalSkinFactory().updateSkin(player);
        //playerNMS.server.getPlayerList().moveToWorld(playerNMS, 0, false, player.getLocation(), true);
        // new UniversalSkinFactory().applySkin(player, player.getUniqueId());
        //new UniversalSkinFactory().applySkin(player, player.getUniqueId());
        /*new BukkitRunnable() {
            boolean righthand=false;
            @Override
            public void run() {
                if(righthand){
                    playerNMS.getDataWatcher().watch(10 , (byte)-1);
                    new UniversalSkinFactory().updateSkin(player);
                    righthand = false;
                }else {
                    playerNMS.getDataWatcher().watch(10 , (byte)-2);
                    new UniversalSkinFactory().updateSkin(player);
                    righthand= true;
                }

            }
        }.runTaskTimer(Main.getInstance(), 10,10);*/




        //connection.sendPacket(new PacketPlayOutNamedEntitySpawn(playerNMS));
            /*DBObject r = new BasicDBObject("nickname", player.getName());
            DBObject found = Main.getInstance().collection.findOne(r);
            if(found == null){
                player.sendMessage("§c§lVeuillez ressayer plus tard , notre base de donnée semble avoir un probleme , veuillez contacter les administrateurs");
                return false;
            }else {

                BasicDBObject set = new BasicDBObject("COINS", r);
                set.append("COINS", new BasicDBObject("COINS", 1000));
                Main.getInstance().collection.update(found , set);

                return true;
            }*/

//Give skull to player etc.



    }
    public static PropertyMap getProp(GameProfile profile, UUID uuid) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                String skin = reply.split("\"value\":\"")[1].split("\"")[0];
                String signature = reply.split("\"signature\":\"")[1].split("\"")[0];
                profile.getProperties().removeAll("textures");
                profile.getProperties().put("textures", new Property("textures", skin, signature));

                return profile.getProperties();
            } else {
                System.out.println("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static UUID fromTrimmed(String trimmedUUID) throws IllegalArgumentException{
        if(trimmedUUID == null){
            return null;
        }

        StringBuilder builder = new StringBuilder(trimmedUUID.trim());
        System.out.println(builder);
        System.out.println(trimmedUUID);
        /* Backwards adding to avoid index adjustments */
        try {
            builder.insert(20, "-");
            builder.insert(16, "-");
            builder.insert(12, "-");
            builder.insert(8, "-");
        } catch (StringIndexOutOfBoundsException e){
            throw new IllegalArgumentException();
        }
        return UUID.fromString(builder.toString());
    }
    public static String getUUID(String playerName) {
        try {
            String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
            String UUIDJson = IOUtils.toString(new URL(url));
            System.out.println(UUIDJson);
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);

            String uuid = UUIDObject.get("id").toString();
            System.out.println(uuid);
            return uuid;
        } catch (Exception e) {
            return null;
        }
    }


}

