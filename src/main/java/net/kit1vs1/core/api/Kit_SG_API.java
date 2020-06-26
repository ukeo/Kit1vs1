package net.kit1vs1.core.api;

import net.kit1vs1.driver.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Kit_SG_API {

    public boolean playerExists(UUID uuid) {
        ResultSet rs = MySQL.getResult("SELECT UUID FROM KIT_SG WHERE UUID='" + uuid + "'");
        try {
            while (rs.next()) {
                if (rs != null) {
                    return true;
                }
            }
            rs.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return false;
    }

    public void createPlayer(UUID uuid) {
        if (!playerExists(uuid)) {
            MySQL.update("INSERT INTO KIT_SG (UUID, KILLS, DEATHS, ELO, WINS, RANK_WINS, STILL_RANKING) VALUES ('" + uuid + "', 0, 0, 0, 0, 0, 'true');");
        }
    }

    public int getInt(UUID uuid, String tableName, String columnName) {
        int i = 0;
        if (playerExists(uuid)) {
            ResultSet rs = MySQL.getResult("SELECT * FROM " + tableName + " WHERE UUID='" + uuid + "'");

            try {
                if (rs.next()) {
                    rs.getInt(columnName);
                }
                i = rs.getInt(columnName);
                rs.close();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        } else {
            createPlayer(uuid);
            getInt(uuid, tableName, columnName);
        }
        return i;
    }

    public void setInt(UUID uuid, String tableName, String columnName, Integer i) {
        if (playerExists(uuid)) {
            MySQL.update("UPDATE " + tableName + " SET " + columnName + "='" + i + "' WHERE UUID='" + uuid + "'");
        } else {
            createPlayer(uuid);
            setInt(uuid, tableName, columnName,i);
        }
    }

    public void addInt(UUID uuid, String tableName, String columnName, Integer i) {
        if (playerExists(uuid)) {
            setInt(uuid, tableName, columnName, getInt(uuid, tableName, columnName) + i);
        } else {
            createPlayer(uuid);
            addInt(uuid, tableName, columnName, i);
        }
    }

    public void removeInt(UUID uuid, String tableName, String columnName, Integer i) {
        if (playerExists(uuid)) {
            if (getInt(uuid, tableName, columnName) >= 1) {
                setInt(uuid, tableName, columnName, getInt(uuid, tableName, columnName) - i);
            }
        } else {
            createPlayer(uuid);
            removeInt(uuid, tableName, columnName, i);
        }
    }

    public boolean getBoolean(UUID uuid, String tableName, String columnName) {
        boolean b = true;
        if (playerExists(uuid)) {
            ResultSet rs = MySQL.getResult("SELECT * FROM " + tableName + " WHERE UUID='" + uuid + "'");

            try {
                if (rs.next()) {
                    rs.getBoolean(columnName);
                }
                b = rs.getBoolean(columnName);
                rs.close();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        } else {
            createPlayer(uuid);
            getBoolean(uuid, tableName, columnName);
        }
        return b;
    }

    public void setBoolean(UUID uuid, String tableName, String columnName, boolean b) {
        if (playerExists(uuid)) {
            MySQL.update("UPDATE " + tableName + " SET " + columnName + "='" + b + "' WHERE UUID='" + uuid + "'");
        } else {
            createPlayer(uuid);
            setBoolean(uuid, tableName, columnName, b);
        }
    }

    public void deleteData() {
        MySQL.update("DELETE FROM KIT_SG");
    }

}
