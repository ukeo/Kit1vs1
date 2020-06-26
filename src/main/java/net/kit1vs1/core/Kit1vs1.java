package net.kit1vs1.core;

import de.dytanic.cloudnet.bridge.CloudServer;
import net.kit1vs1.core.api.KIT_NHD_API;
import net.kit1vs1.core.api.KIT_UHC_API;
import net.kit1vs1.core.api.Kit_SG_API;
import net.kit1vs1.driver.MySQL;
import net.kit1vs1.game.api.GameLocationAPI;
import net.kit1vs1.game.api.GameUtil;
import net.kit1vs1.game.cmd.Game_Setup_CMD;
import net.kit1vs1.game.gamehandler.GameManager;
import net.kit1vs1.game.gamehandler.GameState;
import net.kit1vs1.game.gamehandler.Kit;
import net.kit1vs1.game.listener.*;
import net.kit1vs1.lobby.api.ScoreboardAPI;
import net.kit1vs1.lobby.cmd.Lobby_AddElo_CMD;
import net.kit1vs1.lobby.cmd.Lobby_Setup_CMD;
import net.kit1vs1.lobby.cmd.Lobby_Stats_CMD;
import net.kit1vs1.lobby.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Kit1vs1 extends JavaPlugin {

    private static Kit1vs1 instance;
    private static Kit kit;
    private static ArrayList<String> maps = new ArrayList<>();
    private static GameState gameState;
    private static final Kit_SG_API kitSgApi = new Kit_SG_API();
    private static final KIT_UHC_API kitUhcApi = new KIT_UHC_API();
    private static final KIT_NHD_API kitNhdApi = new KIT_NHD_API();
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
            .availableProcessors());
    public static String[] GG = new String [] {"gg", "GG", "good game", "gutes Spiel"};

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("[Kit1vs1] Starting System...");

        maps.add("Clay");

        getConfig().options().copyDefaults(true);
        getConfig().options().header("Kit1vs1 config | System by Espen | Modes (lobby/1vs1) | Kits(SG/UHC/NoHitDelay)");
        getConfig().addDefault("Settings.mode", "lobby");
        getConfig().addDefault("Settings.kit", "SG");
        getConfig().addDefault("Settings.ranked", false);
        getConfig().addDefault("Settings.seasonStart", "01.06.2020");
        getConfig().addDefault("Settings.seasonEnd", "01.08.2020");
        getConfig().addDefault("Settings.maps", maps);
        saveConfig();

        try {
            GlobalUtil.START_SEASON = new SimpleDateFormat("dd.MM.yyy").parse(getConfig().getString("Settings.seasonStart"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            GlobalUtil.END_SEASON = new SimpleDateFormat("dd.MM.yyy").parse(getConfig().getString("Settings.seasonEnd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GlobalUtil.IS_RANKED = getConfig().getBoolean("Settings.ranked");

        System.out.println("[Kit1vs1] Config registered!");

        maps = (ArrayList<String>) getConfig().getStringList("Settings.maps");
        System.out.println("[Kit1vs1] Initialised maps!");

        //ADD LATER ON
        System.out.println("[Kit1vs1] Searching Module...");
        if (getConfig().getString("Settings.mode").equalsIgnoreCase("lobby")) {
            registerLobbySystems();
            System.out.println("[Kit1vs1] Hooked into Lobby-Module!");
        } else {
            registerInGameSystems();
            System.out.println("[Kit1vs1] Hooked into InGame-Module");
        }

        System.out.println("[Kit1vs1] Hooking into database backend...");
        MySQL.setStandardMySQL();
        MySQL.readMySQL();
        new MySQL();
        MySQL.connect();
        MySQL.createTable();

        System.out.println("[Kit1vs1] Hooked into database backend!");

    }

    @Override
    public void onDisable() {
        executorService.shutdown();
    }

    public static Kit1vs1 getInstance() {
        return instance;
    }

    public  Kit getKit() {
        return kit;
    }

    public ArrayList<String> getMaps() {
        return maps;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        Kit1vs1.gameState = gameState;
    }

    public KIT_NHD_API getKitNhdApi() {
        return kitNhdApi;
    }

    public Kit_SG_API getKitSgApi() {
        return kitSgApi;
    }

    public KIT_UHC_API getKitUhcApi() {
        return kitUhcApi;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    private static void registerLobbySystems() {
        PluginManager pm = Bukkit.getPluginManager();
        System.out.println("[Kit1vs1] Getting PluginManager...");
        pm.registerEvents(new Lobby_DisableListener(), instance);
        pm.registerEvents(new Lobby_HubListener(), instance);
        pm.registerEvents(new Lobby_JoinListener(), instance);
        pm.registerEvents(new Lobby_QueueInteractListener(), instance);
        pm.registerEvents(new Lobby_ChangeSlotListener(), instance);
        pm.registerEvents(new Lobby_SetHeadsListener(), instance);
        System.out.println("[Kit1vs1] Hooked into Listener!");
        instance.getCommand("setup").setExecutor(new Lobby_Setup_CMD());
        instance.getCommand("addelo").setExecutor(new Lobby_AddElo_CMD());
        instance.getCommand("stats").setExecutor(new Lobby_Stats_CMD());
        System.out.println("[Kit1vs1] Registered Commands!");

        if (ScoreboardAPI.init()) {
            System.out.println("[Kit1vs1] ScoreboardAPI is ready. ["+ ScoreboardAPI.obj.getDisplayName() +"]");
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
            CloudServer.getInstance().getServerConfig().setExtra("");
            CloudServer.getInstance().setMotd("GlobalLobby");
            CloudServer.getInstance().setMaxPlayers(50);
            CloudServer.getInstance().update();
            System.out.println("[Kit1vs1] Updated Cloud Info!");
            GlobalUtil.checkIfSeasonExpired();
        }, 7L);

    }

    private static void registerInGameSystems() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Game_DisableListener(), instance);
        pm.registerEvents(new Game_HubListener(), instance);
        pm.registerEvents(new Game_JoinListener(), instance);
        pm.registerEvents(new Game_MoveListener(), instance);
        pm.registerEvents(new Game_TNTListener(), instance);
        pm.registerEvents(new Game_DeathListener(), instance);
        pm.registerEvents(new Game_InteractListener(), instance);
        pm.registerEvents(new Game_ProjectileHitListener(), instance);
        pm.registerEvents(new Game_ChatListener(), instance);
        System.out.println("[Kit1vs1] Hooked into Listener!");
        instance.getCommand("setup").setExecutor(new Game_Setup_CMD());
        System.out.println("[Kit1vs1] Registered Commands!");
        gameState = GameState.PRE;
        System.out.println("[Kit1vs1] Set GameState to PRE!");
        if (GameLocationAPI.f.exists()) {
            GameManager.startGame();
            System.out.println("[Kit1vs1] Starting GameEngine!");
        } else {
            System.out.println("[Kit1vs1] Setup-Mode is activated!");
        }

        GameUtil.KIT = instance.getConfig().getString("Settings.kit");
        System.out.println("[Kit1vs1] Fetching Kit...");

        if (GameUtil.KIT.equalsIgnoreCase("SG")) {
            kit = Kit.SG;
            System.out.println("[Kit1vs1] Hooked into Kit SG!");
        } else if (GameUtil.KIT.equalsIgnoreCase("UHC")) {
            kit = Kit.UHC;
            System.out.println("[Kit1vs1] Hooked into Kit UHC");
        } else if (GameUtil.KIT.equalsIgnoreCase("NoHitDelay")) {
            kit = Kit.NoHitDelay;
            System.out.println("[Kit1vs1] Hooked into Kit NHD!");
        }

        Random rnd = new Random();
        int pos = rnd.nextInt(instance.getMaps().size());
        String random = instance.getMaps().get(pos);
        GameUtil.MAP_INT = pos;
        GameUtil.MAP_NAME = random;
        System.out.println("[Kit1vs1] Defined random Map!");

        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
        CloudServer.getInstance().getServerConfig().setExtra("");
        CloudServer.getInstance().setMotd(GameUtil.MAP_NAME);
        CloudServer.getInstance().setMaxPlayers(2);
        CloudServer.getInstance().update();

        System.out.println("[Kit1vs1] Updated Cloud Info!");
        }, 7L);

    }

}
