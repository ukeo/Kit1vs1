package net.kit1vs1.lobby.listener;

import net.kit1vs1.lobby.api.LobbyUtil;
import net.kit1vs1.lobby.api.ScoreboardAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.ArrayList;

public class Lobby_ChangeSlotListener implements Listener {

    private static final ArrayList<Player> hasBoard = new ArrayList<>();

    @EventHandler
    public void onSlot(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (event.getNewSlot() == 0) {
            hasBoard.remove(player);
            if (LobbyUtil.ELO_NO_PLAYER_SG.contains(player)) {
                ScoreboardAPI.sendScoreboardUnranked(player);
            }
            if (LobbyUtil.ELO_BRONZE_PLAYER_SG.contains(player)) {
                ScoreboardAPI.sendScoreboardBronze(player);
            }
            if (LobbyUtil.ELO_IRON_PLAYER_SG.contains(player)) {
                ScoreboardAPI.sendScoreboardIron(player);
            }
            if (LobbyUtil.ELO_GOLD_PLAYER_SG.contains(player)) {
                ScoreboardAPI.sendScoreboardGold(player);
            }
        }
        if (event.getNewSlot() == 1) {
            hasBoard.remove(player);
            if (LobbyUtil.ELO_NO_PLAYER_UHC.contains(player)) {
                ScoreboardAPI.sendScoreboardUnranked(player);
            }
            if (LobbyUtil.ELO_BRONZE_PLAYER_UHC.contains(player)) {
                ScoreboardAPI.sendScoreboardBronze(player);
            }
            if (LobbyUtil.ELO_IRON_PLAYER_UHC.contains(player)) {
                ScoreboardAPI.sendScoreboardIron(player);
            }
            if (LobbyUtil.ELO_GOLD_PLAYER_UHC.contains(player)) {
                ScoreboardAPI.sendScoreboardGold(player);
            }
        }

        if (event.getNewSlot() == 2) {
            hasBoard.remove(player);
            if (LobbyUtil.ELO_NO_PLAYER_NHD.contains(player)) {
                ScoreboardAPI.sendScoreboardUnranked(player);
            }
            if (LobbyUtil.ELO_BRONZE_PLAYER_NHD.contains(player)) {
                ScoreboardAPI.sendScoreboardBronze(player);
            }
            if (LobbyUtil.ELO_IRON_PLAYER_NHD.contains(player)) {
                ScoreboardAPI.sendScoreboardIron(player);
            }
            if (LobbyUtil.ELO_GOLD_PLAYER_NHD.contains(player)) {
                ScoreboardAPI.sendScoreboardGold(player);
            }
        }
        if (event.getNewSlot() >= 3 && event.getNewSlot() <= 8) {
            if (!hasBoard.contains(player)) {
                ScoreboardAPI.sendScoreboard(player);
                hasBoard.add(player);
            }
        }
    }

}
