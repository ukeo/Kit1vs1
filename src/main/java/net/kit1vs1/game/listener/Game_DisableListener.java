package net.kit1vs1.game.listener;

import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.gamehandler.GameState;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Game_DisableListener implements Listener {

    @EventHandler
    public void onBuild(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if (!Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (!event.getBlock().getType().equals(Material.WEB)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (!Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            event.setCancelled(true);
        }
    }

    /**@EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
    event.setCancelled(true);
    }
     */

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCycle(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(!Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        if (!Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.PHYSICAL) {
            if (e.getClickedBlock().getType() == Material.SOIL) {
                e.setCancelled(true);

            }
        }

    }

    @EventHandler
    public void toggle(PlayerToggleFlightEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE) || !event.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract3(PlayerInteractEvent event) {

        if (!Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            event.setCancelled(true);
        }

    }

    /**@EventHandler
    public void onMove(PlayerMoveEvent event) {

    if (SkyWars.getGameState().equals(GameState.INGAME)) {
    if (!Utils.LIVING_PLAYERS.contains(event.getPlayer())) {
    event.getPlayer().setGameMode(GameMode.SPECTATOR);
    }
    }

    if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE) || !event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
    event.getPlayer().setAllowFlight(false);
    event.getPlayer().setFlying(false);
    } else {
    event.getPlayer().setAllowFlight(true);
    event.getPlayer().setFlying(false);
    }
    }*/

    @EventHandler
    public void onPickUp2(PlayerPickupItemEvent event) {
        if (!Kit1vs1.getInstance().getGameState().equals(GameState.INGAME)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void AnimalSpiritEndermanNoPearlDamage(PlayerTeleportEvent event){
        Player p = event.getPlayer();
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL){
            event.setCancelled(true);
            p.setFallDistance(0F);
            p.setNoDamageTicks(1);
            p.teleport(event.getTo());
        }
    }

}
