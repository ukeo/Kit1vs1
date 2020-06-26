package net.kit1vs1.lobby.listener;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;
import de.dytanic.cloudnet.lib.utility.document.Document;
import net.core.cloud.ServerAPI;
import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.lobby.api.LobbyUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lobby_HubListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Document properties = CloudServer.getInstance().getServerConfig().getProperties();
        if (event.getItem() == null) return;
        if (event.getAction() == null) return;
        if (event.getItem().equals(LobbyUtil.HUB)) {
            player.sendMessage(GlobalUtil.PREFIX + "§7Sende auf die Lobby...");
            List<String> server = new ArrayList<>();
            for (ServerInfo info : CloudAPI.getInstance().getServers("Lobby")) {
                server.add(info.getServiceId().getServerId());
            }
            ServerAPI.sendPlayer(player.getUniqueId(), server.get(new Random().nextInt(server.size())));
        }

    }

}
