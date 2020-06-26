package net.kit1vs1.lobby.api;

import de.dytanic.cloudnet.api.CloudAPI;
import net.core.api.CoinsAPI;
import net.kit1vs1.core.GlobalUtil;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ScoreboardAPI {

    public static Scoreboard board = new Scoreboard();
    public static ScoreboardObjective obj = board.registerObjective("obj", IScoreboardCriteria.b);

    public static PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
    public static PacketPlayOutScoreboardObjective removepacket = new PacketPlayOutScoreboardObjective(obj, 1);
    public static PacketPlayOutScoreboardObjective createpacket = new PacketPlayOutScoreboardObjective(obj, 0);

    public static HashMap<Scoreboard , Player> boards = new HashMap<>();

    public static boolean init() {
        if (board == null) {
            return false;
        }
        if (obj == null) {
            return  false;
        }
        obj.setDisplayName("§3SparkleMC.de");
        return true;
    }

    public static void sendScoreboard(Player player) {

        display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        removepacket =  new PacketPlayOutScoreboardObjective(obj, 1);
        createpacket = new PacketPlayOutScoreboardObjective(obj, 0);

        try {

            ScoreboardScore s0 = new ScoreboardScore(board, obj, " ");
            ScoreboardScore s1 = new ScoreboardScore(board, obj, "§8» §7Season");
            ScoreboardScore s2 = new ScoreboardScore(board, obj, GlobalUtil.CURRENT_SEASON_DATE);
            ScoreboardScore s3 = new ScoreboardScore(board, obj, "  ");
            ScoreboardScore s4 = new ScoreboardScore(board, obj, "§8» §7Coins");
            ScoreboardScore s5 = new ScoreboardScore(board, obj, "§6" + CoinsAPI.getCoinsByUUID(player.getUniqueId()));
            ScoreboardScore s6 = new ScoreboardScore(board, obj, "   ");
            ScoreboardScore s7 = new ScoreboardScore(board, obj, "§8» §7Twitter");
            ScoreboardScore s8 = new ScoreboardScore(board, obj, "§b@SparkleMCde");
            ScoreboardScore s9 = new ScoreboardScore(board, obj, "    ");
            ScoreboardScore s10 = new ScoreboardScore(board, obj, "§8» §7TeamSpeak");
            ScoreboardScore s11 = new ScoreboardScore(board, obj, "§cSparkleMC.de");
            ScoreboardScore s12 = new ScoreboardScore(board, obj, "     ");

            s0.setScore(12);
            s1.setScore(11);
            s2.setScore(10);
            s3.setScore(9);
            s4.setScore(8);
            s5.setScore(7);
            s6.setScore(6);
            s7.setScore(5);
            s8.setScore(4);
            s9.setScore(3);
            s10.setScore(2);
            s11.setScore(1);
            s12.setScore(0);

            PacketPlayOutScoreboardScore ps1 = new PacketPlayOutScoreboardScore(s0);
            PacketPlayOutScoreboardScore ps2 = new PacketPlayOutScoreboardScore(s1);
            PacketPlayOutScoreboardScore ps3 = new PacketPlayOutScoreboardScore(s2);
            PacketPlayOutScoreboardScore ps4 = new PacketPlayOutScoreboardScore(s3);
            PacketPlayOutScoreboardScore ps5 = new PacketPlayOutScoreboardScore(s4);
            PacketPlayOutScoreboardScore ps6 = new PacketPlayOutScoreboardScore(s5);
            PacketPlayOutScoreboardScore ps7 = new PacketPlayOutScoreboardScore(s6);
            PacketPlayOutScoreboardScore ps8 = new PacketPlayOutScoreboardScore(s7);
            PacketPlayOutScoreboardScore ps9 = new PacketPlayOutScoreboardScore(s8);
            PacketPlayOutScoreboardScore ps10 = new PacketPlayOutScoreboardScore(s9);
            PacketPlayOutScoreboardScore ps11 = new PacketPlayOutScoreboardScore(s10);
            PacketPlayOutScoreboardScore ps12 = new PacketPlayOutScoreboardScore(s11);
            PacketPlayOutScoreboardScore ps13 = new PacketPlayOutScoreboardScore(s12);

            sendPacket(player, removepacket);
            sendPacket(player, createpacket);
            sendPacket(player, display);

            sendPacket(player, ps1);
            sendPacket(player, ps2);
            sendPacket(player, ps3);
            sendPacket(player, ps4);
            sendPacket(player, ps5);
            sendPacket(player, ps6);
            sendPacket(player, ps7);
            sendPacket(player, ps8);
            sendPacket(player, ps9);
            sendPacket(player, ps10);
            sendPacket(player, ps11);
            sendPacket(player, ps12);
            sendPacket(player, ps13);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

    }

    public static void sendScoreboardUnranked(Player player) {

        display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        removepacket =  new PacketPlayOutScoreboardObjective(obj, 1);
        createpacket = new PacketPlayOutScoreboardObjective(obj, 0);

        try {

            ScoreboardScore s0 = new ScoreboardScore(board, obj, " ");
            ScoreboardScore s1 = new ScoreboardScore(board, obj, "§8» §7Season");
            ScoreboardScore s2 = new ScoreboardScore(board, obj, GlobalUtil.CURRENT_SEASON_DATE);
            ScoreboardScore s3 = new ScoreboardScore(board, obj, "  ");
            ScoreboardScore s4 = new ScoreboardScore(board, obj, "§8» §7Rank");
            ScoreboardScore s5 = new ScoreboardScore(board, obj, "§7§oUnranked");
            ScoreboardScore s6 = new ScoreboardScore(board, obj, "   ");
            ScoreboardScore s7 = new ScoreboardScore(board, obj, "§8» §7Twitter");
            ScoreboardScore s8 = new ScoreboardScore(board, obj, "§b@SparkleMCde");
            ScoreboardScore s9 = new ScoreboardScore(board, obj, "    ");
            ScoreboardScore s10 = new ScoreboardScore(board, obj, "§8» §7TeamSpeak");
            ScoreboardScore s11 = new ScoreboardScore(board, obj, "§cSparkleMC.de");
            ScoreboardScore s12 = new ScoreboardScore(board, obj, "     ");

            s0.setScore(12);
            s1.setScore(11);
            s2.setScore(10);
            s3.setScore(9);
            s4.setScore(8);
            s5.setScore(7);
            s6.setScore(6);
            s7.setScore(5);
            s8.setScore(4);
            s9.setScore(3);
            s10.setScore(2);
            s11.setScore(1);
            s12.setScore(0);

            PacketPlayOutScoreboardScore ps1 = new PacketPlayOutScoreboardScore(s0);
            PacketPlayOutScoreboardScore ps2 = new PacketPlayOutScoreboardScore(s1);
            PacketPlayOutScoreboardScore ps3 = new PacketPlayOutScoreboardScore(s2);
            PacketPlayOutScoreboardScore ps4 = new PacketPlayOutScoreboardScore(s3);
            PacketPlayOutScoreboardScore ps5 = new PacketPlayOutScoreboardScore(s4);
            PacketPlayOutScoreboardScore ps6 = new PacketPlayOutScoreboardScore(s5);
            PacketPlayOutScoreboardScore ps7 = new PacketPlayOutScoreboardScore(s6);
            PacketPlayOutScoreboardScore ps8 = new PacketPlayOutScoreboardScore(s7);
            PacketPlayOutScoreboardScore ps9 = new PacketPlayOutScoreboardScore(s8);
            PacketPlayOutScoreboardScore ps10 = new PacketPlayOutScoreboardScore(s9);
            PacketPlayOutScoreboardScore ps11 = new PacketPlayOutScoreboardScore(s10);
            PacketPlayOutScoreboardScore ps12 = new PacketPlayOutScoreboardScore(s11);
            PacketPlayOutScoreboardScore ps13 = new PacketPlayOutScoreboardScore(s12);

            sendPacket(player, removepacket);
            sendPacket(player, createpacket);
            sendPacket(player, display);

            sendPacket(player, ps1);
            sendPacket(player, ps2);
            sendPacket(player, ps3);
            sendPacket(player, ps4);
            sendPacket(player, ps5);
            sendPacket(player, ps6);
            sendPacket(player, ps7);
            sendPacket(player, ps8);
            sendPacket(player, ps9);
            sendPacket(player, ps10);
            sendPacket(player, ps11);
            sendPacket(player, ps12);
            sendPacket(player, ps13);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

    }

    public static void sendScoreboardBronze(Player player) {

        display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        removepacket =  new PacketPlayOutScoreboardObjective(obj, 1);
        createpacket = new PacketPlayOutScoreboardObjective(obj, 0);

        try {

            ScoreboardScore s0 = new ScoreboardScore(board, obj, " ");
            ScoreboardScore s1 = new ScoreboardScore(board, obj, "§8» §7Season");
            ScoreboardScore s2 = new ScoreboardScore(board, obj, GlobalUtil.CURRENT_SEASON_DATE);
            ScoreboardScore s3 = new ScoreboardScore(board, obj, "  ");
            ScoreboardScore s4 = new ScoreboardScore(board, obj, "§8» §7Rank");
            ScoreboardScore s5 = new ScoreboardScore(board, obj, "§cBronze");
            ScoreboardScore s6 = new ScoreboardScore(board, obj, "   ");
            ScoreboardScore s7 = new ScoreboardScore(board, obj, "§8» §7Twitter");
            ScoreboardScore s8 = new ScoreboardScore(board, obj, "§b@SparkleMCde");
            ScoreboardScore s9 = new ScoreboardScore(board, obj, "    ");
            ScoreboardScore s10 = new ScoreboardScore(board, obj, "§8» §7TeamSpeak");
            ScoreboardScore s11 = new ScoreboardScore(board, obj, "§cSparkleMC.de");
            ScoreboardScore s12 = new ScoreboardScore(board, obj, "     ");

            s0.setScore(12);
            s1.setScore(11);
            s2.setScore(10);
            s3.setScore(9);
            s4.setScore(8);
            s5.setScore(7);
            s6.setScore(6);
            s7.setScore(5);
            s8.setScore(4);
            s9.setScore(3);
            s10.setScore(2);
            s11.setScore(1);
            s12.setScore(0);

            PacketPlayOutScoreboardScore ps1 = new PacketPlayOutScoreboardScore(s0);
            PacketPlayOutScoreboardScore ps2 = new PacketPlayOutScoreboardScore(s1);
            PacketPlayOutScoreboardScore ps3 = new PacketPlayOutScoreboardScore(s2);
            PacketPlayOutScoreboardScore ps4 = new PacketPlayOutScoreboardScore(s3);
            PacketPlayOutScoreboardScore ps5 = new PacketPlayOutScoreboardScore(s4);
            PacketPlayOutScoreboardScore ps6 = new PacketPlayOutScoreboardScore(s5);
            PacketPlayOutScoreboardScore ps7 = new PacketPlayOutScoreboardScore(s6);
            PacketPlayOutScoreboardScore ps8 = new PacketPlayOutScoreboardScore(s7);
            PacketPlayOutScoreboardScore ps9 = new PacketPlayOutScoreboardScore(s8);
            PacketPlayOutScoreboardScore ps10 = new PacketPlayOutScoreboardScore(s9);
            PacketPlayOutScoreboardScore ps11 = new PacketPlayOutScoreboardScore(s10);
            PacketPlayOutScoreboardScore ps12 = new PacketPlayOutScoreboardScore(s11);
            PacketPlayOutScoreboardScore ps13 = new PacketPlayOutScoreboardScore(s12);

            sendPacket(player, removepacket);
            sendPacket(player, createpacket);
            sendPacket(player, display);

            sendPacket(player, ps1);
            sendPacket(player, ps2);
            sendPacket(player, ps3);
            sendPacket(player, ps4);
            sendPacket(player, ps5);
            sendPacket(player, ps6);
            sendPacket(player, ps7);
            sendPacket(player, ps8);
            sendPacket(player, ps9);
            sendPacket(player, ps10);
            sendPacket(player, ps11);
            sendPacket(player, ps12);
            sendPacket(player, ps13);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

    }

    public static void sendScoreboardIron(Player player) {

        display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        removepacket =  new PacketPlayOutScoreboardObjective(obj, 1);
        createpacket = new PacketPlayOutScoreboardObjective(obj, 0);

        try {

            ScoreboardScore s0 = new ScoreboardScore(board, obj, " ");
            ScoreboardScore s1 = new ScoreboardScore(board, obj, "§8» §7Season");
            ScoreboardScore s2 = new ScoreboardScore(board, obj, GlobalUtil.CURRENT_SEASON_DATE);
            ScoreboardScore s3 = new ScoreboardScore(board, obj, "  ");
            ScoreboardScore s4 = new ScoreboardScore(board, obj, "§8» §7Rank");
            ScoreboardScore s5 = new ScoreboardScore(board, obj, "§7Iron");
            ScoreboardScore s6 = new ScoreboardScore(board, obj, "   ");
            ScoreboardScore s7 = new ScoreboardScore(board, obj, "§8» §7Twitter");
            ScoreboardScore s8 = new ScoreboardScore(board, obj, "§b@SparkleMCde");
            ScoreboardScore s9 = new ScoreboardScore(board, obj, "    ");
            ScoreboardScore s10 = new ScoreboardScore(board, obj, "§8» §7TeamSpeak");
            ScoreboardScore s11 = new ScoreboardScore(board, obj, "§cSparkleMC.de");
            ScoreboardScore s12 = new ScoreboardScore(board, obj, "     ");

            s0.setScore(12);
            s1.setScore(11);
            s2.setScore(10);
            s3.setScore(9);
            s4.setScore(8);
            s5.setScore(7);
            s6.setScore(6);
            s7.setScore(5);
            s8.setScore(4);
            s9.setScore(3);
            s10.setScore(2);
            s11.setScore(1);
            s12.setScore(0);

            PacketPlayOutScoreboardScore ps1 = new PacketPlayOutScoreboardScore(s0);
            PacketPlayOutScoreboardScore ps2 = new PacketPlayOutScoreboardScore(s1);
            PacketPlayOutScoreboardScore ps3 = new PacketPlayOutScoreboardScore(s2);
            PacketPlayOutScoreboardScore ps4 = new PacketPlayOutScoreboardScore(s3);
            PacketPlayOutScoreboardScore ps5 = new PacketPlayOutScoreboardScore(s4);
            PacketPlayOutScoreboardScore ps6 = new PacketPlayOutScoreboardScore(s5);
            PacketPlayOutScoreboardScore ps7 = new PacketPlayOutScoreboardScore(s6);
            PacketPlayOutScoreboardScore ps8 = new PacketPlayOutScoreboardScore(s7);
            PacketPlayOutScoreboardScore ps9 = new PacketPlayOutScoreboardScore(s8);
            PacketPlayOutScoreboardScore ps10 = new PacketPlayOutScoreboardScore(s9);
            PacketPlayOutScoreboardScore ps11 = new PacketPlayOutScoreboardScore(s10);
            PacketPlayOutScoreboardScore ps12 = new PacketPlayOutScoreboardScore(s11);
            PacketPlayOutScoreboardScore ps13 = new PacketPlayOutScoreboardScore(s12);

            sendPacket(player, removepacket);
            sendPacket(player, createpacket);
            sendPacket(player, display);

            sendPacket(player, ps1);
            sendPacket(player, ps2);
            sendPacket(player, ps3);
            sendPacket(player, ps4);
            sendPacket(player, ps5);
            sendPacket(player, ps6);
            sendPacket(player, ps7);
            sendPacket(player, ps8);
            sendPacket(player, ps9);
            sendPacket(player, ps10);
            sendPacket(player, ps11);
            sendPacket(player, ps12);
            sendPacket(player, ps13);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

    }

    public static void sendScoreboardGold(Player player) {

        display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        removepacket =  new PacketPlayOutScoreboardObjective(obj, 1);
        createpacket = new PacketPlayOutScoreboardObjective(obj, 0);

        try {

            ScoreboardScore s0 = new ScoreboardScore(board, obj, " ");
            ScoreboardScore s1 = new ScoreboardScore(board, obj, "§8» §7Season");
            ScoreboardScore s2 = new ScoreboardScore(board, obj, GlobalUtil.CURRENT_SEASON_DATE);
            ScoreboardScore s3 = new ScoreboardScore(board, obj, "  ");
            ScoreboardScore s4 = new ScoreboardScore(board, obj, "§8» §7Rank");
            ScoreboardScore s5 = new ScoreboardScore(board, obj, "§6Gold");
            ScoreboardScore s6 = new ScoreboardScore(board, obj, "   ");
            ScoreboardScore s7 = new ScoreboardScore(board, obj, "§8» §7Twitter");
            ScoreboardScore s8 = new ScoreboardScore(board, obj, "§b@SparkleMCde");
            ScoreboardScore s9 = new ScoreboardScore(board, obj, "    ");
            ScoreboardScore s10 = new ScoreboardScore(board, obj, "§8» §7TeamSpeak");
            ScoreboardScore s11 = new ScoreboardScore(board, obj, "§cSparkleMC.de");
            ScoreboardScore s12 = new ScoreboardScore(board, obj, "     ");

            s0.setScore(12);
            s1.setScore(11);
            s2.setScore(10);
            s3.setScore(9);
            s4.setScore(8);
            s5.setScore(7);
            s6.setScore(6);
            s7.setScore(5);
            s8.setScore(4);
            s9.setScore(3);
            s10.setScore(2);
            s11.setScore(1);
            s12.setScore(0);

            PacketPlayOutScoreboardScore ps1 = new PacketPlayOutScoreboardScore(s0);
            PacketPlayOutScoreboardScore ps2 = new PacketPlayOutScoreboardScore(s1);
            PacketPlayOutScoreboardScore ps3 = new PacketPlayOutScoreboardScore(s2);
            PacketPlayOutScoreboardScore ps4 = new PacketPlayOutScoreboardScore(s3);
            PacketPlayOutScoreboardScore ps5 = new PacketPlayOutScoreboardScore(s4);
            PacketPlayOutScoreboardScore ps6 = new PacketPlayOutScoreboardScore(s5);
            PacketPlayOutScoreboardScore ps7 = new PacketPlayOutScoreboardScore(s6);
            PacketPlayOutScoreboardScore ps8 = new PacketPlayOutScoreboardScore(s7);
            PacketPlayOutScoreboardScore ps9 = new PacketPlayOutScoreboardScore(s8);
            PacketPlayOutScoreboardScore ps10 = new PacketPlayOutScoreboardScore(s9);
            PacketPlayOutScoreboardScore ps11 = new PacketPlayOutScoreboardScore(s10);
            PacketPlayOutScoreboardScore ps12 = new PacketPlayOutScoreboardScore(s11);
            PacketPlayOutScoreboardScore ps13 = new PacketPlayOutScoreboardScore(s12);

            sendPacket(player, removepacket);
            sendPacket(player, createpacket);
            sendPacket(player, display);

            sendPacket(player, ps1);
            sendPacket(player, ps2);
            sendPacket(player, ps3);
            sendPacket(player, ps4);
            sendPacket(player, ps5);
            sendPacket(player, ps6);
            sendPacket(player, ps7);
            sendPacket(player, ps8);
            sendPacket(player, ps9);
            sendPacket(player, ps10);
            sendPacket(player, ps11);
            sendPacket(player, ps12);
            sendPacket(player, ps13);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

    }

    public static void sendPacket(Player p, Packet<?> packet) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }

}
