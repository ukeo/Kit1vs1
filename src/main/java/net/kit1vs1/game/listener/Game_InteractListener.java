package net.kit1vs1.game.listener;

import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.gamehandler.GameState;
import net.kit1vs1.game.gamehandler.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Game_InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            if (Kit1vs1.getInstance().getKit().equals(Kit.NoHitDelay)) {
                player.setMaximumNoDamageTicks(2);
            }
        }
    }

}
