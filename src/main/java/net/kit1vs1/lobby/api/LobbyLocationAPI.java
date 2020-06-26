package net.kit1vs1.lobby.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LobbyLocationAPI {

    public static File f = new File("plugins//Kit1vs1", "locations.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration((File)f);

    public static void saveLocation(String path, Location loc) {
        cfg.set(String.valueOf(path) + ".X", (Object)loc.getX());
        cfg.set(String.valueOf(path) + ".Y", (Object)loc.getY());
        cfg.set(String.valueOf(path) + ".Z", (Object)loc.getZ());
        cfg.set(String.valueOf(path) + ".Yaw", (Object)Float.valueOf(loc.getYaw()));
        cfg.set(String.valueOf(path) + ".Pitch", (Object)Float.valueOf(loc.getPitch()));
        cfg.set(String.valueOf(path) + ".World", (Object)loc.getWorld().getName());
        try {
            cfg.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getLocation(String path) {
        String world = cfg.getString(String.valueOf(path) + ".World");
        double x = cfg.getDouble(String.valueOf(path) + ".X");
        double y = cfg.getDouble(String.valueOf(path) + ".Y");
        double z = cfg.getDouble(String.valueOf(path) + ".Z");
        double yaw = cfg.getDouble(String.valueOf(path) + ".Yaw");
        double pitch = cfg.getDouble(String.valueOf(path) + ".Pitch");
        Location loc = new Location(Bukkit.getWorld((String)world), x, y, z);
        loc.setYaw((float)yaw);
        loc.setPitch((float)pitch);
        return loc;
    }

}
