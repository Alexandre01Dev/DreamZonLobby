package be.alexandre01.dreamzon.lobby;

import be.alexandre01.dreamzon.lobby.commands.BlockedCommand;
import be.alexandre01.dreamzon.lobby.commands.SkinCommand;
import be.alexandre01.dreamzon.lobby.commands.SpawnCommand;
import be.alexandre01.dreamzon.lobby.listeners.*;
import be.alexandre01.dreamzon.lobby.scoreboard.ScoreboardManager;
import be.alexandre01.dreamzon.lobby.utils.NPC;
import be.alexandre01.dreamzon.lobby.utils.NPCManager;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends JavaPlugin {
    public NPCManager npcManager = new NPCManager();
    public HashMap<Player, UUID> savesUUID = new HashMap<>();
    public static Location home;
    public ArrayList<Player> fastTime = new ArrayList<Player>();
    public List<Location> spawnpoints = new ArrayList<Location>();
    private static Main instance;
    public int maxPlayers = 10;
    private ArrayList<Player> playerList = new ArrayList<Player>();
    private File playersSkinFile = new File(getDataFolder(),"players_skins.yml");
    private FileConfiguration playersSkinConfig = YamlConfiguration.loadConfiguration(playersSkinFile);
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    public File spawnFile = new File(getDataFolder(),"spawn.yml");
    public FileConfiguration spawnConfig = YamlConfiguration.loadConfiguration(playersSkinFile);
    public File commandFile = new File(getDataFolder(),"blocked_commands.yml");
    public FileConfiguration commandConfig = YamlConfiguration.loadConfiguration(playersSkinFile);
    public HashMap<Player, HashMap<BlockPosition, Material>> protect = new HashMap<Player, HashMap<BlockPosition,Material>>();
    public HashMap<Player, NPC> villagerold = new HashMap<>();
    public HashMap<Player, HashMap<NPC, EntityPlayer>> test = new HashMap<>();
    public HashMap<Player, NPC> quest1 = new HashMap<>();
    public Location spawn;
    public ArrayList<String> commands = new ArrayList<>();
    public HashMap<Player,String> textures = new HashMap<>();
    @Override
    public void onEnable() {
        String url = "https://api.mojang.com/users/profiles/minecraft/Alexandre01";

        String UUIDJson = null;
        try {
            UUIDJson = IOUtils.toString(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(UUIDJson);
        JSONObject UUIDObject = null;
        try {
            UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String uuid = UUIDObject.get("id").toString();
        System.out.println(uuid);
        instance = this;
        saveDefaultConfig();
        if(!playersSkinFile.exists()){
            playersSkinFile.getParentFile().mkdirs();
            saveResource("players_skins.yml",false);
        }
        playersSkinConfig = new YamlConfiguration();
        try {
            playersSkinConfig.load(playersSkinFile);
        } catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
        if(!spawnFile.exists()){
            spawnFile.getParentFile().mkdirs();
            saveResource("spawn.yml",false);
        }
        spawnConfig = new YamlConfiguration();
        try {
            spawnConfig.load(spawnFile);
        } catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
        if(!commandFile.exists()){
            commandFile.getParentFile().mkdirs();
            saveResource("blocked_commands.yml",false);
        }
        commandConfig = new YamlConfiguration();
        try {
            commandConfig.load(commandFile);
        } catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
        //new FastTime().setFastTime();
        Bukkit.getPluginManager().registerEvents(new JoinEvent(),this);
        Bukkit.getPluginManager().registerEvents(new WeatherEvent(),this);
        Bukkit.getPluginManager().registerEvents(new LeaveEvent(),this);
        Bukkit.getPluginManager().registerEvents(new MoveBlockLift(),this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(),this);
        Bukkit.getPluginManager().registerEvents(new FishingEvent(),this);
        Bukkit.getPluginManager().registerEvents(new BlockPoseEvent(),this);
        Bukkit.getPluginManager().registerEvents(new MoveEvent(),this);
        Bukkit.getPluginManager().registerEvents(new ArmorStandClickEvent(),this);
        Bukkit.getPluginManager().registerEvents(new InteractEvent(),this);
        Bukkit.getPluginManager().registerEvents(new Sneak(),this);
        Bukkit.getPluginManager().registerEvents(new CommandEvent(),this);
        getCommand("blockcmd").setExecutor(new BlockedCommand());
        getCommand("skin").setExecutor(new SkinCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("setspawn").setExecutor(new SpawnCommand());
        getCommand("blabla").setExecutor(new SpawnCommand());
       /* getCommand("setspawn").setExecutor(new Spawn());
        Bukkit.getPluginManager().registerEvents(new ProtectPlayer(),this);*/
        if (getPlayersSkinConfig().contains("skins")){
            for(String all : getPlayersSkinConfig().getConfigurationSection("skins").getKeys(false)){
                System.out.println("DREAMZONLOBBY => "+all);
                if(getPlayersSkinConfig().contains("skins."+all+".texture") && getPlayersSkinConfig().contains("skins."+all+".signature")){
                    Main.getInstance().getPlayersSkinConfig().set("skins."+all+".texture", null);
                    Main.getInstance().getPlayersSkinConfig().set("skins."+all+".signature", null);
                    try {
                        Main.getInstance().getPlayersSkinConfig().save(Main.getInstance().getPlayersSkinFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        commands.addAll(commandConfig.getStringList("commands"));

        if(spawnConfig.contains("spawn")){
            spawn = (Location) spawnConfig.get("spawn");
        }
        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();

    }

    @Override
    public void onDisable() {
        scoreboardManager.onDisable();
    }




    public static Main getInstance() {
        return instance;
    }



    public ArrayList<Player> getPlayerList() {
        return playerList;

    }

    public FileConfiguration getPlayersSkinConfig() {
        return playersSkinConfig;
    }

    public File getPlayersSkinFile() {
        return playersSkinFile;
    }
    public NPCManager getNpc(){
        return npcManager;
    }
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public void sendPacket(Player player, Packet packet){
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }


}
