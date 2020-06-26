package net.kit1vs1.lobby.listener;

import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.lobby.api.LobbyLocationAPI;
import net.kit1vs1.lobby.api.LobbyUtil;
import net.kit1vs1.lobby.api.ScoreboardAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Lobby_JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        LobbyUtil.setLobbyInv(player);

        Kit1vs1.getInstance().getKitSgApi().createPlayer(player.getUniqueId());
        Kit1vs1.getInstance().getKitUhcApi().createPlayer(player.getUniqueId());
        Kit1vs1.getInstance().getKitNhdApi().createPlayer(player.getUniqueId());

        LobbyUtil.LIST_QUEUE_NHD.remove(player);
        LobbyUtil.LIST_QUEUE_UHC.remove(player);
        LobbyUtil.LIST_QUEUE_SG.remove(player);
        LobbyUtil.JOINING_SERVER.remove(player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
            player.teleport(LobbyLocationAPI.getLocation("Lobby"));
            ScoreboardAPI.sendScoreboard(player);
        }, 7L);

        if (!LobbyUtil.RANKING_SET) {
            LobbyUtil.RANKING_SET = true;
            LobbyUtil.updateRanking();
        }


        Bukkit.getScheduler().scheduleAsyncDelayedTask(Kit1vs1.getInstance(), () -> {
            if (!LobbyUtil.ELO_NO_PLAYER_SG.contains(player) || !LobbyUtil.ELO_BRONZE_PLAYER_SG.contains(player) || !LobbyUtil.ELO_IRON_PLAYER_SG.contains(player) || !LobbyUtil.ELO_GOLD_PLAYER_SG.contains(player)) {

                if (Kit1vs1.getInstance().getKitSgApi().getBoolean(player.getUniqueId(), "KIT_SG", "STILL_RANKING")) {
                    LobbyUtil.ELO_NO_PLAYER_SG.add(player);
                } else {
                    int elo = Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "ELO");
                    if (elo <= 499) {
                        LobbyUtil.ELO_BRONZE_PLAYER_SG.add(player);
                    }
                    if (elo >= 500 && elo <= 999) {
                        LobbyUtil.ELO_IRON_PLAYER_SG.add(player);
                    }
                    if (elo >= 1000) {
                        LobbyUtil.ELO_GOLD_PLAYER_SG.add(player);
                    }
                }

            }
            if (!LobbyUtil.ELO_NO_PLAYER_UHC.contains(player) || !LobbyUtil.ELO_BRONZE_PLAYER_UHC.contains(player) || !LobbyUtil.ELO_IRON_PLAYER_UHC.contains(player) || !LobbyUtil.ELO_GOLD_PLAYER_UHC.contains(player)) {

                if (Kit1vs1.getInstance().getKitUhcApi().getBoolean(player.getUniqueId(), "KIT_UHC", "STILL_RANKING")) {
                    LobbyUtil.ELO_NO_PLAYER_UHC.add(player);
                } else {
                    int elo = Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "ELO");
                    if (elo <= 499) {
                        LobbyUtil.ELO_BRONZE_PLAYER_UHC.add(player);
                    }
                    if (elo >= 500 && elo <= 999) {
                        LobbyUtil.ELO_IRON_PLAYER_UHC.add(player);
                    }
                    if (elo >= 1000) {
                        LobbyUtil.ELO_GOLD_PLAYER_UHC.add(player);
                    }
                }

            }
            if (!LobbyUtil.ELO_NO_PLAYER_NHD.contains(player) || !LobbyUtil.ELO_BRONZE_PLAYER_NHD.contains(player) || !LobbyUtil.ELO_IRON_PLAYER_NHD.contains(player) || !LobbyUtil.ELO_GOLD_PLAYER_NHD.contains(player)) {

                if (Kit1vs1.getInstance().getKitNhdApi().getBoolean(player.getUniqueId(), "KIT_NHD", "STILL_RANKING")) {
                    LobbyUtil.ELO_NO_PLAYER_NHD.add(player);
                } else {
                    int elo = Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "ELO");
                    if (elo <= 499) {
                        LobbyUtil.ELO_BRONZE_PLAYER_NHD.add(player);
                    }
                    if (elo >= 500 && elo <= 999) {
                        LobbyUtil.ELO_IRON_PLAYER_NHD.add(player);
                    }
                    if (elo >= 1000) {
                        LobbyUtil.ELO_GOLD_PLAYER_NHD.add(player);
                    }
                }

            }
        }, 25L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();
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

        LobbyUtil.ELO_NO_PLAYER_SG.remove(player);
        LobbyUtil.ELO_NO_PLAYER_UHC.remove(player);
        LobbyUtil.ELO_NO_PLAYER_NHD.remove(player);

        LobbyUtil.ELO_BRONZE_PLAYER_SG.remove(player);
        LobbyUtil.ELO_BRONZE_PLAYER_UHC.remove(player);
        LobbyUtil.ELO_BRONZE_PLAYER_NHD.remove(player);

        LobbyUtil.ELO_IRON_PLAYER_SG.remove(player);
        LobbyUtil.ELO_IRON_PLAYER_UHC.remove(player);
        LobbyUtil.ELO_IRON_PLAYER_NHD.remove(player);

        LobbyUtil.ELO_GOLD_PLAYER_SG.remove(player);
        LobbyUtil.ELO_GOLD_PLAYER_UHC.remove(player);
        LobbyUtil.ELO_GOLD_PLAYER_NHD.remove(player);

    }

}
