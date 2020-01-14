package be.alexandre01.dreamzon.lobby.hub;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.utils.CustomHead;
import be.alexandre01.dreamzon.lobby.utils.ItemBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.UUID;

public class HubInventory {
    public static void HubInventory(Inventory inv,Player player){
        inv.clear();
        ItemBuilder menu = new ItemBuilder(Material.COMPASS);
        menu.setName("§6§lMenu");
        menu.addLoreLine("§7§l- §e§lClique droit pour voir les jeux");
        inv.setItem(4,menu.toItemStack());
        ItemBuilder shop = new ItemBuilder(Material.EMERALD);
        shop.addEnchant(Enchantment.LUCK,1);
        shop.setName("§6§lSubscriber");
        shop.addLoreLine("§7§l- §e§lClique droit pour voir le shopping");
        inv.setItem(6,shop.toItemStack());
        ItemBuilder lobbies = new ItemBuilder(Material.ENDER_PORTAL_FRAME);
        lobbies.setName("§6§lChangement de Lobby");
        lobbies.addLoreLine("§7§l- §e§lClique droit pour voir les lobbies");
        inv.setItem(8,lobbies.toItemStack());
        ItemBuilder settings = new ItemBuilder(Material.REDSTONE_TORCH_ON);
        settings.setName("§6§lParamètres");
        settings.addLoreLine("§7§l- §e§lClique droit pour voir les paramètres");
        inv.setItem(7,settings.toItemStack());
        ItemStack friends = CustomHead.CustomHead("eyJ0aW1lc3RhbXAiOjE1Nzc4ODIzMzIzMjIsInByb2ZpbGVJZCI6ImI3NDc5YmFlMjljNDRiMjNiYTU2MjgzMzc4ZjBlM2M2IiwicHJvZmlsZU5hbWUiOiJTeWxlZXgiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2E2NjRhMGIyYWM4MTQ0NmViMTYxYmFmM2I3NjU2ZjZlOWFmZWFmOTVlMjMyYmQwMjM1YThiYjhiNWQxNDI2MDIifX19");
        ItemMeta itemMeta = friends.getItemMeta();
        itemMeta.setDisplayName("§6§lAmis");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        friends.setItemMeta(itemMeta);
        inv.setItem(2,friends);
        ItemBuilder glass = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(byte)1);
        glass.setDyeColor(DyeColor.BLACK);
        glass.setName(" ");

        inv.setItem(3,glass.toItemStack());
        inv.setItem(1,glass.toItemStack());
        inv.setItem(5,glass.toItemStack());
        ItemStack infos = CustomHead.CustomHead(Main.getInstance().textures.get(player));
        ItemMeta itemMetaInfo = infos.getItemMeta();
        itemMetaInfo.setDisplayName("§6§lVotre Profil");
        itemMetaInfo.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        infos.setItemMeta(itemMetaInfo);

        inv.setItem(0,infos);
    }
    public static Inventory menuAnimation(Player player, DyeColor color,String title){
        ItemBuilder glass = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(byte)1);
        Inventory inv = Bukkit.createInventory(null, 36, title);
        ItemBuilder air = new ItemBuilder(Material.AIR);
        glass.setDyeColor(color);
        glass.setName(" ");

        new BukkitRunnable() {
            int valuemm = 2;
            int oldvaluemm=1;
            int valuemin  = 3;
            int oldvaluemin=2;
            int value=4;
            int oldvalue=3;
            int valueplus = 5;
            int oldvalueplus=4;
            int valuepp = 6;
            int oldvaluepp=5;

            int bvaluemm = 29;
            int boldvaluemm=30;
            int bvaluemin  = 30;
            int boldvaluemin=31;
            int bvalue=31;
            int boldvalue=32;
            int bvalueplus = 32;
            int boldvalueplus=33;
            int bvaluepp = 33;
            int boldvaluepp=34;

            @Override
            public void run() {
                inv.setItem(setValue(oldvaluepp),air.toItemStack());
                inv.setItem(setValue(oldvalueplus),air.toItemStack());
                inv.setItem(setValue(oldvaluemm),air.toItemStack());
                inv.setItem(setValue(oldvaluemin),air.toItemStack());
                inv.setItem(setValue(oldvalue),air.toItemStack());
                inv.setItem(setValue(value),glass.toItemStack());
                inv.setItem(setValue(valuemin),glass.toItemStack());
                inv.setItem(setValue(valuemm),glass.toItemStack());
                inv.setItem(setValue(valueplus),glass.toItemStack());
                inv.setItem(setValue(valuepp),glass.toItemStack());
                oldvaluemin = setValue(oldvaluemin);
                oldvaluemm = setValue(oldvaluemm);
                oldvalueplus = setValue(oldvalueplus);
                oldvaluepp = setValue(oldvaluepp);
                oldvalue = setValue(oldvalue);
                valuemin = setValue(valuemin);
                valuemm = setValue(valuemm);
                valueplus = setValue(valueplus);
                valuepp = setValue(valuepp);
                value = setValue(value);

                inv.setItem(setValue(boldvaluepp),air.toItemStack());
                inv.setItem(setValue(boldvalueplus),air.toItemStack());
                inv.setItem(setValue(boldvaluemm),air.toItemStack());
                inv.setItem(setValue(boldvaluemin),air.toItemStack());
                inv.setItem(setValue(boldvalue),air.toItemStack());
                inv.setItem(setValue(bvalue),glass.toItemStack());
                inv.setItem(setValue(bvaluemin),glass.toItemStack());
                inv.setItem(setValue(bvaluemm),glass.toItemStack());
                inv.setItem(setValue(bvalueplus),glass.toItemStack());
                inv.setItem(setValue(bvaluepp),glass.toItemStack());
                boldvaluemin = setValue(boldvaluemin);
                boldvaluemm = setValue(boldvaluemm);
                boldvalueplus = setValue(boldvalueplus);
                boldvaluepp = setValue(boldvaluepp);
                boldvalue = setValue(boldvalue);
                bvaluemin = setValue(bvaluemin);
                bvaluemm = setValue(bvaluemm);
                bvalueplus = setValue(bvalueplus);
                bvaluepp = setValue(bvaluepp);
                bvalue = setValue(bvalue);
            }
        }.runTaskTimer(Main.getInstance(),0,2l);
        return inv;
    }
    public static int setValue(int value){
        if(value==8){
            value = 17;
            return value;
        }else {
            if(value== 17){
                value = 26;
                return value;
            }else {
                if(value== 26){
                    value = 35;
                    return value;
                } else {
                    if(value>= 0 && value < 8){
                        value++;
                        return value;
                    }else {
                    if(value<= 35 && value >= 28){
                        value--;
                        return value;
                    }else {
                        if(value== 27){
                            value = 18;
                            return value;
                        }else {
                            if(value==18){
                                value=9;
                                return value;
                            }else{
                                if(value==9){
                                    value=0;
                                    return value;
                                }
                            }
                        }


                }
                    }
            }

        }
        }

        return value;
    }
}
