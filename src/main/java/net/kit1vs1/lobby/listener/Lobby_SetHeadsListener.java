package net.kit1vs1.lobby.listener;

import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.lobby.api.LobbyLocationAPI;
import net.kit1vs1.lobby.api.LobbyUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Lobby_SetHeadsListener implements Listener {

    private static int counter = 0;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (LobbyUtil.SETUP_RANKING.contains(player)) {
            if (counter == 0) {
                LobbyLocationAPI.saveLocation("SgHead1", block.getLocation());
                counter++;
                player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Kopf 1 für SG gesetzt! Nun setze den Kopf 2.");
                return;
            }
            if (counter == 1) {
                LobbyLocationAPI.saveLocation("SgHead2", block.getLocation());
                counter++;
                player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Kopf 2 für SG gesetzt! Nun setze den Kopf 3.");
                return;
            }
            if (counter == 2) {
                LobbyLocationAPI.saveLocation("SgHead3", block.getLocation());
                counter++;
                player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Kopf 3 für SG gesetzt! Nun setze den Kopf 1 für UHC.");
                return;
            }
            if (counter == 3) {
                LobbyLocationAPI.saveLocation("UhcHead1", block.getLocation());
                counter++;
                player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Kopf 1 für UHC gesetzt! Nun setze den Kopf 2.");
                return;
            }
            if (counter == 4) {
                LobbyLocationAPI.saveLocation("UhcHead2", block.getLocation());
                counter++;
                player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Kopf 2 für UHC gesetzt! Nun setze den Kopf 3.");
                return;
            }
            if (counter == 5) {
                LobbyLocationAPI.saveLocation("UhcHead3", block.getLocation());
                counter++;
                player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Kopf 3 für UHC gesetzt! Nun setze den Kopf 1 für NHD.");
                return;
            }
            if (counter == 6) {
                LobbyLocationAPI.saveLocation("NhdHead1", block.getLocation());
                counter++;
                player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Kopf 1 für NHD gesetzt! Nun setze den Kopf 2.");
                return;
            }
            if (counter == 7) {
                LobbyLocationAPI.saveLocation("NhdHead2", block.getLocation());
                counter++;
                player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Kopf 2 für NHD gesetzt! Nun setze den Kopf 3.");
                return;
            }
            if (counter == 8) {
                LobbyLocationAPI.saveLocation("NhdHead3", block.getLocation());
                counter++;
                player.sendMessage(GlobalUtil.PREFIX + "§7Du hast den Kopf 3 für NHD gesetzt! Setup zuende.");
                LobbyUtil.SETUP_RANKING.remove(player);
                return;
            }
        }
    }

}
