package be.alexandre01.dreamzon.lobby.scoreboard;



import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.UUID;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard {
    private Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;

    PersonalScoreboard(Player player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "§9§lDream§b§lZon");

        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData(){}

    public void setLines(){
        objectiveSign.setDisplayName("§9§lDream§f§lZon");
        objectiveSign.setLine(1, "§3");
        objectiveSign.setLine(2, "§e§lInfos:");
        objectiveSign.setLine(3, "§f§l•§7 Compte : §e"+player.getName());
        if(player.hasPermission("dz.support")){
            objectiveSign.setLine(4, "§f§l•§7 Support : §a§l✓" );
        }else {
            objectiveSign.setLine(4, "§f§l•§7 Support : §c§l✕" );
        }


        objectiveSign.setLine(5, "§f§l•§7 Grade : §c§lNaN");
        objectiveSign.setLine(6, "§f§l•§7 Levels : §c§lNaN");
        objectiveSign.setLine(7, "§f§l•§7 Serveur : §c§lNaN");
        objectiveSign.setLine(8, "");
        objectiveSign.setLine(9, "§ebeta.dreamzon.fr");

        objectiveSign.updateLines();
    }

    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}