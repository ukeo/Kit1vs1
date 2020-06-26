package net.kit1vs1.game.gamehandler;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;
import de.musterbukkit.replaysystem.main.ReplayAPI;
import net.core.cloud.ServerAPI;
import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.api.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private static boolean gameEnded = false;

    public static void startGame() {
        GameUtil.PRE_SCHEDULER = Bukkit.getScheduler().scheduleSyncRepeatingTask(Kit1vs1.getInstance(), () -> {
            if (Bukkit.getOnlinePlayers().size() == 2) {
                GameUtil.PRE_TIMER--;
                Bukkit.getOnlinePlayers().forEach(all -> {
                    if (GameUtil.PRE_TIMER == 5) {
                        all.sendTitle("§45", "");
                        all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 2, 2);
                    }
                    if (GameUtil.PRE_TIMER == 4) {
                        all.sendTitle("§64", "");
                        all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 2, 2);
                    }
                    if (GameUtil.PRE_TIMER == 3) {
                        all.sendTitle("§e3", "");
                        all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 2, 2);
                    }
                    if (GameUtil.PRE_TIMER == 2) {
                        all.sendTitle("§22", "");
                        all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 2, 2);
                    }
                    if (GameUtil.PRE_TIMER == 1) {
                        all.sendTitle("§a1", "");
                        all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 2, 2);
                    }
                    if (GameUtil.PRE_TIMER == 0) {
                        all.sendTitle("§a§lGO", "");
                        all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
                        GameUtil.setPlayerKit(all);
                    }
                });
                if (GameUtil.PRE_TIMER <= 0) {
                    Kit1vs1.getInstance().setGameState(GameState.INGAME);
                    Bukkit.getScheduler().cancelTask(GameUtil.PRE_SCHEDULER);
                }
            }
        }, 20L, 20L);
    }

    public static void gameEnd() {
        if (!gameEnded) {
            gameEnded = true;
            Kit1vs1.getInstance().setGameState(GameState.ENDING);
            GameUtil.ENDING_SCHEDULER = Bukkit.getScheduler().scheduleSyncRepeatingTask(Kit1vs1.getInstance(), new Runnable() {
                @Override
                public void run() {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (GameUtil.ENDING_TIMER == 5) {
                            all.getInventory().clear();
                            all.getInventory().setArmorContents(null);
                            all.getInventory().setItem(4, GameUtil.HUB);
                            all.setGameMode(GameMode.ADVENTURE);
                            all.sendMessage(GlobalUtil.PREFIX + "§7Der Server startet in ein paar " +
                                    "Sekunden §cneu§7.");
                            all.sendMessage(GlobalUtil.PREFIX + "§7Die GameID dieser Runde lautet §e§l" + ReplayAPI.getReplayID() + "§7.");

                        }
                    }
                    if (GameUtil.ENDING_TIMER <= 0) {

                        List<String> server = new ArrayList<>();
                        for (ServerInfo info : CloudAPI.getInstance().getServers("Kit1vs1")) {
                            server.add(info.getServiceId().getServerId());
                        }

                        String lobbyServer = server.get(new Random().nextInt(server.size()));

                        for (Player all : Bukkit.getOnlinePlayers()) {
                            ServerAPI.sendPlayer(all.getUniqueId(), lobbyServer);
                        }
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.getScheduler().cancelTask(GameUtil.ENDING_SCHEDULER);
                                Bukkit.getServer().shutdown();
                            }
                        }, 50);
                    } else {
                        GameUtil.ENDING_TIMER--;
                    }
                }
            }, 0, 20);
        }
    }

}
