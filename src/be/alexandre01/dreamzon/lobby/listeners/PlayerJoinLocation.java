package be.alexandre01.dreamzon.lobby.listeners;

import be.alexandre01.dreamzon.lobby.Main;
import be.alexandre01.dreamzon.lobby.data.SkinsDataBase;
import be.alexandre01.dreamzon.lobby.utils.Cuboid;
import be.alexandre01.dreamzon.lobby.utils.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerJoinLocation  {
    public void isInZones(Player player){


        Location loc1 = new Location(player.getWorld(), 218, 74 ,1570  );
        Location loc2 = new Location(player.getWorld(),  275 ,140 ,1616);
        Cuboid cuboid = new Cuboid(loc1, loc2);
        //RENTRE

            if(cuboid.contains(player.getLocation())){

        player.sendMessage("DEBUG : TU ES DANS LA ZONE DE LA PREMIERE QUEST");
        //new NPCManager().createNpcCustomSkin(SkinsDataBase.VillagerLocation(),player,"Vieux villageois",SkinsDataBase.VillagerText(),SkinsDataBase.VillagerSignature(),SkinsDataBase.VillagerUUID());
        NPC npc = new NPC(player , "Villageoise", SkinsDataBase.getQuest1Loc());
        Main.getInstance().quest1.put(player, npc);
        npc.changeSkin(SkinsDataBase.getQuest1Text(),SkinsDataBase.getQuest1Sign());
        npc.spawn();
        npc.setCrouch();

        }
    }
}