package be.alexandre01.dreamzon.lobby.commands;

import be.alexandre01.dreamzon.lobby.utils.Books;
import be.alexandre01.dreamzon.lobby.utils.MessageConfig;
import be.alexandre01.dreamzon.lobby.utils.Signs;
import com.avaje.ebeaninternal.server.core.Message;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class SkinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("skin")){
                if(player.hasPermission("dz.skin")){
                    if(args.length==0){
                        Books.openBook(Books.getSkinBook(),player);
                    }else {
                        if(args[0].equalsIgnoreCase("choice")){

                            Signs.openSign(player,"> ","Entre un pseudo","---------------","---------------");

                        }else {
                            Books.openBook(Books.getSkinBook(),player);
                        }
                    }

                }else {
                    MessageConfig.noPerms(player,true);
                }
            }
        }
        return false;
    }
}
