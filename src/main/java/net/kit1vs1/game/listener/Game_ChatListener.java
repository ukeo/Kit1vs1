package net.kit1vs1.game.listener;

import net.core.api.CoinsAPI;
import net.kit1vs1.core.GlobalUtil;
import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.api.GameUtil;
import net.kit1vs1.game.gamehandler.GameState;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Game_ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        for (String all : Kit1vs1.GG) {
            if (event.getMessage().toLowerCase().contains(all) && !GameUtil.WROTE_GG.contains(player) && Kit1vs1.getInstance().getGameState().equals(GameState.ENDING)) {
                GameUtil.WROTE_GG.add(player);
                CoinsAPI.addCoinsByUUID(player.getUniqueId(), 20);
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                player.sendMessage(GlobalUtil.PREFIX + "§a+§e20 §7Coins");
            }
        }
    }

}
