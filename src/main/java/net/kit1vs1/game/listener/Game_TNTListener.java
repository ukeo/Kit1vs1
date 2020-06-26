package net.kit1vs1.game.listener;

import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.gamehandler.GameState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Game_TNTListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            if (block.getType().equals(Material.TNT)) {
                block.setType(Material.AIR);
                TNTPrimed tnt = block.getLocation().getWorld().spawn(block.getLocation(), TNTPrimed.class);
                tnt.setFuseTicks(60);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

}
