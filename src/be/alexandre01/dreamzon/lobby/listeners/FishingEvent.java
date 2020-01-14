package be.alexandre01.dreamzon.lobby.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FishingEvent implements Listener{
    @EventHandler
    public void onFishing(PlayerFishEvent event){
        ItemStack item = ((Item)event.getCaught()).getItemStack();
        item.setType(Material.JUKEBOX);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§eReveilleur");
        itemMeta.setLore(Arrays.asList("§e Fais de la musique en la posant :)","§e Serais capable de reveiller n'importe qui.","§bRARE"));
        itemMeta.addEnchant(Enchantment.LUCK,1,false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);

    }
}
