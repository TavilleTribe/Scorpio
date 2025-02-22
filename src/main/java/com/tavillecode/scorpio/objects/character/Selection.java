package com.tavillecode.scorpio.objects.character;

import com.tavillecode.itemStorage.utils.ItemGetter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Selection {
    private final String text;
    private final String actionContent;
    private final int id;

    public Selection(String text, String actionContent, int id) {
        this.text = text;
        this.actionContent = actionContent;
        this.id = id;
    }

    public int executeSelection(Player player) {
        int i = -2;
        for (String s:actionContent.split("\\|")) {
            i = analyseAction(player,s.split(":")[0],s.split(":"));
        }
        if (actionContent.contains("END")) {
            i = -2;
        }
        return i;
    }

    private int analyseAction(Player player,String type,String[] action) {
        if (type.equals("GIVE")) {
            try {
                ItemStack i = ItemGetter.getItem(action[1]);
                i.setAmount(Integer.parseInt(action[2]));
                player.getInventory().addItem(i);
            } catch (NullPointerException e) {
                player.sendMessage("§不存在该物品，出错了！");
            }
        }
        else if (type.equals("COMMAND")) {
            player.performCommand(action[1]);
        }
        else if (type.equals("STAGE")) {
            return Integer.parseInt(action[1]);
        }
        else {
            return -2;
        }
        return -1;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
