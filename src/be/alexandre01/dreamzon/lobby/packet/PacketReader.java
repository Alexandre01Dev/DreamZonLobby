package be.alexandre01.dreamzon.lobby.packet;



import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.utils.SkinManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.util.List;

public class PacketReader {

    Player player;
    Channel channel;

    public PacketReader(Player player) {
        this.player = player;
    }

    public void inject(){
        CraftPlayer cPlayer = (CraftPlayer)this.player;
        channel = cPlayer.getHandle().playerConnection.networkManager.channel;
        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {@Override protected void decode(ChannelHandlerContext arg0, Packet<?> packet,List<Object> arg2) throws Exception {arg2.add(packet);readPacket(packet);}});
    }

    public void uninject(){
        if(channel.pipeline().get("PacketInjector") != null){
            channel.pipeline().remove("PacketInjector");
        }
    }


    public void readPacket(Packet<?> packet){

        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUpdateSign")){
            BlockPosition bp = (BlockPosition) getValue(packet,"a");
            if(bp.getX() == 250 && bp.getZ() == 1600){
                IChatBaseComponent[] i = (IChatBaseComponent[]) getValue(packet,"b");
                if(i[1].getText().equalsIgnoreCase("Entre un pseudo")){
                    if(i[0].getText().contains(">")){
                        String nick = i[0].getText().split(">")[1].replaceAll(" ","");
                        player.sendMessage(nick);
                       SkinManager.setSkin(nick,player,((CraftPlayer)player).getProfile(),false);
                    }

                }
            }
        }

        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){
            int id = (Integer) getValue(packet,"a");
            System.out.println( (Integer) getValue(packet,"a"));
            if(player.getEntityId()==id){
                setValue(packet,"a",999);
                player.sendMessage("Hey");
            }
        }
        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){

           int id = (Integer)getValue(packet, "a");


           //Vieux Villagois
            if(Main.getInstance().villagerold.containsKey(player)){
                if(id == Main.getInstance().villagerold.get(player).getEntityID()){
                    if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")){
                        player.sendMessage("[Vieux villageois] Zzzzzzz");
                    }else if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")){
                        //player.sendMessage("[Vieux villageois] hmmm ... Arrete de me frapper vieux schnoque ");
                        // player.sendMessage("[Vieux villageois] [...] Zzzzz U-U");
                        Main.getInstance().villagerold.get(player).animation(1);
                        player.playSound(player.getLocation(), Sound.VILLAGER_HIT,1.0f,0.7f);
                        /*new BukkitRunnable() {
                            @Override
                            public void run() {

                            }
                        }.runTaskLater(Main.getInstance(), 7*20L);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.sendMessage("[Toi] Il a l'air pas de se reveiller ce pd qui n'arrive pas à s'endormir malgré mon code à 2h du mat , §lMAUDIT SOIS TU !!! ");
                            }
                        }.runTaskLater(Main.getInstance(),8*20L);*/
                    }
            }

            }


            }

        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInBlockDig")){

            System.out.println(getValue(packet, "a").toString());
            System.out.println(getValue(packet, "b").toString());
            System.out.println(getValue(packet, "c").toString());
        }
        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInChat")){

            System.out.println(getValue(packet, "a").toString());
        }
        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInBlockPlace")){
            System.out.println(getValue(packet, "b").toString());
            System.out.println("HEY");
            System.out.println(getValue(packet, "c").toString());
            System.out.println(getValue(packet, "f").toString());
            //System.out.println(Main.getInstance().protect.get(getValue(packet, "b")).name());
            System.out.println("HEY");
                BlockPosition bp = ((BlockPosition) getValue(packet, "b"));
                Location location = new Location(player.getWorld(),bp.getX(),bp.getY(),bp.getZ());
                System.out.println(location);
                //System.out.println(Main.getInstance().protect.get((BlockPosition) getValue(packet, "b")));
            System.out.println("HEY");
                System.out.println("LISTE : "+Main.getInstance().protect);
                if(!Main.getInstance().protect.isEmpty()){
                    System.out.println("PAS VIDE");
                if(Main.getInstance().protect.containsKey(player)){
                    System.out.println("CONTIENT JOUEUR");
                    if(Main.getInstance().protect.get(player).containsKey((BlockPosition) getValue(packet, "b"))){
                        System.out.println("CONTIENT BLOCKS");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.sendBlockChange(location,  Main.getInstance().protect.get(player).get((BlockPosition) getValue(packet, "b")),(byte)0);

                            }
                        }.runTaskLater(Main.getInstance(),1L);
                        player.sendMessage("Tu cliques droit sur un block de "+Main.getInstance().protect.get(player).get((BlockPosition) getValue(packet, "b")));
                        player.sendBlockChange(location,  Main.getInstance().protect.get(player).get((BlockPosition) getValue(packet, "b")),(byte)0);
                    }
                }


        }
        }
        }



    public void setValue(Object obj,String name,Object value){
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        }catch(Exception e){}
    }

    public Object getValue(Object obj,String name){
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        }catch(Exception e){}
        return null;
    }

}