package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.hub.HubInventory;
import be.alexandre01.dreamzon.lobby.packet.PacketReader;
import be.alexandre01.dreamzon.lobby.packet.Title;
import be.alexandre01.dreamzon.lobby.utils.ArmorStandNMS;
import be.alexandre01.dreamzon.lobby.utils.NPC;
import be.alexandre01.dreamzon.lobby.utils.SkinManager;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
            Location location = event.getPlayer().getLocation();
            initialiseplayer(event.getPlayer(),location);
            Player player =event.getPlayer();

        player.updateInventory();
        if(isinitialised(event.getPlayer())){

            event.setJoinMessage("§7[§e§lLobby§7] "+ event.getPlayer().getName()+" a rejoint le Lobby §a[+]");
        }else {
            event.setJoinMessage("");
        }



    }

    //Chargement DB
    public void initialiseplayer(Player player, Location location){

        PacketReader pr = new PacketReader(player);
        pr.inject();

        for(Player players : Bukkit.getOnlinePlayers()){
            if(!players.equals(player)){
                players.hidePlayer(player);

            }


        }
        EntityPlayer playerNMS = ((CraftPlayer) player).getHandle();
        GameProfile profile = playerNMS.getProfile();


            new BukkitRunnable(){

                int times = 1;
                @Override
                public void run() {
                    if(times==1){

                    if(Main.getInstance().getPlayersSkinConfig().contains("skins."+player.getName()+".texture")&&Main.getInstance().getPlayersSkinConfig().contains("skins."+player.getName()+".signature")){
                        String texture = Main.getInstance().getPlayersSkinConfig().getString("skins."+player.getName()+".texture");
                        Main.getInstance().textures.put(player,texture);
                        String signatures = Main.getInstance().getPlayersSkinConfig().getString("skins."+player.getName()+".signature");
                        SkinManager.setSkin(player,profile,texture,signatures,false);
                        HubInventory.HubInventory(player.getInventory(),player);
                        player.sendMessage("INITIALISED BY DREAMZON");
                        player.sendMessage("Votre skin à bien été chargé et enregistré par le systeme de dreamzon ");
                        //new UniversalSkinFactory().applySkin(player,new Property("textures", texture, signatures));
                        System.out.println(player.getName());
                        for(Player players : Bukkit.getOnlinePlayers()){
                            if(!players.equals(player)){
                                players.showPlayer(player);

                            }
                            
                        }


                        player.teleport(Main.getInstance().spawn);
                       //new NPCManager().createNpcCustomSkinSpectate(player.getLocation(),player,"§2"+player.getName());
                    }else {

                       SkinManager.setSkin(player,profile,false);
                        HubInventory.HubInventory(player.getInventory(),player);
                        player.sendMessage("old");
                        player.sendMessage("INITIALISED");
                        player.sendMessage("Votre skin à bien été chargé et enregistré par le systeme de dreamzon ");
                        System.out.println(player.getName());
                        player.sendMessage("Renvoie 2");
                        //new NPCManager().createNpcCustomSkinSpectate(player.getLocation(),player,"§2"+player.getName());
                        for(Player players : Bukkit.getOnlinePlayers()){
                            if(!players.equals(player)){
                                players.showPlayer(player);

                            }

                        }

                        player.teleport(Main.getInstance().spawn);

                    }
                    }
                    if(times==4){
                        /*
                        if(Main.getInstance().getPlayersSkinConfig().contains("skins."+player.getName())){
                            player.sendMessage("old");
                            String texture = Main.getInstance().getPlayersSkinConfig().getString("skins."+player.getName()+".texture");
                            String signatures = Main.getInstance().getPlayersSkinConfig().getString("skins."+player.getName()+".signature");
                             SkinManager.setSkin(player,profile,texture,signatures,false);
                        }else {
                            player.sendMessage("new");
                              SkinManager.setSkin(player,profile,false);
                        }
                    */
                        new Title().sendTitle(player,20,120,20,"§9§lDream§f§lZon","§7Bienvenue §7§l"+ player.getName()+ "§7 !");
                        new BukkitRunnable() {
                            int seconds = 0;
                            @Override
                            public void run() {
                                if(seconds == 30){
                                    cancel();
                                }
                                new Title().sendActionBar(player,"§7Serveur de Build du Lobby");
                                seconds++;
                            }
                        }.runTaskTimer(Main.getInstance(),0,20L);

                        new Title().sendTabTitle(player,"§9§lDream§f§lZon","§7> "+Bukkit.getOnlinePlayers().size()+" joueurs connectés sur le serveur ");
                        Main.getInstance().getScoreboardManager().onLogin(player);
                        if(Main.getInstance().spawn == null){


                            player.sendMessage("§c§lDésolé , le spawn n'est pas trouvé dans les données du serveur");
                        }else {


                            player.teleport(Main.getInstance().spawn);
                        }
                        new PlayerJoinLocation().isInZones(player);

                        cancel();
                    }

                    times++;
                }

            }.runTaskTimer(Main.getInstance(),0,6L);

        }



    public boolean isinitialised(Player player){
        return true;
    }

}
