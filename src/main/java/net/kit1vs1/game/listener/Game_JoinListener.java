package net.kit1vs1.game.listener;

import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.api.GameLocationAPI;
import net.kit1vs1.game.api.GameUtil;
import net.kit1vs1.game.gamehandler.GameManager;
import net.kit1vs1.game.gamehandler.GameState;
import net.kit1vs1.game.gamehandler.Kit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Game_JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        if (Kit1vs1.getInstance().getGameState().equals(GameState.PRE)) {
            if (GameUtil.TEAM_1.isEmpty()) {
                GameUtil.TEAM_1.add(player);
            } else {
                GameUtil.TEAM_2.add(player);
            }

            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                if (GameUtil.TEAM_1.contains(player)) {
                    player.teleport(GameLocationAPI.getLocation("spawn1." + GameUtil.MAP_INT));
                } else {
                    player.teleport(GameLocationAPI.getLocation("spawn2." + GameUtil.MAP_INT));
                }
            }, 7L);

            if (GlobalUtil.IS_RANKED) {

                Bukkit.getScheduler().scheduleAsyncDelayedTask(Kit1vs1.getInstance(), () -> {
                    if (Kit1vs1.getInstance().getKit().equals(Kit.SG)) {
                        if (Kit1vs1.getInstance().getKitSgApi().getBoolean(player.getUniqueId(), "KIT_SG", "STILL_RANKING")) {
                            int winsToRanked = 15 - Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "WINS");
                            player.sendMessage(GlobalUtil.PREFIX + "§7Du musst noch §9§l" + winsToRanked + " §7Spiele gewinnen um eingerankt zu werden.");
                        }
                    }
                    if (Kit1vs1.getInstance().getKit().equals(Kit.UHC)) {
                        if (Kit1vs1.getInstance().getKitUhcApi().getBoolean(player.getUniqueId(), "KIT_UHC", "STILL_RANKING")) {
                            int winsToRanked = 15 - Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "WINS");
                            player.sendMessage(GlobalUtil.PREFIX + "§7Du musst noch §9§l" + winsToRanked + " §7Spiele gewinnen um eingerankt zu werden.");
                        }
                    }
                    if (Kit1vs1.getInstance().getKit().equals(Kit.NoHitDelay)) {
                        if (Kit1vs1.getInstance().getKitNhdApi().getBoolean(player.getUniqueId(), "KIT_NHD", "STILL_RANKING")) {
                            int winsToRanked = 15 - Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "WINS");
                            player.sendMessage(GlobalUtil.PREFIX + "§7Du musst noch §9§l" + winsToRanked + " §7Spiele gewinnen um eingerankt zu werden.");
                        }
                    }
                }, 25L);
            }

        } else {
            Bukkit.getOnlinePlayers().forEach(all -> {
                if (all != player) {
                    all.hidePlayer(player);
                    player.teleport(all.getLocation());
                }
            });
            player.setGameMode(GameMode.SPECTATOR);
            player.setHealth(20D);
            player.setFoodLevel(20);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Kit1vs1.getInstance().getGameState().equals(GameState.INGAME) || Kit1vs1.getInstance().getGameState().equals(GameState.PRE)) {
            event.setQuitMessage(GlobalUtil.PREFIX + "§c" + player.getName() + " §7hat das Spiel verlassen! Das Spiel wird abgebrochen!");
            GameManager.gameEnd();
        }
        event.setQuitMessage(null);
    }

}
