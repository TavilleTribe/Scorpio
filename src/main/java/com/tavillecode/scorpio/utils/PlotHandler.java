package com.tavillecode.scorpio.utils;

import com.tavillecode.scorpio.Scorpio;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


/**
 * @author Interface39
 * @version 1.0
 * @description: Sqlite reader and writer(static utilities)
 * @date 2024/10/6 16:50
 */
public class PlotHandler {
    public static int getCurrentPlot(Player player,String npc) throws ExecutionException, InterruptedException {
        //CompletableFuture<Integer> asyncGetter = CompletableFuture.supplyAsync(() -> {
            String sql = String.format("select * from Citizens_%s where Player = '%s';",npc,player.getName());
            try {
                return Scorpio.getDatabase().executeQuery(sql, rs -> rs.getInt("Stage"));
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        //});
        //return asyncGetter.get();
    }

    public static void setPlotFirstTime(Player player,String npc,int stage){
        Bukkit.getScheduler().runTaskAsynchronously(Scorpio.getInstance(),() -> {
            String sql = String.format("insert into Citizens_%s (Player,Stage) values ('%s','%d');",npc,player.getName(),stage);
            try {
                Scorpio.getDatabase().executeUpdate(sql);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void updatePlot(Player player,String npc,int stage) {
        //Bukkit.getScheduler().runTaskAsynchronously(AdvancedInteract.getInstance(),() -> {
           String sql = String.format("update Citizens_%s set Stage = '%d' where Player = '%s';",npc,stage,player.getName());
            try {
                Scorpio.getDatabase().executeUpdate(sql);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        //});
    }

    public static void updatePlotAsync() {

    }
}
