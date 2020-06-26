package net.kit1vs1.lobby.cmd;

import net.core.api.StatsAPI;
import net.core.uuid.UUIDFetcher;
import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.core.Kit1vs1;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Lobby_Stats_CMD implements CommandExecutor {

    private HashMap<String, Long> tcd = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            Long time = Long.valueOf(System.currentTimeMillis());
            if (this.tcd.containsKey(player.getName())) {
                Long lastUse = this.tcd.get(player.getName());
                if (lastUse.longValue() + 5000L > time.longValue()) {
                    player.sendMessage(GlobalUtil.PREFIX + "§cBitte warte kurz, bevor du die Stats wieder benutzen kannst!");
                    return true;
                }
            }

            try {
                if (args.length == 0) {
                    player.sendMessage(GlobalUtil.PREFIX + "§7Nutze: /stats [SG/UHC/NHD] [Spieler(optional)]");
                }
                if (args.length == 1) {
                    String mode = args[0];
                    if (mode.equalsIgnoreCase("SG")) {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Lade Stats...");
                        Bukkit.getScheduler().runTaskAsynchronously(Kit1vs1.getInstance(), () -> {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Deine Season Stats für SG:");
                            player.sendMessage(" ");
                            player.sendMessage("§8» §7Kills§8: §a" + Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "KILLS"));
                            player.sendMessage("§8» §7Tode§8: §c" + Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "DEATHS"));
                            player.sendMessage("§8» §7Wins§8: §a" + Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "WINS"));
                            player.sendMessage("§8» §6Gold§7-Wins§8: §a" + Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "RANK_WINS"));
                            player.sendMessage("§8» §7Loses§8: §c" + Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "DEATHS"));
                            player.sendMessage("§8» §7Elo§8: §a" + Kit1vs1.getInstance().getKitSgApi().getInt(player.getUniqueId(), "KIT_SG", "ELO"));
                            player.sendMessage(" ");
                            this.tcd.put(player.getName(), time);
                        });
                    }
                    if (mode.equalsIgnoreCase("UHC")) {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Lade Stats...");
                        Bukkit.getScheduler().runTaskAsynchronously(Kit1vs1.getInstance(), () -> {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Deine Season Stats für UHC:");
                            player.sendMessage(" ");
                            player.sendMessage("§8» §7Kills§8: §a" + Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "KILLS"));
                            player.sendMessage("§8» §7Tode§8: §c" + Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "DEATHS"));
                            player.sendMessage("§8» §7Wins§8: §a" + Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "WINS"));
                            player.sendMessage("§8» §6Gold§7-Wins§8: §a" + Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "RANK_WINS"));
                            player.sendMessage("§8» §7Loses§8: §c" + Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "DEATHS"));
                            player.sendMessage("§8» §7Elo§8: §a" + Kit1vs1.getInstance().getKitUhcApi().getInt(player.getUniqueId(), "KIT_UHC", "ELO"));
                            player.sendMessage(" ");
                            this.tcd.put(player.getName(), time);
                        });
                    }
                    if (mode.equalsIgnoreCase("NHD")) {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Lade Stats...");
                        Bukkit.getScheduler().runTaskAsynchronously(Kit1vs1.getInstance(), () -> {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Deine Season Stats für NHD:");
                            player.sendMessage(" ");
                            player.sendMessage("§8» §7Kills§8: §a" + Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "KILLS"));
                            player.sendMessage("§8» §7Tode§8: §c" + Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "DEATHS"));
                            player.sendMessage("§8» §7Wins§8: §a" + Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "WINS"));
                            player.sendMessage("§8» §6Gold§7-Wins§8: §a" + Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "RANK_WINS"));
                            player.sendMessage("§8» §7Loses§8: §c" + Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "DEATHS"));
                            player.sendMessage("§8» §7Elo§8: §a" + Kit1vs1.getInstance().getKitNhdApi().getInt(player.getUniqueId(), "KIT_NHD", "ELO"));
                            player.sendMessage(" ");
                            this.tcd.put(player.getName(), time);
                        });
                    }
                }
                if (args.length == 2) {
                    String mode = args[0];
                    UUID uuid = UUIDFetcher.getUUID(args[1]);
                    if (mode.equalsIgnoreCase("SG")) {
                        if (Kit1vs1.getInstance().getKitSgApi().playerExists(uuid)) {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Lade Stats von §9§l" + args[0] + "§7...");
                            Bukkit.getScheduler().runTaskAsynchronously(Kit1vs1.getInstance(), () -> {
                                player.sendMessage(GlobalUtil.PREFIX + "§7Season Stats für SG von §9§l" + args[0] + "§7:");
                                player.sendMessage(" ");
                                player.sendMessage("§8» §7Kills§8: §a" + Kit1vs1.getInstance().getKitSgApi().getInt(uuid, "KIT_SG", "KILLS"));
                                player.sendMessage("§8» §7Tode§8: §c" + Kit1vs1.getInstance().getKitSgApi().getInt(uuid, "KIT_SG", "DEATHS"));
                                player.sendMessage("§8» §7Wins§8: §a" + Kit1vs1.getInstance().getKitSgApi().getInt(uuid, "KIT_SG", "WINS"));
                                player.sendMessage("§8» §6Gold§7-Wins§8: §a" + Kit1vs1.getInstance().getKitSgApi().getInt(uuid, "KIT_SG", "RANK_WINS"));
                                player.sendMessage("§8» §7Loses§8: §c" + Kit1vs1.getInstance().getKitSgApi().getInt(uuid, "KIT_SG", "DEATHS"));
                                player.sendMessage("§8» §7Elo§8: §a" + Kit1vs1.getInstance().getKitSgApi().getInt(uuid, "KIT_SG", "ELO"));
                                player.sendMessage(" ");
                                this.tcd.put(player.getName(), time);
                            });
                        } else {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Dieser Spieler existiert nicht!");
                        }
                    }
                    if (mode.equalsIgnoreCase("UHC")) {
                        if (Kit1vs1.getInstance().getKitUhcApi().playerExists(uuid)) {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Lade Stats von §9§l" + args[0] + "§7...");
                            Bukkit.getScheduler().runTaskAsynchronously(Kit1vs1.getInstance(), () -> {
                                player.sendMessage(GlobalUtil.PREFIX + "§7Season Stats für UHC von §9§l" + args[0] + "§7:");
                                player.sendMessage(" ");
                                player.sendMessage("§8» §7Kills§8: §a" + Kit1vs1.getInstance().getKitUhcApi().getInt(uuid, "KIT_UHC", "KILLS"));
                                player.sendMessage("§8» §7Tode§8: §c" + Kit1vs1.getInstance().getKitUhcApi().getInt(uuid, "KIT_UHC", "DEATHS"));
                                player.sendMessage("§8» §7Wins§8: §a" + Kit1vs1.getInstance().getKitUhcApi().getInt(uuid, "KIT_UHC", "WINS"));
                                player.sendMessage("§8» §6Gold§7-Wins§8: §a" + Kit1vs1.getInstance().getKitUhcApi().getInt(uuid, "KIT_UHC", "RANK_WINS"));
                                player.sendMessage("§8» §7Loses§8: §c" + Kit1vs1.getInstance().getKitUhcApi().getInt(uuid, "KIT_UHC", "DEATHS"));
                                player.sendMessage("§8» §7Elo§8: §a" + Kit1vs1.getInstance().getKitUhcApi().getInt(uuid, "KIT_UHC", "ELO"));
                                player.sendMessage(" ");
                                this.tcd.put(player.getName(), time);
                            });
                        } else {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Dieser Spieler existiert nicht!");
                        }
                    }
                    if (mode.equalsIgnoreCase("NHD")) {
                        if (Kit1vs1.getInstance().getKitNhdApi().playerExists(uuid)) {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Lade Stats von §9§l" + args[0] + "§7...");
                            Bukkit.getScheduler().runTaskAsynchronously(Kit1vs1.getInstance(), () -> {
                                player.sendMessage(GlobalUtil.PREFIX + "§7Season Stats für NHD von §9§l" + args[0] + "§7:");
                                player.sendMessage(" ");
                                player.sendMessage("§8» §7Kills§8: §a" + Kit1vs1.getInstance().getKitNhdApi().getInt(uuid, "KIT_NHD", "KILLS"));
                                player.sendMessage("§8» §7Tode§8: §c" + Kit1vs1.getInstance().getKitNhdApi().getInt(uuid, "KIT_NHD", "DEATHS"));
                                player.sendMessage("§8» §7Wins§8: §a" + Kit1vs1.getInstance().getKitNhdApi().getInt(uuid, "KIT_NHD", "WINS"));
                                player.sendMessage("§8» §6Gold§7-Wins§8: §a" + Kit1vs1.getInstance().getKitNhdApi().getInt(uuid, "KIT_NHD", "RANK_WINS"));
                                player.sendMessage("§8» §7Loses§8: §c" + Kit1vs1.getInstance().getKitNhdApi().getInt(uuid, "KIT_NHD", "DEATHS"));
                                player.sendMessage("§8» §7Elo§8: §a" + Kit1vs1.getInstance().getKitNhdApi().getInt(uuid, "KIT_NHD", "ELO"));
                                player.sendMessage(" ");
                                this.tcd.put(player.getName(), time);
                            });
                        } else {
                            player.sendMessage(GlobalUtil.PREFIX + "§7Dieser Spieler existiert nicht!");
                        }
                    }
                }
            } catch (NullPointerException exc) {
                player.sendMessage(GlobalUtil.PREFIX + "§7Es gab einen Fehler beim auslesen der Daten!");
            }

        }
        return true;
    }

}
