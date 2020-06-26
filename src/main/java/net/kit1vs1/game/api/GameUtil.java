package net.kit1vs1.game.api;

import net.core.api.ItemBuilder;
import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.game.gamehandler.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameUtil {

    public static ArrayList<Player> TEAM_1 = new ArrayList<>();
    public static ArrayList<Player> TEAM_2 = new ArrayList<>();
    public static ArrayList<Player> WROTE_GG = new ArrayList<>();
    public static ArrayList<Player> SETUP = new ArrayList<>();

    public static String KIT = null;
    public static String MAP_NAME = null;

    public static int MAP_INT;
    public static int ENDING_SCHEDULER;
    public static int ENDING_TIMER = 6;
    public static int PRE_SCHEDULER;
    public static int PRE_TIMER = 10;

    public static final ItemStack HUB = new ItemBuilder(Material.MAGMA_CREAM).setName("§8» §7Zurück zur KitLobby").build();

    public static void setPlayerKit(Player player) {
        if (Kit1vs1.getInstance().getKit().equals(Kit.SG)) {
            ItemStack helmet = new ItemBuilder(Material.IRON_HELMET).build();
            ItemStack chestPlate = new ItemBuilder(Material.IRON_CHESTPLATE).build();
            ItemStack leggings = new ItemBuilder(Material.IRON_LEGGINGS).build();
            ItemStack boots = new ItemBuilder(Material.IRON_BOOTS).build();
            ItemStack arrows = new ItemBuilder(Material.ARROW).setAmount(5).build();
            ItemStack sword = new ItemBuilder(Material.IRON_SWORD).build();
            ItemStack rod = new ItemBuilder(Material.FISHING_ROD).build();
            ItemStack bow = new ItemBuilder(Material.BOW).build();
            ItemStack goldenApple = new ItemBuilder(Material.GOLDEN_APPLE).build();
            ItemStack steak = new ItemBuilder(Material.COOKED_BEEF).setAmount(5).build();
            ItemStack cobweb = new ItemBuilder(Material.WEB).setAmount(2).build();
            ItemStack tnt = new ItemBuilder(Material.TNT).setAmount(2).build();
            ItemStack flint = new ItemBuilder(Material.FLINT_AND_STEEL).setDurability((short) 60).build();

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestPlate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
            player.getInventory().setItem(9, arrows);
            player.getInventory().setItem(0, sword);
            player.getInventory().setItem(1, rod);
            player.getInventory().setItem(2, bow);
            player.getInventory().setItem(3, goldenApple);
            player.getInventory().setItem(5, steak);
            player.getInventory().setItem(6, cobweb);
            player.getInventory().setItem(7, tnt);
            player.getInventory().setItem(8, flint);
        }
        if (Kit1vs1.getInstance().getKit().equals(Kit.UHC)) {
            Map<Enchantment, Integer> enchantmentIntegerMap = new HashMap<>();
            enchantmentIntegerMap.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            enchantmentIntegerMap.put(Enchantment.PROTECTION_PROJECTILE, 2);
            ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
            ItemStack chestPlate = new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build();
            ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build();
            ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS).setEnchantments(enchantmentIntegerMap).build();
            ItemStack arrows = new ItemBuilder(Material.ARROW).setAmount(32).build();
            ItemStack wood = new ItemBuilder(Material.WOOD).setAmount(64).build();
            ItemStack lava = new ItemBuilder(Material.LAVA_BUCKET).setAmount(1).build();
            ItemStack water = new ItemBuilder(Material.WATER_BUCKET).setAmount(1).build();
            ItemStack pickAxe = new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 3).build();
            ItemStack axe = new ItemBuilder(Material.DIAMOND_AXE).addEnchantment(Enchantment.DIG_SPEED, 3).build();
            ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 3).build();
            ItemStack rod = new ItemBuilder(Material.FISHING_ROD).build();
            ItemStack bow = new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 3).build();
            ItemStack goldenApple = new ItemBuilder(Material.GOLDEN_APPLE).setAmount(6).build();
            ItemStack skull = new ItemBuilder(Material.GOLDEN_APPLE).setAmount(2).setName("§fSkull").build();
            ItemStack steak = new ItemBuilder(Material.COOKED_BEEF).setAmount(64).build();
            ItemStack cobble = new ItemBuilder(Material.COBBLESTONE).setAmount(64).build();
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestPlate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
            player.getInventory().setItem(9, arrows);
            player.getInventory().setItem(10, wood);
            player.getInventory().setItem(11, lava);
            player.getInventory().setItem(12, water);
            player.getInventory().setItem(14, pickAxe);
            player.getInventory().setItem(15, axe);
            player.getInventory().setItem(0, sword);
            player.getInventory().setItem(1, rod);
            player.getInventory().setItem(2, bow);
            player.getInventory().setItem(3, lava);
            player.getInventory().setItem(4, water);
            player.getInventory().setItem(5, goldenApple);
            player.getInventory().setItem(6, skull);
            player.getInventory().setItem(7, steak);
            player.getInventory().setItem(8, cobble);
        }
        if (Kit1vs1.getInstance().getKit().equals(Kit.NoHitDelay)) {
            Map<Enchantment, Integer> enchantmentIntegerMap = new HashMap<>();
            enchantmentIntegerMap.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
            enchantmentIntegerMap.put(Enchantment.PROTECTION_FALL, 3);
            Map<Enchantment, Integer> enchantmentIntegerMap2 = new HashMap<>();
            enchantmentIntegerMap2.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
            enchantmentIntegerMap2.put(Enchantment.DURABILITY, 5);
            ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET).setEnchantments(enchantmentIntegerMap2).build();
            ItemStack chestPlate = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setEnchantments(enchantmentIntegerMap2).build();
            ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS).setEnchantments(enchantmentIntegerMap2).build();
            ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS).setEnchantments(enchantmentIntegerMap).build();
            ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 5).build();
            ItemStack goldenApple = new ItemBuilder(new ItemStack(Material.GOLDEN_APPLE,64, (byte) 1)).build();
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestPlate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
            player.getInventory().setItem(0, sword);
            player.getInventory().setItem(1, goldenApple);
        }
    }

}
