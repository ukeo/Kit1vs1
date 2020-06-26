package net.kit1vs1.lobby.listener;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.server.ServerState;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;
import net.core.cloud.ServerAPI;
import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.lobby.api.LobbyUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lobby_QueueInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == null) return;
        if (event.getItem() == null) return;
        if (event.getItem().equals(LobbyUtil.QUEUE_SG)) {
            event.setCancelled(true);
            if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (!LobbyUtil.LIST_QUEUE_SG.contains(player)) {
                    if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                        if (LobbyUtil.LIST_QUEUE_SG.size() < 2) {
                            LobbyUtil.removePlayerFromQueues(player);
                            LobbyUtil.LIST_QUEUE_SG.add(player);
                            player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange betreten.");
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 2);

                            //CHECK IF QUEUE IS FULL
                            if (LobbyUtil.LIST_QUEUE_SG.size() == 2) {
                                List<String> server = new ArrayList<>();
                                for (ServerInfo info : CloudAPI.getInstance().getServers("KITSG")) {
                                    if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                        server.add(info.getServiceId().getServerId());
                                    }
                                }

                                if (server.isEmpty()) {
                                    LobbyUtil.LIST_QUEUE_SG.forEach(sgQueuePlayer -> {
                                        sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                    });
                                    return;
                                }

                                String gameServer = server.get(new Random().nextInt(server.size()));

                                LobbyUtil.LIST_QUEUE_SG.forEach(sgQueuePlayer -> {
                                    LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                    sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                    ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                        LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                        LobbyUtil.LIST_QUEUE_SG.remove(sgQueuePlayer);
                                    }, 5 * 20L);
                                });
                            }
                        } else {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                        }
                    } else {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                        return;
                    }
                } else {
                    if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                        LobbyUtil.LIST_QUEUE_SG.remove(player);
                    } else {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                    }
                    return;
                }
                return;
            }else if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (!LobbyUtil.LIST_QUEUE_SG.contains(player)) {
                    if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                        if (LobbyUtil.ELO_NO_PLAYER_SG.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_SG_ELO_NO.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_SG_ELO_NO.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_SG_ELO_NO.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_SG_ELO_NO.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITSG")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_SG_ELO_NO.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_SG_ELO_NO.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_SG_ELO_NO.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_SG_ELO_NO.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                        if (LobbyUtil.ELO_BRONZE_PLAYER_SG.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_SG_BRONZE.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_SG_BRONZE.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_SG_BRONZE.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_SG_BRONZE.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITSG")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_SG_BRONZE.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_SG_BRONZE.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_SG_BRONZE.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_SG_BRONZE.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                        if (LobbyUtil.ELO_IRON_PLAYER_SG.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_SG_IRON.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_SG_IRON.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_SG_IRON.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_SG_IRON.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITSG")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_SG_IRON.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_SG_IRON.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_SG_IRON.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_SG_IRON.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                        if (LobbyUtil.ELO_GOLD_PLAYER_SG.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_SG_GOLD.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_SG_GOLD.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_SG_GOLD.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_SG_GOLD.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITSG")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_SG_GOLD.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_SG_GOLD.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_SG_GOLD.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_SG_GOLD.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                    }
                }
                return;
            }
        }
        if (event.getItem().equals(LobbyUtil.QUEUE_UHC)) {
            event.setCancelled(true);
            if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (!LobbyUtil.LIST_QUEUE_UHC.contains(player)) {
                    if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                        if (LobbyUtil.LIST_QUEUE_UHC.size() < 2) {
                            LobbyUtil.removePlayerFromQueues(player);
                            LobbyUtil.LIST_QUEUE_UHC.add(player);
                            player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange betreten.");
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 2);

                            //CHECK IF QUEUE IS FULL
                            if (LobbyUtil.LIST_QUEUE_UHC.size() == 2) {
                                List<String> server = new ArrayList<>();
                                for (ServerInfo info : CloudAPI.getInstance().getServers("KITUHC")) {
                                    if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                        server.add(info.getServiceId().getServerId());
                                    }
                                }

                                if (server.isEmpty()) {
                                    LobbyUtil.LIST_QUEUE_UHC.forEach(sgQueuePlayer -> {
                                        sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                    });
                                    return;
                                }

                                String gameServer = server.get(new Random().nextInt(server.size()));

                                LobbyUtil.LIST_QUEUE_UHC.forEach(sgQueuePlayer -> {
                                    LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                    sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                    ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                        LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                        LobbyUtil.LIST_QUEUE_UHC.remove(sgQueuePlayer);
                                    }, 5 * 20L);
                                });
                            }
                        } else {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                        }
                    } else {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                        return;
                    }
                } else {
                    if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                        LobbyUtil.LIST_QUEUE_SG.remove(player);
                    } else {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                    }
                    return;
                }
                return;
            }else if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (!LobbyUtil.LIST_QUEUE_UHC.contains(player)) {
                    if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                        if (LobbyUtil.ELO_NO_PLAYER_UHC.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_UHC_ELO_NO.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_UHC_ELO_NO.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_UHC_ELO_NO.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_UHC_ELO_NO.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITUHC")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_UHC_ELO_NO.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_UHC_ELO_NO.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_UHC_ELO_NO.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_UHC_ELO_NO.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                        if (LobbyUtil.ELO_BRONZE_PLAYER_UHC.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_UHC_BRONZE.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_UHC_BRONZE.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_UHC_BRONZE.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_UHC_BRONZE.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITUHC")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_UHC_BRONZE.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_UHC_BRONZE.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_UHC_BRONZE.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_UHC_BRONZE.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                        if (LobbyUtil.ELO_IRON_PLAYER_UHC.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_UHC_IRON.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_UHC_IRON.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_UHC_IRON.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_UHC_IRON.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITUHC")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_UHC_IRON.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_UHC_IRON.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_UHC_IRON.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_UHC_IRON.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                        if (LobbyUtil.ELO_GOLD_PLAYER_UHC.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_UHC_GOLD.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_UHC_GOLD.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_UHC_GOLD.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_UHC_GOLD.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITUHC")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_UHC_GOLD.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_UHC_GOLD.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_UHC_GOLD.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_UHC_GOLD.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                    }
                }
                return;
            }
        }
        if (event.getItem().equals(LobbyUtil.QUEUE_NHD)) {
            event.setCancelled(true);
            if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (!LobbyUtil.LIST_QUEUE_NHD.contains(player)) {
                    if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                        if (LobbyUtil.LIST_QUEUE_NHD.size() < 2) {
                            LobbyUtil.removePlayerFromQueues(player);
                            LobbyUtil.LIST_QUEUE_NHD.add(player);
                            player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange betreten.");
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 2);

                            //CHECK IF QUEUE IS FULL
                            if (LobbyUtil.LIST_QUEUE_NHD.size() == 2) {
                                List<String> server = new ArrayList<>();
                                for (ServerInfo info : CloudAPI.getInstance().getServers("KITNHD")) {
                                    if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                        server.add(info.getServiceId().getServerId());
                                    }
                                }

                                if (server.isEmpty()) {
                                    LobbyUtil.LIST_QUEUE_NHD.forEach(sgQueuePlayer -> {
                                        sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                    });
                                    return;
                                }

                                String gameServer = server.get(new Random().nextInt(server.size()));

                                LobbyUtil.LIST_QUEUE_NHD.forEach(sgQueuePlayer -> {
                                    LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                    sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                    ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                        LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                        LobbyUtil.LIST_QUEUE_NHD.remove(sgQueuePlayer);
                                    }, 5 * 20L);
                                });
                            }
                        } else {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                        }
                    } else {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                        return;
                    }
                } else {
                    if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                        LobbyUtil.LIST_QUEUE_NHD.remove(player);
                    } else {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                    }
                    return;
                }
                return;
            }else if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (!LobbyUtil.LIST_QUEUE_NHD.contains(player)) {
                    if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                        if (LobbyUtil.ELO_NO_PLAYER_NHD.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_NHD_ELO_NO.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_NHD_ELO_NO.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_NHD_ELO_NO.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_NHD_ELO_NO.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITNHD")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_NHD_ELO_NO.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_NHD_ELO_NO.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_NHD_ELO_NO.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_NHD_ELO_NO.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                        if (LobbyUtil.ELO_BRONZE_PLAYER_NHD.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_NHD_BRONZE.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_NHD_BRONZE.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_NHD_BRONZE.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_NHD_BRONZE.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITNHD")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_NHD_BRONZE.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_NHD_BRONZE.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_NHD_BRONZE.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_NHD_BRONZE.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                        if (LobbyUtil.ELO_IRON_PLAYER_NHD.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_NHD_IRON.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_NHD_IRON.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_NHD_IRON.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_NHD_IRON.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITNHD")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_NHD_IRON.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_NHD_IRON.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_NHD_IRON.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_NHD_IRON.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                        if (LobbyUtil.ELO_GOLD_PLAYER_NHD.contains(player)) {
                            if (!LobbyUtil.LIST_QUEUE_NHD_GOLD.contains(player)) {
                                if (LobbyUtil.LIST_QUEUE_NHD_GOLD.size() < 2) {
                                    LobbyUtil.removePlayerFromQueues(player);
                                    LobbyUtil.LIST_QUEUE_NHD_GOLD.add(player);
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Ranked-Warteschlange betreten.");
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 2);

                                    if (LobbyUtil.LIST_QUEUE_NHD_GOLD.size() == 2) {
                                        List<String> server = new ArrayList<>();
                                        for (ServerInfo info : CloudAPI.getInstance().getServers("RKITNHD")) {
                                            if (!info.getServerState().equals(ServerState.INGAME) && !info.getMotd().equalsIgnoreCase("Lade")) {
                                                server.add(info.getServiceId().getServerId());
                                            }
                                        }

                                        if (server.isEmpty()) {
                                            LobbyUtil.LIST_QUEUE_NHD_GOLD.forEach(sgQueuePlayer -> {
                                                sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Es wurde kein freier GameServer gefunden für dieses Kit.");
                                            });
                                            return;
                                        }

                                        String gameServer = server.get(new Random().nextInt(server.size()));

                                        LobbyUtil.LIST_QUEUE_NHD_GOLD.forEach(sgQueuePlayer -> {
                                            LobbyUtil.JOINING_SERVER.add(sgQueuePlayer);
                                            sgQueuePlayer.sendMessage(GlobalUtil.PREFIX + "§7Server gefunden! Sende auf §9" + gameServer);
                                            ServerAPI.sendPlayer(sgQueuePlayer.getUniqueId(), gameServer);
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(Kit1vs1.getInstance(), () -> {
                                                LobbyUtil.JOINING_SERVER.remove(sgQueuePlayer);
                                                LobbyUtil.LIST_QUEUE_NHD_GOLD.remove(sgQueuePlayer);
                                            }, 5 * 20L);
                                        });
                                    }

                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Derzeitig ist die Warteschlange voll. Bitte versuche es erneut.");
                                }
                            } else {
                                if (!LobbyUtil.JOINING_SERVER.contains(player)) {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Du hast die Warteschlange verlassen.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                    LobbyUtil.LIST_QUEUE_NHD_GOLD.remove(player);
                                } else {
                                    player.sendMessage(GlobalUtil.PREFIX + "§7Bitte warte einen Moment.");
                                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2, 2);
                                }
                                return;
                            }
                        }
                    }
                }
                return;
            }
        }
    }

}
