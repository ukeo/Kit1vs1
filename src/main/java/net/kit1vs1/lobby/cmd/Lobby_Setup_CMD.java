package net.kit1vs1.lobby.cmd;

import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.lobby.api.LobbyLocationAPI;
import net.kit1vs1.lobby.api.LobbyUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Lobby_Setup_CMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("kit1vs1.setup")) {
                if (args.length == 0) {
                    player.sendMessage(GlobalUtil.PREFIX + "§7Nutze: /setup (setLobby, setHolo, setHeads)");
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("setLobby")) {
                        LobbyLocationAPI.saveLocation("Lobby", player.getLocation());
                        player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Lobby Spawn gespeichert!");
                    }
                    if (args[0].equalsIgnoreCase("setHolo")) {
                        LobbyLocationAPI.saveLocation("Holo", player.getLocation());
                        player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Holo Spawn gespeichert!");
                    }
                    if (args[0].equalsIgnoreCase("setHeads")) {
                        LobbyUtil.SETUP_RANKING.add(player);
                        player.sendMessage(GlobalUtil.PREFIX + "§7Du kannst nun die Köpfe schlagen! Schlage nun die Köpfer für Kit SG!");
                    }
                }
            }
        }
        return true;
    }
}
