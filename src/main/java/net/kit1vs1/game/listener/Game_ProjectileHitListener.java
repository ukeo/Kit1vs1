package net.kit1vs1.game.listener;

import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.gamehandler.GameState;
import net.kit1vs1.game.gamehandler.Kit;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Game_ProjectileHitListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            if (Kit1vs1.getInstance().getKit().equals(Kit.SG)) {
                if (event.getEntity() instanceof Arrow) {
                    Arrow arrow = (Arrow) event.getEntity();
                    arrow.remove();
                }
            }
        }
    }

}
