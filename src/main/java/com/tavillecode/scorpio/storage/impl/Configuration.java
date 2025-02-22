package com.tavillecode.scorpio.storage.impl;

import com.tavillecode.scorpio.Scorpio;
import com.tavillecode.scorpio.objects.character.Character;
import com.tavillecode.scorpio.objects.character.EmergingText;
import com.tavillecode.scorpio.objects.character.Selection;
import com.tavillecode.scorpio.objects.displayer.CDisplayer;
import com.tavillecode.scorpio.objects.plot.Plots;
import com.tavillecode.scorpio.storage.Storage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Configuration implements Storage {
    protected final boolean createIfNotExist, resource;
    protected final Scorpio plugin;
    protected FileConfiguration config;
    protected File file, path;
    protected String name;

    public Configuration(Scorpio instance, File path, String name, boolean createIfNotExist, boolean resource) {
        this.plugin = instance;
        this.path = path;
        this.name = name + ".yml";
        this.createIfNotExist = createIfNotExist;
        this.resource = resource;
        create();
    }

    public Configuration(Scorpio instance, String path, String name, boolean createIfNotExist, boolean resource) {
        this(instance, new File(path), name, createIfNotExist, resource);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public void save() {
        try {
            config.save(file);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public File reloadFile() {
        file = new File(path, name);
        return file;
    }

    public FileConfiguration reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    @SuppressWarnings("all")
    @Override
    public void reload() {
        Character.CHARACTERS.clear();
        CDisplayer.PLAYER_STAGE.clear();
        CDisplayer.PLAYING_LIST.clear();
        try {
            YamlConfiguration yml = (YamlConfiguration) reloadConfig();
            for(String key:yml.getKeys(false)){
                String npcname = yml.getConfigurationSection(key).getString("name-showed");
                ArrayList<EmergingText> emergingTexts = new ArrayList<>();
                ArrayList<Plots> plots = new ArrayList<>();
                for(String key2:yml.getConfigurationSection(key).getConfigurationSection("process").getKeys(false)) {
                    List<String> dynamic_text = yml.getConfigurationSection(key).getConfigurationSection("process").getConfigurationSection(key2).getStringList("dynamic-text");
                    //String dynamic_text = yml.getConfigurationSection(key).getConfigurationSection("process").getConfigurationSection(key2).getString("dynamic-text");
                    ArrayList<Selection> selections = new ArrayList<>();
                    for(String key3:yml.getConfigurationSection(key).getConfigurationSection("process").getConfigurationSection(key2).getConfigurationSection("options").getKeys(false)) {
                        String text = yml.getConfigurationSection(key).getConfigurationSection("process").getConfigurationSection(key2).getConfigurationSection("options").getConfigurationSection(key3).getString("text");
                        String content = yml.getConfigurationSection(key).getConfigurationSection("process").getConfigurationSection(key2).getConfigurationSection("options").getConfigurationSection(key3).getString("content");
                        Selection s = new Selection(text,content,Integer.parseInt(key3));
                        selections.add(s);
                    }
                    EmergingText emergingText = new EmergingText(dynamic_text,selections);
                    emergingTexts.add(emergingText);
                }
                for (String key3:yml.getConfigurationSection(key).getConfigurationSection("plots").getKeys(false)) {
                    List<String> dynamic_text = yml.getConfigurationSection(key).getConfigurationSection("plots").getConfigurationSection(key3).getStringList("dynamic-text");
                    ArrayList<Selection> selections = new ArrayList<>();
                    for (String key4:yml.getConfigurationSection(key).getConfigurationSection("plots").getConfigurationSection(key3).getConfigurationSection("options").getKeys(false)) {
                        String text = yml.getConfigurationSection(key).getConfigurationSection("plots").getConfigurationSection(key3).getConfigurationSection("options").getConfigurationSection(key4).getString("text");
                        String content = yml.getConfigurationSection(key).getConfigurationSection("plots").getConfigurationSection(key3).getConfigurationSection("options").getConfigurationSection(key4).getString("content");
                        Selection s = new Selection(text,content,Integer.parseInt(key4));
                        selections.add(s);
                    }
                    Plots plot = new Plots(dynamic_text,selections,Integer.parseInt(key3));
                    plots.add(plot);
                }
                Character character = new Character(Integer.parseInt(key),npcname,emergingTexts,plots);
                Character.CHARACTERS.put(Integer.parseInt(key),character);
            }
        } catch (Exception ex) {
            System.out.print("&f- 无法重载!");
        }
    }

    @SuppressWarnings("all")
    @Override
    public void create() {
        if (file == null) {
            reloadFile();
        }
        if (!createIfNotExist || file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
        if (resource) {
            plugin.saveResource(name, false);
        } else {
            try {
                file.createNewFile();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        if (config == null) {
            reloadConfig();
        }
    }
}
