package net.kit1vs1.driver;

import net.kit1vs1.core.Kit1vs1;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MySQL {

    public static String host;
    public static String user;
    public static String password;
    public static String database;
    public static String port;
    public static Connection con;
    private static RequestQueue requestQ;
    private static int MySQLSchedulerID;
    public MySQL()
    {
        requestQ = new RequestQueue();
        requestQ.setRunning(true);
    }
    public static Connection getConnection() {
        return con;
    }
    public static void connect() {
        if (con == null)
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + password + "&autoReconnect=true");
            } catch (SQLException | ClassNotFoundException e) {
            }
    }
    public static void close()
    {
        if (con != null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    public static void update(String qry) {
        if (isConnected()) {
            new FutureTask<>(new Runnable() {
                @Override
                public void run() {
                    requestQ.addToQueue(qry);
                }
            }, 1).run();
        } else {
            connect();
        }
    }

    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS KIT_SG (UUID VARCHAR(100), KILLS int, DEATHS int, ELO int, WINS int, RANK_WINS int, STILL_RANKING VARCHAR(100))");
        update("CREATE TABLE IF NOT EXISTS KIT_UHC (UUID VARCHAR(100), KILLS int, DEATHS int, ELO int, WINS int, RANK_WINS int, STILL_RANKING VARCHAR(100))");
        update("CREATE TABLE IF NOT EXISTS KIT_NHD (UUID VARCHAR(100), KILLS int, DEATHS int, ELO int, WINS int, RANK_WINS int, STILL_RANKING VARCHAR(100))");
    }

    public static java.io.File getMySQLFile() {
        return new java.io.File("plugins/Kit1vs1", "MySQL.yml");
    }

    public static FileConfiguration getMySQLFileConfiguration() {
        return org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(getMySQLFile());
    }

    public static void setStandardMySQL() {
        FileConfiguration cfg = getMySQLFileConfiguration();

        cfg.options().copyDefaults(true);
        cfg.addDefault("username", "root");
        cfg.addDefault("password", "password");
        cfg.addDefault("database", "localhost");
        cfg.addDefault("host", "localhost");
        cfg.addDefault("port", "3306");
        try
        {
            cfg.save(getMySQLFile());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean isConnected()
    {
        return con != null;
    }

    public static void readMySQL() {
        FileConfiguration cfg = getMySQLFileConfiguration();
        user = cfg.getString("username");
        password = cfg.getString("password");
        database = cfg.getString("database");
        host = cfg.getString("host");
        port = cfg.getString("port");
    }

    public static void reconnectScheduler() {

        MySQLSchedulerID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Kit1vs1.getInstance(), () -> {

            onReconnect();

        }, 20L * 60L * 60L * 6L, 20L * 60L * 60L * 6);

    }

    public static void onReconnect() {

        if (con != null) {
            try {
                con.close();
                System.out.println("[CoinsSQL] Disconnected from current backend!");
            } catch (SQLException exc) {
                System.out.println("[CoinsSQL] Could not disconnect from current backend!");
            }
        }

        Bukkit.getScheduler().scheduleAsyncDelayedTask(Kit1vs1.getInstance(), () -> {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + password + "&autoReconnect=true");
                System.out.println("[Kit1vs1] MySQL verbunden");
            } catch (SQLException exc) {
                exc.printStackTrace();
                System.out.println("[Kit1vs1] Could not connect do default backend!");
            }
        }, 1L);

    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                System.out.println("[MySQL] Disconnected!");

                if (Bukkit.getScheduler().isCurrentlyRunning(MySQLSchedulerID)) {
                    Bukkit.getScheduler().cancelTask(MySQLSchedulerID);
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }

    public static ResultSet getResult(String qry) {
        if(isConnected()) {
            try {
                final FutureTask<ResultSet> task = new FutureTask<ResultSet>(new Callable<ResultSet>() {

                    PreparedStatement ps;

                    @Override
                    public ResultSet call() throws Exception {
                        ps = con.prepareStatement(qry);

                        return ps.executeQuery();
                    }
                });

                task.run();

                return task.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            connect();
        }

        return null;
    }

}
