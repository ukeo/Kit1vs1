package net.kit1vs1.lobby.api;

import net.core.api.ItemBuilder;
import net.kit1vs1.core.Kit1vs1;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class LobbyUtil {

    //NEED TO REWORK SINCE RANKING UPDATE!
    public static ArrayList<Player> LIST_QUEUE_SG = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_UHC = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_NHD = new ArrayList<>();
    public static ArrayList<Player> JOINING_SERVER = new ArrayList<>();

    //ELO RANKING SG
    public static ArrayList<Player> LIST_QUEUE_SG_ELO_NO = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_SG_BRONZE = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_SG_IRON = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_SG_GOLD = new ArrayList<>();

    //ELO RANKING UHC
    public static ArrayList<Player> LIST_QUEUE_UHC_ELO_NO = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_UHC_BRONZE = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_UHC_IRON = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_UHC_GOLD = new ArrayList<>();

    //ELO RANKING NHD
    public static ArrayList<Player> LIST_QUEUE_NHD_ELO_NO = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_NHD_BRONZE = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_NHD_IRON = new ArrayList<>();
    public static ArrayList<Player> LIST_QUEUE_NHD_GOLD = new ArrayList<>();

    //RANKING LOADING SG
    public static ArrayList<Player> ELO_NO_PLAYER_SG = new ArrayList<>();
    public static ArrayList<Player> ELO_BRONZE_PLAYER_SG = new ArrayList<>();
    public static ArrayList<Player> ELO_IRON_PLAYER_SG = new ArrayList<>();
    public static ArrayList<Player> ELO_GOLD_PLAYER_SG = new ArrayList<>();

    //RANKING LOADING UHC
    public static ArrayList<Player> ELO_NO_PLAYER_UHC = new ArrayList<>();
    public static ArrayList<Player> ELO_BRONZE_PLAYER_UHC = new ArrayList<>();
    public static ArrayList<Player> ELO_IRON_PLAYER_UHC = new ArrayList<>();
    public static ArrayList<Player> ELO_GOLD_PLAYER_UHC = new ArrayList<>();

    //RANKING LOADING NHD
    public static ArrayList<Player> ELO_NO_PLAYER_NHD = new ArrayList<>();
    public static ArrayList<Player> ELO_BRONZE_PLAYER_NHD = new ArrayList<>();
    public static ArrayList<Player> ELO_IRON_PLAYER_NHD = new ArrayList<>();
    public static ArrayList<Player> ELO_GOLD_PLAYER_NHD = new ArrayList<>();

    public static ArrayList<Player> SETUP_RANKING = new ArrayList<>();
    public static ArrayList<Player> CHALLENGER = new ArrayList<>();

    public static final ItemStack QUEUE_SG = new ItemBuilder(Material.IRON_SWORD).setName("§8» §7Kit §aSG").build();
    public static final ItemStack QUEUE_UHC = new ItemBuilder(Material.GOLDEN_APPLE).setName("§8» §7Kit §eUHC").build();
    public static final ItemStack QUEUE_NHD = new ItemBuilder(Material.DIAMOND_SWORD).setName("§8» §7Kit §bNoHitDelay").build();
    public static final ItemStack HUB = new ItemBuilder(Material.MAGMA_CREAM).setName("§8» §7Zurück zur §6§lLobby").build();

    public static boolean RANKING_SET = false;

    public static void setLobbyInv(Player player) {
        player.getInventory().clear();
        player.setHealthScale(20D);
        player.setFoodLevel(20);
        player.setHealth(20D);
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.SURVIVAL);

        player.getInventory().setItem(0, QUEUE_SG);
        player.getInventory().setItem(1, QUEUE_UHC);
        player.getInventory().setItem(2, QUEUE_NHD);
        player.getInventory().setItem(8, HUB);

    }

    public static void removePlayerFromQueues(Player player) {
        LobbyUtil.LIST_QUEUE_NHD.remove(player);
        LobbyUtil.LIST_QUEUE_UHC.remove(player);
        LobbyUtil.LIST_QUEUE_SG.remove(player);
        LobbyUtil.JOINING_SERVER.remove(player);

        LobbyUtil.LIST_QUEUE_SG_ELO_NO.remove(player);
        LobbyUtil.LIST_QUEUE_SG_BRONZE.remove(player);
        LobbyUtil.LIST_QUEUE_SG_IRON.remove(player);
        LobbyUtil.LIST_QUEUE_SG_GOLD.remove(player);

        LobbyUtil.LIST_QUEUE_UHC_ELO_NO.remove(player);
        LobbyUtil.LIST_QUEUE_UHC_BRONZE.remove(player);
        LobbyUtil.LIST_QUEUE_UHC_IRON.remove(player);
        LobbyUtil.LIST_QUEUE_UHC_GOLD.remove(player);

        LobbyUtil.LIST_QUEUE_NHD_ELO_NO.remove(player);
        LobbyUtil.LIST_QUEUE_NHD_BRONZE.remove(player);
        LobbyUtil.LIST_QUEUE_NHD_IRON.remove(player);
        LobbyUtil.LIST_QUEUE_NHD_GOLD.remove(player);
    }

    public static void updateRanking() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Kit1vs1.getInstance(), () -> {
            if (Bukkit.getOnlinePlayers().size() >= 1) {
                RankingSGAPI.loadRanking();
                RankingNHDAPI.loadRanking();
                RankingUHCAPI.loadRanking();
            }
        }, 20L, 60 * 5 * 20L);
    }

}
