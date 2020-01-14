package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.utils.ArmorStandNMS;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatEvent implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
    if(event.getMessage().toLowerCase().contains("HubZon".toLowerCase())){
        int index = event.getMessage().toLowerCase().indexOf("hubzon");
        System.out.println(event.getMessage().substring(0,index));
        System.out.println(event.getMessage().substring(index+6));

        event.setMessage(event.getMessage().substring(0,index)+"DreamZon"+event.getMessage().substring(index+6));
    }
    event.setCancelled(true);

    for(Player players : Bukkit.getOnlinePlayers()){
        int totalchar = 0;
        TextComponent message = new TextComponent( "" );
        if(event.getPlayer().hasPermission("support.shootcraft")){
            TextComponent support = new TextComponent(TextComponent.fromLegacyText("§e§l⭐"));
            support.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§6§l"+event.getPlayer().getName() +"\nà supporté le serveur" ).create()));
            message.addExtra(support);
            message.addExtra(" ");
            totalchar = totalchar + 1;
        }
        TextComponent name = new TextComponent(TextComponent.fromLegacyText(event.getPlayer().getName()));
        totalchar = totalchar + name.getText().length();
        name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§7Cliquez ici pour envoyer un message à ce ce joueur"+"§6§l:\n\n§6§l "+event.getPlayer().getPlayerTime()+"\n\n§6§lKill: "+ event.getPlayer().getStatistic(Statistic.PLAYER_KILLS)).create()));
        message.addExtra(name);
        String doublearrow = " §7» ";
        message.addExtra(doublearrow);
        totalchar = totalchar + doublearrow.length();

        //totalchar = totalchar + 4;
        totalchar = message.getText().length();

            if(event.getMessage().toLowerCase().contains("pute")){

                String wordToFind = "pute";
                Pattern word = Pattern.compile(wordToFind);
                Matcher match = word.matcher(event.getMessage().toLowerCase());

                while (match.find()) {
                    System.out.println("Found pute at index "+ match.start() +" - "+ (match.end()-1));
                    String part1 = event.getMessage().substring(0,match.start());
                    String part2 = event.getMessage().substring(match.end());
                    event.setMessage(part1+"putois"+part2);
                }
                int begin = event.getMessage().toLowerCase().indexOf("pute");


                int last = event.getMessage().toLowerCase().lastIndexOf("pute");



            }
            TextComponent msg = new TextComponent(TextComponent.fromLegacyText(event.getMessage().replaceAll("&","§")));
            //msg.setText(msg.getText().replaceAll("&","§"));
            message.addExtra(msg);

        message.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/msg "+event.getPlayer().getName()));
        players.spigot().sendMessage(message);
    }
//54


    event.getMessage();
    }

    public String getTime(Player player){
        Date today = new Date();
        System.out.println("Today, the date is "+today.getDate());
        System.out.println("Today is the "+today.getDay()+" of the week");
        if(today.getSeconds()<10){
            return today.getHours()+":"+today.getMinutes()+":0"+today.getSeconds();
        }else {
            return today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
        }




    }





}
