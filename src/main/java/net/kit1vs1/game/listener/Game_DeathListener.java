package net.kit1vs1.game.listener;

import net.core.api.CoinsAPI;
import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.gamehandler.GameManager;
import net.kit1vs1.game.gamehandler.Kit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Game_DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        event.setDeathMessage(null);

        if (player.getKiller() != null) {
            Player killer = player.getKiller();
            CoinsAPI.addCoinsByUUID(killer.getUniqueId(), 10);

            Bukkit.getOnlinePlayers().forEach(all -> {
                all.sendMessage(GlobalUtil.PREFIX + "§a" + killer.getName() + " §7hat das Spiel gewonnen!");
            });

            GameManager.gameEnd();

            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                player.spigot().respawn();
                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(player.getLocation().add(0, 5, 0));
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setHealth(20D);
                player.setFoodLevel(20);
                Bukkit.getOnlinePlayers().forEach(all -> {
                    if (player != all) {
                        player.teleport(all);
                    }
                });
            }, 20L);

            if (GlobalUtil.IS_RANKED) {

                if (Kit1vs1.getInstance().getKit().equals(Kit.SG)) {
                    Kit1vs1.getInstance().getExecutorService().execute(new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (Kit1vs1.getInstance().getKitSgApi().getBoolean(killer.getUniqueId(), "KIT_SG", "STILL_RANKING")) {
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer.getUniqueId(), "KIT_SG", "KILLS", 1);
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer.getUniqueId(), "KIT_SG", "WINS", 1);
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer.getUniqueId(), "KIT_SG", "ELO", 2);
                                Kit1vs1.getInstance().getKitSgApi().removeInt(player.getUniqueId(), "KIT_SG", "ELO", 2);
                                Kit1vs1.getInstance().getKitSgApi().addInt(player.getUniqueId(), "KIT_SG", "DEATHS", 1);
                                int killerElo = Kit1vs1.getInstance().getKitSgApi().getInt(killer.getUniqueId(), "KIT_SG", "ELO");
                                int killerWins = Kit1vs1.getInstance().getKitSgApi().getInt(killer.getUniqueId(), "KIT_SG", "WINS");
                                if (killerWins == 15) {
                                    Kit1vs1.getInstance().getKitSgApi().setBoolean(killer.getUniqueId(), "KIT_SG", "STILL_RANKING", false);
                                    if (killerElo >= 0 && killerElo <= 9) {
                                        Kit1vs1.getInstance().getKitSgApi().setInt(killer.getUniqueId(), "KIT_SG", "ELO", 200);
                                        killer.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 10 && killerElo <= 19) {
                                        Kit1vs1.getInstance().getKitSgApi().setInt(killer.getUniqueId(), "KIT_SG", "ELO", 400);
                                        killer.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 20 && killerElo <= 29) {
                                        Kit1vs1.getInstance().getKitSgApi().setInt(killer.getUniqueId(), "KIT_SG", "ELO", 500);
                                        killer.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo == 30) {
                                        Kit1vs1.getInstance().getKitSgApi().setInt(killer.getUniqueId(), "KIT_SG", "ELO", 650);
                                        killer.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            } else {

                                int elo = Kit1vs1.getInstance().getKitSgApi().getInt(killer.getUniqueId(), "KIT_SG", "ELO");
                                int afterWinElo = elo + 20;
                                int eloPlayer = Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "ELO");
                                int afterLoseElo = eloPlayer - 20;

                                Kit1vs1.getInstance().getKitSgApi().addInt(killer.getUniqueId(), "KIT_SG", "KILLS", 1);
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer.getUniqueId(), "KIT_SG", "WINS", 1);
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer.getUniqueId(), "KIT_SG", "ELO", 20);
                                Kit1vs1.getInstance().getKitSgApi().removeInt(player.getUniqueId(), "KIT_SG", "ELO", 20);
                                Kit1vs1.getInstance().getKitSgApi().addInt(player.getUniqueId(), "KIT_SG", "DEATHS", 1);
                                if (afterWinElo >= 1000) {
                                    Kit1vs1.getInstance().getKitSgApi().addInt(killer.getUniqueId(), "KIT_SG", "RANK_WINS", 1);
                                }
                                //ADD DONWRANK

                                if (eloPlayer >= 500 && eloPlayer <= 999) {
                                    if (afterLoseElo <= 499) {
                                        player.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (eloPlayer >= 1000) {
                                    if (afterLoseElo <= 999) {
                                        player.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }

                                if (elo <= 499) {
                                    if (afterWinElo > elo) {
                                        killer.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (elo >= 500 && elo <= 999) {
                                    if (afterWinElo >= 980 && afterWinElo > elo) {
                                        killer.sendTitle("§7Neue Skillgruppe", "§6§lGold");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            }
                        }
                    });
                }
                if (Kit1vs1.getInstance().getKit().equals(Kit.UHC)) {
                    Kit1vs1.getInstance().getExecutorService().execute(new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (Kit1vs1.getInstance().getKitUhcApi().getBoolean(killer.getUniqueId(), "KIT_UHC", "STILL_RANKING")) {
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer.getUniqueId(), "KIT_UHC", "KILLS", 1);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer.getUniqueId(), "KIT_UHC", "WINS", 1);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer.getUniqueId(), "KIT_UHC", "ELO", 2);
                                Kit1vs1.getInstance().getKitUhcApi().removeInt(player.getUniqueId(), "KIT_UHC", "ELO", 2);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(player.getUniqueId(), "KIT_UHC", "DEATHS", 1);
                                int killerElo = Kit1vs1.getInstance().getKitUhcApi().getInt(killer.getUniqueId(), "KIT_UHC", "ELO");
                                int killerWins = Kit1vs1.getInstance().getKitUhcApi().getInt(killer.getUniqueId(), "KIT_UHC", "WINS");
                                if (killerWins == 15) {
                                    Kit1vs1.getInstance().getKitUhcApi().setBoolean(killer.getUniqueId(), "KIT_UHC", "STILL_RANKING", false);
                                    if (killerElo >= 0 && killerElo <= 9) {
                                        Kit1vs1.getInstance().getKitUhcApi().setInt(killer.getUniqueId(), "KIT_UHC", "ELO", 200);
                                        killer.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 10 && killerElo <= 19) {
                                        Kit1vs1.getInstance().getKitUhcApi().setInt(killer.getUniqueId(), "KIT_UHC", "ELO", 400);
                                        killer.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 20 && killerElo <= 29) {
                                        Kit1vs1.getInstance().getKitUhcApi().setInt(killer.getUniqueId(), "KIT_UHC", "ELO", 500);
                                        killer.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo == 30) {
                                        Kit1vs1.getInstance().getKitUhcApi().setInt(killer.getUniqueId(), "KIT_UHC", "ELO", 650);
                                        killer.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            } else {

                                int elo = Kit1vs1.getInstance().getKitUhcApi().getInt(killer.getUniqueId(), "KIT_UHC", "ELO");
                                int afterWinElo = elo + 20;
                                int eloPlayer = Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "ELO");
                                int afterLoseElo = eloPlayer - 20;

                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer.getUniqueId(), "KIT_UHC", "KILLS", 1);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer.getUniqueId(), "KIT_UHC", "WINS", 1);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer.getUniqueId(), "KIT_UHC", "ELO", 20);
                                Kit1vs1.getInstance().getKitUhcApi().removeInt(player.getUniqueId(), "KIT_UHC", "ELO", 20);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(player.getUniqueId(), "KIT_UHC", "DEATHS", 1);
                                if (afterWinElo >= 1000) {
                                    Kit1vs1.getInstance().getKitUhcApi().addInt(killer.getUniqueId(), "KIT_UHC", "RANK_WINS", 1);
                                }
                                //ADD DONWRANK

                                if (eloPlayer >= 500 && eloPlayer <= 999) {
                                    if (afterLoseElo <= 499) {
                                        player.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (eloPlayer >= 1000) {
                                    if (afterLoseElo <= 999) {
                                        player.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }

                                if (elo <= 499) {
                                    if (afterWinElo > elo) {
                                        killer.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (elo >= 500 && elo <= 999) {
                                    if (afterWinElo >= 980 && afterWinElo > elo) {
                                        killer.sendTitle("§7Neue Skillgruppe", "§6§lGold");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            }
                        }
                    });
                }

                if (Kit1vs1.getInstance().getKit().equals(Kit.NoHitDelay)) {
                    Kit1vs1.getInstance().getExecutorService().execute(new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (Kit1vs1.getInstance().getKitNhdApi().getBoolean(killer.getUniqueId(), "KIT_NHD", "STILL_RANKING")) {
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer.getUniqueId(), "KIT_NHD", "KILLS", 1);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer.getUniqueId(), "KIT_NHD", "WINS", 1);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer.getUniqueId(), "KIT_NHD", "ELO", 2);
                                Kit1vs1.getInstance().getKitNhdApi().removeInt(player.getUniqueId(), "KIT_NHD", "ELO", 2);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(player.getUniqueId(), "KIT_NHD", "DEATHS", 1);
                                int killerElo = Kit1vs1.getInstance().getKitNhdApi().getInt(killer.getUniqueId(), "KIT_NHD", "ELO");
                                int killerWins = Kit1vs1.getInstance().getKitNhdApi().getInt(killer.getUniqueId(), "KIT_NHD", "WINS");
                                if (killerWins == 15) {
                                    Kit1vs1.getInstance().getKitNhdApi().setBoolean(killer.getUniqueId(), "KIT_NHD", "STILL_RANKING", false);
                                    if (killerElo >= 0 && killerElo <= 9) {
                                        Kit1vs1.getInstance().getKitNhdApi().setInt(killer.getUniqueId(), "KIT_NHD", "ELO", 200);
                                        killer.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 10 && killerElo <= 19) {
                                        Kit1vs1.getInstance().getKitNhdApi().setInt(killer.getUniqueId(), "KIT_NHD", "ELO", 400);
                                        killer.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 20 && killerElo <= 29) {
                                        Kit1vs1.getInstance().getKitNhdApi().setInt(killer.getUniqueId(), "KIT_NHD", "ELO", 500);
                                        killer.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo == 30) {
                                        Kit1vs1.getInstance().getKitNhdApi().setInt(killer.getUniqueId(), "KIT_NHD", "ELO", 650);
                                        killer.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            } else {

                                int elo = Kit1vs1.getInstance().getKitNhdApi().getInt(killer.getUniqueId(), "KIT_NHD", "ELO");
                                int afterWinElo = elo + 20;
                                int eloPlayer = Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "ELO");
                                int afterLoseElo = eloPlayer - 20;

                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer.getUniqueId(), "KIT_NHD", "KILLS", 1);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer.getUniqueId(), "KIT_NHD", "WINS", 1);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer.getUniqueId(), "KIT_NHD", "ELO", 20);
                                Kit1vs1.getInstance().getKitNhdApi().removeInt(player.getUniqueId(), "KIT_NHD", "ELO", 20);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(player.getUniqueId(), "KIT_NHD", "DEATHS", 1);
                                if (afterWinElo >= 1000) {
                                    Kit1vs1.getInstance().getKitNhdApi().addInt(killer.getUniqueId(), "KIT_NHD", "RANK_WINS", 1);
                                }
                                //ADD DONWRANK

                                if (eloPlayer >= 500 && eloPlayer <= 999) {
                                    if (afterLoseElo <= 499) {
                                        player.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (eloPlayer >= 1000) {
                                    if (afterLoseElo <= 999) {
                                        player.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }

                                if (elo <= 499) {
                                    if (afterWinElo > elo) {
                                        killer.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (elo >= 500 && elo <= 999) {
                                    if (afterWinElo >= 980 && afterWinElo > elo) {
                                        killer.sendTitle("§7Neue Skillgruppe", "§6§lGold");
                                        killer.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            }
                        }
                    });
                }
            }

        } else {

            final Player[] killer = {null};

            Bukkit.getOnlinePlayers().forEach(all -> {
                if (player != all) {
                    killer[0] = all;
                }
                all.sendMessage(GlobalUtil.PREFIX + "§a" + killer[0].getName() + " §7hat das Spiel gewonnen!");
            });

            CoinsAPI.addCoinsByUUID(killer[0].getUniqueId(), 10);

            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                player.spigot().respawn();
                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(player.getLocation().add(0, 5, 0));
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setHealth(20D);
                player.setFoodLevel(20);
                Bukkit.getOnlinePlayers().forEach(all -> {
                    if (player != all) {
                        player.teleport(all);
                    }
                });
            }, 20L);

            if (GlobalUtil.IS_RANKED) {
                if (Kit1vs1.getInstance().getKit().equals(Kit.SG)) {
                    Kit1vs1.getInstance().getExecutorService().execute(new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (Kit1vs1.getInstance().getKitSgApi().getBoolean(killer[0].getUniqueId(), "KIT_SG", "STILL_RANKING")) {
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer[0].getUniqueId(), "KIT_SG", "KILLS", 1);
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer[0].getUniqueId(), "KIT_SG", "WINS", 1);
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer[0].getUniqueId(), "KIT_SG", "ELO", 2);
                                Kit1vs1.getInstance().getKitSgApi().removeInt(player.getUniqueId(), "KIT_SG", "ELO", 2);
                                Kit1vs1.getInstance().getKitSgApi().addInt(player.getUniqueId(), "KIT_SG", "DEATHS", 1);
                                int killerElo = Kit1vs1.getInstance().getKitSgApi().getInt(killer[0].getUniqueId(), "KIT_SG", "ELO");
                                int killerWins = Kit1vs1.getInstance().getKitSgApi().getInt(killer[0].getUniqueId(), "KIT_SG", "WINS");
                                if (killerWins == 15) {
                                    Kit1vs1.getInstance().getKitSgApi().setBoolean(killer[0].getUniqueId(), "KIT_SG", "STILL_RANKING", false);
                                    if (killerElo >= 0 && killerElo <= 9) {
                                        Kit1vs1.getInstance().getKitSgApi().setInt(killer[0].getUniqueId(), "KIT_SG", "ELO", 200);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 10 && killerElo <= 19) {
                                        Kit1vs1.getInstance().getKitSgApi().setInt(killer[0].getUniqueId(), "KIT_SG", "ELO", 400);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 20 && killerElo <= 29) {
                                        Kit1vs1.getInstance().getKitSgApi().setInt(killer[0].getUniqueId(), "KIT_SG", "ELO", 500);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo == 30) {
                                        Kit1vs1.getInstance().getKitSgApi().setInt(killer[0].getUniqueId(), "KIT_SG", "ELO", 650);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            } else {

                                int elo = Kit1vs1.getInstance().getKitSgApi().getInt(killer[0].getUniqueId(), "KIT_SG", "ELO");
                                int afterWinElo = elo + 20;
                                int eloPlayer = Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "ELO");
                                int afterLoseElo = eloPlayer - 20;

                                Kit1vs1.getInstance().getKitSgApi().addInt(killer[0].getUniqueId(), "KIT_SG", "KILLS", 1);
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer[0].getUniqueId(), "KIT_SG", "WINS", 1);
                                Kit1vs1.getInstance().getKitSgApi().addInt(killer[0].getUniqueId(), "KIT_SG", "ELO", 20);
                                Kit1vs1.getInstance().getKitSgApi().removeInt(player.getUniqueId(), "KIT_SG", "ELO", 20);
                                Kit1vs1.getInstance().getKitSgApi().addInt(player.getUniqueId(), "KIT_SG", "DEATHS", 1);
                                if (afterWinElo >= 1000) {
                                    Kit1vs1.getInstance().getKitSgApi().addInt(killer[0].getUniqueId(), "KIT_SG", "RANK_WINS", 1);
                                }
                                //ADD DONWRANK

                                if (eloPlayer >= 500 && eloPlayer <= 999) {
                                    if (afterLoseElo <= 499) {
                                        player.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (eloPlayer >= 1000) {
                                    if (afterLoseElo <= 999) {
                                        player.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }

                                if (elo <= 499) {
                                    if (afterWinElo > elo) {
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (elo >= 500 && elo <= 999) {
                                    if (afterWinElo >= 980 && afterWinElo > elo) {
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§6§lGold");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            }
                        }
                    });
                }
                if (Kit1vs1.getInstance().getKit().equals(Kit.UHC)) {
                    Kit1vs1.getInstance().getExecutorService().execute(new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (Kit1vs1.getInstance().getKitUhcApi().getBoolean(killer[0].getUniqueId(), "KIT_UHC", "STILL_RANKING")) {
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer[0].getUniqueId(), "KIT_UHC", "KILLS", 1);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer[0].getUniqueId(), "KIT_UHC", "WINS", 1);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer[0].getUniqueId(), "KIT_UHC", "ELO", 2);
                                Kit1vs1.getInstance().getKitUhcApi().removeInt(player.getUniqueId(), "KIT_UHC", "ELO", 2);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(player.getUniqueId(), "KIT_UHC", "DEATHS", 1);
                                int killerElo = Kit1vs1.getInstance().getKitUhcApi().getInt(killer[0].getUniqueId(), "KIT_UHC", "ELO");
                                int killerWins = Kit1vs1.getInstance().getKitUhcApi().getInt(killer[0].getUniqueId(), "KIT_UHC", "WINS");
                                if (killerWins == 15) {
                                    Kit1vs1.getInstance().getKitUhcApi().setBoolean(killer[0].getUniqueId(), "KIT_UHC", "STILL_RANKING", false);
                                    if (killerElo >= 0 && killerElo <= 9) {
                                        Kit1vs1.getInstance().getKitUhcApi().setInt(killer[0].getUniqueId(), "KIT_UHC", "ELO", 200);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 10 && killerElo <= 19) {
                                        Kit1vs1.getInstance().getKitUhcApi().setInt(killer[0].getUniqueId(), "KIT_UHC", "ELO", 400);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 20 && killerElo <= 29) {
                                        Kit1vs1.getInstance().getKitUhcApi().setInt(killer[0].getUniqueId(), "KIT_UHC", "ELO", 500);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo == 30) {
                                        Kit1vs1.getInstance().getKitUhcApi().setInt(killer[0].getUniqueId(), "KIT_UHC", "ELO", 650);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            } else {

                                int elo = Kit1vs1.getInstance().getKitUhcApi().getInt(killer[0].getUniqueId(), "KIT_UHC", "ELO");
                                int afterWinElo = elo + 20;
                                int eloPlayer = Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "ELO");
                                int afterLoseElo = eloPlayer - 20;

                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer[0].getUniqueId(), "KIT_UHC", "KILLS", 1);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer[0].getUniqueId(), "KIT_UHC", "WINS", 1);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(killer[0].getUniqueId(), "KIT_UHC", "ELO", 20);
                                Kit1vs1.getInstance().getKitUhcApi().removeInt(player.getUniqueId(), "KIT_UHC", "ELO", 20);
                                Kit1vs1.getInstance().getKitUhcApi().addInt(player.getUniqueId(), "KIT_UHC", "DEATHS", 1);
                                if (afterWinElo >= 1000) {
                                    Kit1vs1.getInstance().getKitUhcApi().addInt(killer[0].getUniqueId(), "KIT_UHC", "RANK_WINS", 1);
                                }
                                //ADD DONWRANK

                                if (eloPlayer >= 500 && eloPlayer <= 999) {
                                    if (afterLoseElo <= 499) {
                                        player.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (eloPlayer >= 1000) {
                                    if (afterLoseElo <= 999) {
                                        player.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }

                                if (elo <= 499) {
                                    if (afterWinElo > elo) {
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (elo >= 500 && elo <= 999) {
                                    if (afterWinElo >= 980 && afterWinElo > elo) {
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§6§lGold");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            }
                        }
                    });
                }

                if (Kit1vs1.getInstance().getKit().equals(Kit.NoHitDelay)) {
                    Kit1vs1.getInstance().getExecutorService().execute(new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (Kit1vs1.getInstance().getKitNhdApi().getBoolean(killer[0].getUniqueId(), "KIT_NHD", "STILL_RANKING")) {
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer[0].getUniqueId(), "KIT_NHD", "KILLS", 1);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer[0].getUniqueId(), "KIT_NHD", "WINS", 1);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer[0].getUniqueId(), "KIT_NHD", "ELO", 2);
                                Kit1vs1.getInstance().getKitNhdApi().removeInt(player.getUniqueId(), "KIT_NHD", "ELO", 2);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(player.getUniqueId(), "KIT_NHD", "DEATHS", 1);
                                int killerElo = Kit1vs1.getInstance().getKitNhdApi().getInt(killer[0].getUniqueId(), "KIT_NHD", "ELO");
                                int killerWins = Kit1vs1.getInstance().getKitNhdApi().getInt(killer[0].getUniqueId(), "KIT_NHD", "WINS");
                                if (killerWins == 15) {
                                    Kit1vs1.getInstance().getKitNhdApi().setBoolean(killer[0].getUniqueId(), "KIT_NHD", "STILL_RANKING", false);
                                    if (killerElo >= 0 && killerElo <= 9) {
                                        Kit1vs1.getInstance().getKitNhdApi().setInt(killer[0].getUniqueId(), "KIT_NHD", "ELO", 200);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 10 && killerElo <= 19) {
                                        Kit1vs1.getInstance().getKitNhdApi().setInt(killer[0].getUniqueId(), "KIT_NHD", "ELO", 400);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo >= 20 && killerElo <= 29) {
                                        Kit1vs1.getInstance().getKitNhdApi().setInt(killer[0].getUniqueId(), "KIT_NHD", "ELO", 500);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                    if (killerElo == 30) {
                                        Kit1vs1.getInstance().getKitNhdApi().setInt(killer[0].getUniqueId(), "KIT_NHD", "ELO", 650);
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            } else {

                                int elo = Kit1vs1.getInstance().getKitNhdApi().getInt(killer[0].getUniqueId(), "KIT_NHD", "ELO");
                                int afterWinElo = elo + 20;
                                int eloPlayer = Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "ELO");
                                int afterLoseElo = eloPlayer - 20;

                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer[0].getUniqueId(), "KIT_NHD", "KILLS", 1);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer[0].getUniqueId(), "KIT_NHD", "WINS", 1);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(killer[0].getUniqueId(), "KIT_NHD", "ELO", 20);
                                Kit1vs1.getInstance().getKitNhdApi().removeInt(player.getUniqueId(), "KIT_NHD", "ELO", 20);
                                Kit1vs1.getInstance().getKitNhdApi().addInt(player.getUniqueId(), "KIT_NHD", "DEATHS", 1);
                                if (afterWinElo >= 1000) {
                                    Kit1vs1.getInstance().getKitNhdApi().addInt(killer[0].getUniqueId(), "KIT_NHD", "RANK_WINS", 1);
                                }
                                //ADD DONWRANK

                                if (eloPlayer >= 500 && eloPlayer <= 999) {
                                    if (afterLoseElo <= 499) {
                                        player.sendTitle("§7Neue Skillgruppe", "§c§lBronze");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (eloPlayer >= 1000) {
                                    if (afterLoseElo <= 999) {
                                        player.sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }

                                if (elo <= 499) {
                                    if (afterWinElo > elo) {
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§7§lIron");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                                if (elo >= 500 && elo <= 999) {
                                    if (afterWinElo >= 980 && afterWinElo > elo) {
                                        killer[0].sendTitle("§7Neue Skillgruppe", "§6§lGold");
                                        killer[0].playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);
                                    }
                                }
                            }
                        }
                    });
                }
            }

            GameManager.gameEnd();

        }

    }

}
