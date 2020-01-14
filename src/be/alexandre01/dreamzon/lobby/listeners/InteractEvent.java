package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.hub.HubInventory;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        //System.out.println(event.getPlayer().getItemInHand());
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(event.getItem());
        System.out.println(nmsItem.getName());
        if(event.getItem() != null){
            if(event.getItem().getType().equals(Material.COMPASS)){
                event.getPlayer().openInventory(HubInventory.menuAnimation(event.getPlayer(), DyeColor.YELLOW,"§6§lMenu"));
            }
            if(event.getItem().getType().equals(Material.EMERALD)){
                event.getPlayer().openInventory(HubInventory.menuAnimation(event.getPlayer(), DyeColor.GREEN,"§6§lSubscriber"));
            }
            if(event.getItem().equals(Material.SKULL)){
                event.getPlayer().sendMessage(event.getItem().getItemMeta().getDisplayName());
                if(event.getItem().getItemMeta().getDisplayName().equals("§6§lAmis")){
                    event.getPlayer().openInventory(HubInventory.menuAnimation(event.getPlayer(), DyeColor.BLUE,"§6§lAmis"));
                }
                if(event.getItem().getItemMeta().getDisplayName().equals("§6§lVotre Profil")){
                    event.getPlayer().openInventory(HubInventory.menuAnimation(event.getPlayer(), DyeColor.ORANGE,"§6§lVotre Profil"));
                }
            }

        }

    }
}
