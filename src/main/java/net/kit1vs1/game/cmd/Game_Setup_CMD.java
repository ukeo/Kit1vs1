package net.kit1vs1.game.cmd;

import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.game.api.GameUtil;
import net.kit1vs1.lobby.api.LobbyLocationAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Game_Setup_CMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("kit1vs1.setup")) {
                if (args.length == 0) {
                    player.sendMessage(GlobalUtil.PREFIX + "§7Nutze: /setup (setSpawn1 (mapInt), setSpawn2 (mapInt))");
                    if (!GameUtil.SETUP.contains(player)) {
                        GameUtil.SETUP.add(player);
                        player.sendMessage(GlobalUtil.PREFIX + "§7Du bist nun im Setup.");
                        return true;
                    }
                    if (GameUtil.SETUP.contains(player)) {
                        GameUtil.SETUP.remove(player);
                        player.sendMessage(GlobalUtil.PREFIX + "§7Dz bist nun nicht mehr im Setup.");
                    }
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("setSpawn1")) {
                        LobbyLocationAPI.saveLocation("spawn1." + args[1], player.getLocation());
                        player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Spawn1 auf Map " + args[1] + " gespeichert!");
                    }
                    if (args[0].equalsIgnoreCase("setSpawn2")) {
                        LobbyLocationAPI.saveLocation("spawn2." + args[1], player.getLocation());
                        player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Spawn2 auf Map " + args[1] + " gespeichert!");
                    }
                }
            }
        }
        return true;
    }

}
