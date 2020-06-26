package net.kit1vs1.game.listener;

import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.api.GameLocationAPI;
import net.kit1vs1.game.api.GameUtil;
import net.kit1vs1.game.gamehandler.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Game_MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!GameUtil.SETUP.contains(player)) {
            if (Kit1vs1.getInstance().getGameState().equals(GameState.PRE)) {
                if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
                    if (GameUtil.TEAM_1.contains(player)) {
                        player.teleport(GameLocationAPI.getLocation("spawn1." + GameUtil.MAP_INT));
                    }
                    if (GameUtil.TEAM_2.contains(player)) {
                        player.teleport(GameLocationAPI.getLocation("spawn2." + GameUtil.MAP_INT));
                    }
                }
            }
        }
    }

}
