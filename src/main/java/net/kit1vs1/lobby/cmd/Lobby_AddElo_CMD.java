package net.kit1vs1.lobby.cmd;

import net.core.uuid.UUIDFetcher;
import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.core.Kit1vs1;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Lobby_AddElo_CMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("kit1vs1.addelo")) {
                if (args.length == 1) {
                    player.sendMessage(GlobalUtil.PREFIX + "§7Nutze: /addelo [player] [elo]");
                }
                if (args.length == 2) {
                    try {
                        UUID uuid = UUIDFetcher.getUUID(args[0]);
                        Kit1vs1.getInstance().getKitSgApi().addInt(uuid, "KIT_SG", "ELO", Integer.valueOf(args[1]));
                        Kit1vs1.getInstance().getKitSgApi().setBoolean(uuid, "KIT_SG", "STILL_RANKING", false);
                    } catch (NumberFormatException exc) {
                        player.sendMessage(GlobalUtil.PREFIX + "§7Das ist keine Zahl.");
                    }
                }
            }
        }
        return true;
    }
}
