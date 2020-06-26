package net.kit1vs1.core;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class GlobalUtil {

    public static final String PREFIX = "§9§lKit1vs1 §8» ";
    public static boolean IS_RANKED;
    public static Date START_SEASON;
    public static Date END_SEASON;
    public static String CURRENT_SEASON_DATE;

    public static void checkIfSeasonExpired() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Kit1vs1.getInstance(), () -> {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyy");
            Date currentDate = new Date();

            System.out.println("[Kit1vs1] Checking Season time...");

            if (currentDate.after(END_SEASON)) {
                System.out.println("[Kit1vs1] Season ended! Changing season now...");

                Kit1vs1.getInstance().getConfig().set("Settings.seasonStart", format.format(currentDate));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.MONTH, 2);
                Date newSeasonEnd = calendar.getTime();
                Kit1vs1.getInstance().getConfig().set("Settings.seasonEnd", format.format(newSeasonEnd));
                System.out.println("[Kit1vs1] Changed season!");

                Kit1vs1.getInstance().saveConfig();

                Date seasonDate = new Date();
                LocalDate localDate = seasonDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();

                String seasonMonthStart = getMonthForInt(month);

                LocalDate localDate1 = newSeasonEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month2 = localDate1.getMonthValue();

                String seasonMonthEnd = getMonthForInt(month2);

                CURRENT_SEASON_DATE = "§e" + seasonMonthStart + " §7- §e" + seasonMonthEnd;

                File source = new File(Kit1vs1.getInstance().getDataFolder().getPath(), "config.yml");
                File destination = new File("/home/cloud/CloudNet-Wrapper/local/templates/Kit1vs1/default/plugins/Kit1vs1/", "config.yml");
                try {
                    FileUtils.copyFile(source, destination);
                } catch (IOException e) {
                    System.out.println("[Kit1vs1] Failed to copy conifg! Exception ->");
                    e.printStackTrace();
                }

                //TODO
                //Add auto deletion for Stats in database and restart all Kit-Lobby Server.
                Kit1vs1.getInstance().getKitSgApi().deleteData();
                Kit1vs1.getInstance().getKitUhcApi().deleteData();
                Kit1vs1.getInstance().getKitNhdApi().deleteData();

                Bukkit.shutdown();

            } else {
                System.out.println("[Kit1vs1] No season change found.");

                LocalDate localDate = START_SEASON.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                String seasonMonthStart = getMonthForInt(month);

                LocalDate localDate1 = END_SEASON.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month2 = localDate1.getMonthValue();
                String seasonMonthEnd = getMonthForInt(month2);

                CURRENT_SEASON_DATE = "§e" + seasonMonthStart + " §7- §e" + seasonMonthEnd;

            }

        }, 20L, 30 * 20L);

    }

    private static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

}
