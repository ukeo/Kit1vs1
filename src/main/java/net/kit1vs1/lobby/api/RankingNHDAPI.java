package net.kit1vs1.lobby.api;

import net.core.uuid.UUIDFetcher;
import net.kit1vs1.core.Kit1vs1;
import net.kit1vs1.driver.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RankingNHDAPI {

    public static HashMap<Integer, String> map = new HashMap();

    public static void loadRanking() {
        ResultSet rs = null;
        String n = "NORTH";
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT UUID FROM KIT_NHD ORDER BY RANK_WINS DESC LIMIT 3");
            rs = ps.executeQuery();
            int zahl = 0;
            while (rs.next()) {
                if (!Kit1vs1.getInstance().getKitNhdApi().getBoolean(UUID.fromString(rs.getString("UUID")), "KIT_NHD", "STILL_RANKING")) {
                    map.put(++zahl, rs.getString("UUID"));
                }
            }
            ArrayList<Location> locs = new ArrayList<Location>();
            locs.add(LobbyLocationAPI.getLocation("NhdHead1"));
            locs.add(LobbyLocationAPI.getLocation("NhdHead2"));
            locs.add(LobbyLocationAPI.getLocation("NhdHead3"));
            int i = 0;
            while (i < locs.size()) {
                int id = i + 1;
                ((Location)locs.get(i)).getBlock().setType(Material.SKULL);
                Skull s = (Skull)((Location)locs.get(i)).getBlock().getState();
                s.setSkullType(SkullType.PLAYER);
                boolean legit = true;
                String name = "???";
                try {
                    name = UUIDFetcher.getName(UUID.fromString(map.get(id)));
                    s.setOwner(name);
                    s.setRotation(BlockFace.valueOf(n.toUpperCase()));
                }
                catch (NullPointerException e) {
                    legit = false;
                    s.setOwner("MHF_Question");
                    s.setRotation(BlockFace.valueOf(n.toUpperCase()));
                    e.printStackTrace();
                }
                s.update();
                Location newloc = locs.get(i).add(0, -1, 0);
                if (newloc.getBlock().getState() instanceof Sign) {
                    BlockState b = newloc.getBlock().getState();
                    Sign sign = (Sign)b;
                    if (legit) {
                        double deaths = Kit1vs1.getInstance().getKitNhdApi().getInt(UUID.fromString(String.valueOf(map.get(id))), "KIT_NHD", "DEATHS");
                        double kills = Kit1vs1.getInstance().getKitNhdApi().getInt(UUID.fromString(String.valueOf(map.get(id))), "KIT_NHD", "KILLS");
                        double kd = 0;
                        if (deaths == 0) {
                            kd = kills + 0.00;
                            kd = Math.round(kd * 100.0D) / 100.D;
                        } else {
                            kd = kills / deaths;
                            kd = Math.round(kd * 100.0D) / 100.D;
                        }
                        sign.setLine(0, "- NHD Platz #" + id + " -");
                        sign.setLine(1, "\u00a70" + name);
                        sign.setLine(2, Kit1vs1.getInstance().getKitNhdApi().getInt(UUID.fromString(String.valueOf(map.get(id))), "KIT_NHD", "RANK_WINS") + " Gold-Wins");
                        sign.setLine(3, "\u00a70" + kd + " K/D");
                        sign.update();
                        if (sign.getLine(1).contains("null")) {
                            sign.setLine(1, "???");
                            sign.update();
                        }
                    } else {
                        sign.setLine(0, "- NHD Platz #" + id + " -");
                        sign.setLine(1, "\u00a70???");
                        sign.setLine(2, "0 Gold-Wins");
                        sign.setLine(3, "\u00a700 K/D");
                        sign.update();
                    }
                }
                ++i;
            }
        }
        catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("\u00a7cDie MySQL Verbindung wurde nicht aufgebaut, weswegen leider die Statswand nicht genutzt werden kann!");
            Bukkit.getConsoleSender().sendMessage("\u00a76>> " + e.getMessage());
            e.printStackTrace();
        }
    }

}
