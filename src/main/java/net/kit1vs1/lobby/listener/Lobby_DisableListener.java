package net.kit1vs1.lobby.listener;

import net.core.api.ParticleAPI;
import net.kit1vs1.lobby.api.LobbyLocationAPI;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Lobby_DisableListener implements Listener {

    @EventHandler
    public void onBuild(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    /**@EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
    event.setCancelled(true);
    }
     */

    @EventHandler
    public void ondamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onCycle(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(e.getAction() == Action.PHYSICAL) {
            if(e.getClickedBlock().getType() == Material.SOIL) {
                e.setCancelled(true);

            }
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (player.getLocation().getY() <= 1) {
            player.teleport(LobbyLocationAPI.getLocation("Lobby"));
        }

    }

    @EventHandler
    public void onInteractPet(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (!(event.getRightClicked() == null)) {
            if (!(event.getClickedPosition() == null)) {
                if (event.getRightClicked().getType().getEntityClass() == ArmorStand.class) {
                    event.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void onInteract3(PlayerInteractEvent event) {

        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }

    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onUnleash(PlayerUnleashEntityEvent entityEvent) {
        entityEvent.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void EntityChangeBlockEvent (EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.FALLING_BLOCK) {
            ParticleAPI particleAPI = new ParticleAPI(EnumParticle.CRIT, event.getEntity().getLocation(), true, 1, 1, 1, 1, 10);
            particleAPI.sendAll();
            event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.DIG_STONE, 1, 1);
            event.getEntity().remove();
            event.setCancelled(true);
        }
    }

}
