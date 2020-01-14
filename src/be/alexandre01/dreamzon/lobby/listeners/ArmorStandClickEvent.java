package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.utils.Cuboid;
import be.alexandre01.dreamzon.lobby.utils.FakeBlock;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorStandClickEvent implements Listener {
    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Location loc1 = new Location(player.getWorld(), 516, 95, 1466);
        Location loc2 = new Location(player.getWorld(), 509, 98, 1471);

        Cuboid cuboid = new Cuboid(loc1, loc2);
        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            if (cuboid.contains(player.getLocation()) && event.getRightClicked() == armorStand) {
                Location as1 = new Location(armorStand.getWorld(), 509, 96, 1470);
                Location as2 = new Location(armorStand.getWorld(), 509, 96, 1469);
                Location as3 = new Location(armorStand.getWorld(), 509, 96, 1468);
                Location as4 = new Location(armorStand.getWorld(), 509, 96, 1467);

                if (armorStand.getHelmet().getType() == Material.LEATHER_HELMET) {

                    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) armorStand.getHelmet().getItemMeta();
                    leatherArmorMeta.getColor();
                    event.setCancelled(true);
                    if (leatherArmorMeta.getColor().equals(Color.fromRGB(252, 0, 0))) {
                        ItemStack r1 = new ItemStack(Material.LEATHER_HELMET);
                        LeatherArmorMeta leatherHelmetMeta = (LeatherArmorMeta) r1.getItemMeta();
                        leatherHelmetMeta.setColor(Color.fromRGB(252, 252, 252));
                        r1.setItemMeta(leatherHelmetMeta);
                        armorStand.setHelmet(r1);

                        ItemStack r2 = new ItemStack(Material.LEATHER_CHESTPLATE);
                        LeatherArmorMeta leatherChestplatMeta = (LeatherArmorMeta) r2.getItemMeta();
                        leatherChestplatMeta.setColor(Color.fromRGB(252, 252, 252));
                        r2.setItemMeta(leatherChestplatMeta);
                        armorStand.setChestplate(r2);

                        ItemStack r3 = new ItemStack(Material.LEATHER_LEGGINGS);
                        LeatherArmorMeta leatherLegginsMeta = (LeatherArmorMeta) r3.getItemMeta();
                        leatherLegginsMeta.setColor(Color.fromRGB(252, 252, 252));
                        r3.setItemMeta(leatherLegginsMeta);
                        armorStand.setLeggings(r3);

                        ItemStack r4 = new ItemStack(Material.LEATHER_BOOTS);
                        LeatherArmorMeta leatherbootsMeta = (LeatherArmorMeta) r4.getItemMeta();
                        leatherbootsMeta.setColor(Color.fromRGB(252, 252, 252));
                        r4.setItemMeta(leatherbootsMeta);
                        armorStand.setBoots(r4);
                    }
                    if (leatherArmorMeta.getColor().equals(Color.fromRGB(252, 252, 252))) {
                        ItemStack w1 = new ItemStack(Material.LEATHER_HELMET);
                        LeatherArmorMeta leatherHelmetMeta = (LeatherArmorMeta) w1.getItemMeta();
                        leatherHelmetMeta.setColor(Color.fromRGB(0, 252, 0));
                        w1.setItemMeta(leatherHelmetMeta);
                        armorStand.setHelmet(w1);

                        ItemStack w2 = new ItemStack(Material.LEATHER_CHESTPLATE);
                        LeatherArmorMeta leatherChestplatMeta = (LeatherArmorMeta) w2.getItemMeta();
                        leatherChestplatMeta.setColor(Color.fromRGB(0, 252, 0));
                        w2.setItemMeta(leatherChestplatMeta);
                        armorStand.setChestplate(w2);

                        ItemStack w3 = new ItemStack(Material.LEATHER_LEGGINGS);
                        LeatherArmorMeta leatherLegginsMeta = (LeatherArmorMeta) w3.getItemMeta();
                        leatherLegginsMeta.setColor(Color.fromRGB(0, 252, 0));
                        w3.setItemMeta(leatherLegginsMeta);
                        armorStand.setLeggings(w3);

                        ItemStack w4 = new ItemStack(Material.LEATHER_BOOTS);
                        LeatherArmorMeta leatherbootsMeta = (LeatherArmorMeta) w4.getItemMeta();
                        leatherbootsMeta.setColor(Color.fromRGB(0, 252, 0));
                        w4.setItemMeta(leatherbootsMeta);
                        armorStand.setBoots(w4);
                    }
                    if (leatherArmorMeta.getColor().equals(Color.fromRGB(0, 252, 0))) {
                        ItemStack g1 = new ItemStack(Material.LEATHER_HELMET);
                        LeatherArmorMeta leatherHelmetMeta = (LeatherArmorMeta) g1.getItemMeta();
                        leatherHelmetMeta.setColor(Color.fromRGB(252, 0, 252));
                        g1.setItemMeta(leatherHelmetMeta);
                        armorStand.setHelmet(g1);

                        ItemStack g2 = new ItemStack(Material.LEATHER_CHESTPLATE);
                        LeatherArmorMeta leatherChestplatMeta = (LeatherArmorMeta) g2.getItemMeta();
                        leatherChestplatMeta.setColor(Color.fromRGB(252, 0, 252));
                        g2.setItemMeta(leatherChestplatMeta);
                        armorStand.setChestplate(g2);

                        ItemStack g3 = new ItemStack(Material.LEATHER_LEGGINGS);
                        LeatherArmorMeta leatherLegginsMeta = (LeatherArmorMeta) g3.getItemMeta();
                        leatherLegginsMeta.setColor(Color.fromRGB(252, 0, 252));
                        g3.setItemMeta(leatherLegginsMeta);
                        armorStand.setLeggings(g3);

                        ItemStack g4 = new ItemStack(Material.LEATHER_BOOTS);
                        LeatherArmorMeta leatherbootsMeta = (LeatherArmorMeta) g4.getItemMeta();
                        leatherbootsMeta.setColor(Color.fromRGB(252, 0, 252));
                        g4.setItemMeta(leatherbootsMeta);
                        armorStand.setBoots(g4);
                    }
                    if (leatherArmorMeta.getColor().equals(Color.fromRGB(252, 0, 252))) {
                        ItemStack p1 = new ItemStack(Material.LEATHER_HELMET);
                        LeatherArmorMeta leatherHelmetMeta = (LeatherArmorMeta) p1.getItemMeta();
                        leatherHelmetMeta.setColor(Color.fromRGB(252, 0, 0));
                        p1.setItemMeta(leatherHelmetMeta);
                        armorStand.setHelmet(p1);

                        ItemStack p2 = new ItemStack(Material.LEATHER_CHESTPLATE);
                        LeatherArmorMeta leatherChestplatMeta = (LeatherArmorMeta) p2.getItemMeta();
                        leatherChestplatMeta.setColor(Color.fromRGB(252, 0, 0));
                        p2.setItemMeta(leatherChestplatMeta);
                        armorStand.setChestplate(p2);

                        ItemStack p3 = new ItemStack(Material.LEATHER_LEGGINGS);
                        LeatherArmorMeta leatherLegginsMeta = (LeatherArmorMeta) p3.getItemMeta();
                        leatherLegginsMeta.setColor(Color.fromRGB(252, 0, 0));
                        p3.setItemMeta(leatherLegginsMeta);
                        armorStand.setLeggings(p3);

                        ItemStack p4 = new ItemStack(Material.LEATHER_BOOTS);
                        LeatherArmorMeta leatherbootsMeta = (LeatherArmorMeta) p4.getItemMeta();
                        leatherbootsMeta.setColor(Color.fromRGB(252, 0, 0));
                        p4.setItemMeta(leatherbootsMeta);
                        armorStand.setBoots(p4);
                        if (armorStand.getLocation() == as1) {
                            ItemStack g1 = new ItemStack(Material.LEATHER_HELMET);
                            LeatherArmorMeta g1Meta = (LeatherArmorMeta) g1.getItemMeta();
                            g1Meta.setColor(Color.fromRGB(252, 0, 252));
                            g1.setItemMeta(g1Meta);
                            if(armorStand.getHelmet().getItemMeta() == g1) {
                                if(armorStand.getLocation() == as2){
                                    if(armorStand.getHelmet().getItemMeta() == p1){
                                        if(armorStand.getLocation() == as3) {
                                            ItemStack w1 = new ItemStack(Material.LEATHER_HELMET);
                                            LeatherArmorMeta w1Meta = (LeatherArmorMeta) w1.getItemMeta();
                                            w1Meta.setColor(Color.fromRGB(0, 252, 0));
                                            w1.setItemMeta(w1Meta);
                                            if(armorStand.getHelmet().getItemMeta() == w1){
                                                if(armorStand.getLocation() == as3) {
                                                    ItemStack r1 = new ItemStack(Material.LEATHER_HELMET);
                                                    LeatherArmorMeta r1Meta = (LeatherArmorMeta) r1.getItemMeta();
                                                    r1Meta.setColor(Color.fromRGB(252, 252, 252));
                                                    r1.setItemMeta(r1Meta);
                                                    if(armorStand.getHelmet().getItemMeta() == r1){
                                                        player.sendMessage("DEBUG: CODE DECHIFRE");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}