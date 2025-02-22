package com.tavillecode.scorpio;

import com.tavillecode.scorpio.commands.ScorpioExecutor;
import com.tavillecode.scorpio.listeners.PlayerHeldItemListener;
import com.tavillecode.scorpio.listeners.PlayerSwapItemListener;
import com.tavillecode.scorpio.listeners.citizens.RightClickNPCListener;
import com.tavillecode.scorpio.storage.impl.Configuration;
import com.tavillecode.scorpio.storage.impl.SqliteDB;
import com.tavillecode.scorpio.utils.MessageSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Scorpio extends JavaPlugin {
    private static Configuration yaml;
    private static SqliteDB database;
    private static Scorpio instance;

    @Override
    public void onEnable() {
        instance = this;
        yaml = new Configuration(this,this.getDataFolder(),"characters",true,true);
        yaml.reload();
        /*
        try {
            database = new SqliteDB(this.getDataFolder() + "\\playersdata");
            Character.CHARACTERS.keySet().forEach(integer -> {
                try {
                    database.executeUpdate("create table IF NOT EXISTS Citizens_" + integer + " ('Player' TEXT PRIMARY KEY,'Stage' INT);");
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
         */
        getServer().getPluginManager().registerEvents(new RightClickNPCListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerHeldItemListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerSwapItemListener(),this);
        Objects.requireNonNull(getCommand("scorpio")).setExecutor(new ScorpioExecutor());
        MessageSection.EnableMessages();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Scorpio getInstance() {
        return instance;
    }

    public static Configuration getYaml() {
        return yaml;
    }

    public static SqliteDB getDatabase() {
        return database;
    }
}
